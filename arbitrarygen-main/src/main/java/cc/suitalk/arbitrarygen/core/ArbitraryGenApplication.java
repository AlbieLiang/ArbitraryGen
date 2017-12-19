/*
 *  Copyright (C) 2016-present Albie Liang. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package cc.suitalk.arbitrarygen.core;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cc.suitalk.arbitrarygen.engine.DefaultAGEngine;
import cc.suitalk.arbitrarygen.engine.JavaCodeAGEngine;
import cc.suitalk.arbitrarygen.engine.PsychicAGEngine;
import cc.suitalk.arbitrarygen.engine.ScriptTemplateAGEngine;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenEngine;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.processor.ExecuteScriptProcessor;
import cc.suitalk.arbitrarygen.processor.HybridTemplateProcessor;
import cc.suitalk.arbitrarygen.processor.LoggerAGProcessor;
import cc.suitalk.arbitrarygen.processor.ParseJavaFileProcessor;
import cc.suitalk.arbitrarygen.processor.ParseJsonProcessor;
import cc.suitalk.arbitrarygen.processor.ParseRuleProcessor;
import cc.suitalk.arbitrarygen.processor.ParseXmlProcessor;
import cc.suitalk.arbitrarygen.processor.PsychicTaskProcessor;
import cc.suitalk.arbitrarygen.processor.ScannerAGProcessor;
import cc.suitalk.arbitrarygen.processor.StatisticProcessor;
import cc.suitalk.arbitrarygen.processor.TemplateProcessor;
import cc.suitalk.arbitrarygen.utils.ExtJarClassLoaderTools;
import cc.suitalk.arbitrarygen.utils.JSONArgsUtils;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by albieliang on 2017/12/9.
 */

final class ArbitraryGenApplication implements AGApplication {

    private static final String TAG = "AG.ArbitraryGenApplication";

    private List<ArbitraryGenProcessor> mProcessorList;
    private Map<String, ArbitraryGenProcessor> mProcessors;
    private Map<String, ArbitraryGenEngine> mEngines;
    private volatile boolean mInitialized;
    private volatile boolean mStarted;
    private JSONObject mArgs;

    private JarClassLoaderWrapper mJarClassLoader;

    public ArbitraryGenApplication() {
        mProcessors = new ConcurrentHashMap<>();
        mProcessorList = new LinkedList<>();
        mEngines = new ConcurrentHashMap<>();
        mJarClassLoader = new JarClassLoaderWrapper();
        addInnerProcessor();
    }

    @Override
    public void initialize(JSONObject jsonObject) {
        if (jsonObject == null) {
            throw new NullPointerException("jsonObject can't be null.");
        }
        if (mInitialized) {
            Log.i(TAG, "the core has been initialized.");
            return;
        }
        mArgs = jsonObject;
        prepare(jsonObject);
        for (ArbitraryGenProcessor processor : mProcessorList) {
            processor.initialize(this, getAGProcessorArgs(mArgs, processor.getName()));
        }
        mInitialized = true;
    }

    @Override
    public void execute() {
        if (mStarted) {
            Log.i(TAG, "the core has been started.");
            return;
        }
        List<ArbitraryGenEngine> engines = new LinkedList<>(mEngines.values());
        for (ArbitraryGenEngine engine : engines) {
            execProcess(engine, mArgs.optJSONObject(engine.getName()));
        }
        mStarted = true;
    }

    @Override
    public void addProcessor(ArbitraryGenProcessor processor) {
        if (processor == null) {
            Log.w(TAG, "the processor is null.");
            return;
        }
        if (mInitialized) {
            processor.initialize(this, getAGProcessorArgs(mArgs, processor.getName()));
        }
        ArbitraryGenProcessor p = mProcessors.put(processor.getName(), processor);
        if (p != null) {
            mProcessorList.remove(p);
        }
        mProcessorList.add(processor);
        // For auto execute engine
        if (processor instanceof ArbitraryGenEngine) {
            mEngines.put(processor.getName(), (ArbitraryGenEngine) processor);
            if (mStarted) {
                execProcess(processor, mArgs.optJSONObject(processor.getName()));
            }
        }
    }

    @Override
    public ArbitraryGenProcessor removeProcessor(String name) {
        ArbitraryGenProcessor processor = mProcessors.remove(name);
        if (processor != null) {
            mProcessorList.remove(processor);
        }
        return processor;
    }

    @Override
    public ArbitraryGenProcessor getProcessor(String name) {
        return mProcessors.get(name);
    }

    @Override
    public JSONObject execProcess(Map<String, ArbitraryGenProcessor> processors, String processorName, JSONObject args) {
        return execProcess(processors.get(processorName), args);
    }

    @Override
    public JSONObject execProcess(ArbitraryGenProcessor processor, JSONObject args) {
        return execProcess(processor, args, null);
    }

    @Override
    public JSONObject execProcess(String processorName, JSONObject args) {
        return execProcess(getProcessor(processorName), args);
    }

    @Override
    public JSONObject execProcess(ArbitraryGenProcessor processor, JSONObject args, Map<String, ArbitraryGenProcessor> deliverDepends) {
        if (processor == null) {
            return null;
        }
        String[] dependencies = processor.getDependencies();
        Map<String, ArbitraryGenProcessor> dependsPros = new HashMap<>();
        for (int i = 0; i < dependencies.length; i++) {
            ArbitraryGenProcessor p = mProcessors.get(dependencies[i]);
            if (p == null) {
                processor.onError(this, ArbitraryGenProcessor.ErrorCode.MISSING_DEPENDENCIES, String.format("Missing dependencies engine '%s", dependencies[i]));
                return null;
            }
            dependsPros.put(dependencies[i], p);
        }
        if (deliverDepends != null && !deliverDepends.isEmpty()) {
            dependsPros.putAll(deliverDepends);
        }
        return processor.exec(this, dependsPros, args);
    }

    @Override
    public JarClassLoaderWrapper getJarClassLoader() {
        return mJarClassLoader;
    }

    private void addInnerProcessor() {
        // Resolve hardcode Engine
        addProcessor(new LoggerAGProcessor());
        addProcessor(new StatisticProcessor());

        /*@@@#SCRIPT-BEGIN#
        <% if (processorList && processorList.length > 0) {
            for (var i = 0; i < processorList.length; i++) {
                if ("LoggerAGProcessor" == processorList[i]._class[0]._name || "StatisticProcessor" == processorList[i]._class[0]._name) {
                    continue;
                }%>
        addProcessor(new <%=processorList[i]._class[0]._name%>());<%
            }
        }%>
        #SCRIPT-END#@@@*///@@@#AUTO-GEN-BEGIN#

        addProcessor(new ExecuteScriptProcessor());
        addProcessor(new HybridTemplateProcessor());
        addProcessor(new ParseJavaFileProcessor());
        addProcessor(new ParseJsonProcessor());
        addProcessor(new ParseRuleProcessor());
        addProcessor(new ParseXmlProcessor());
        addProcessor(new PsychicTaskProcessor());
        addProcessor(new ScannerAGProcessor());
        addProcessor(new TemplateProcessor());

        //@@@#AUTO-GEN-END#
        // Add more extension Engine by arguments
        addProcessor(new DefaultAGEngine());
        addProcessor(new ScriptTemplateAGEngine());
        addProcessor(new JavaCodeAGEngine());
        addProcessor(new PsychicAGEngine());
    }

    private void prepare(JSONObject jsonObject) {
        // load external engine
        JSONObject engineJson = jsonObject.optJSONObject(ArgsConstants.EXTERNAL_ARGS_KEY_ENGINE);
        if (engineJson != null) {
            JSONObject extensionJson = engineJson.optJSONObject(ArgsConstants.EXTERNAL_ARGS_KEY_EXTENSION);
            if (extensionJson != null) {
                addExternalProcessor(engineJson, extensionJson);
            }
        }
        // load external processor
        JSONObject processorJson = jsonObject.optJSONObject(ArgsConstants.EXTERNAL_ARGS_KEY_PROCESSOR);
        if (processorJson != null) {
            JSONObject extensionJson = processorJson.optJSONObject(ArgsConstants.EXTERNAL_ARGS_KEY_EXTENSION);
            if (extensionJson != null) {
                addExternalProcessor(processorJson, extensionJson);
            }
        }
    }

    private void addExternalProcessor(final JSONObject argsJson, JSONObject extensionJson) {
        JarClassLoaderWrapper loader = getJarClassLoader();
        ExtJarClassLoaderTools.loadJar(loader,
                JSONArgsUtils.getJSONArray(extensionJson, ArgsConstants.EXTERNAL_ARGS_KEY_JAR, true));
        ExtJarClassLoaderTools.loadClass(loader,
                JSONArgsUtils.getJSONArray(extensionJson, ArgsConstants.EXTERNAL_ARGS_KEY_CLASS, true),
                new ExtJarClassLoaderTools.OnLoadedClass() {
                    @Override
                    public void onLoadedClass(Object o) {
                        if (o instanceof ArbitraryGenProcessor) {
                            ArbitraryGenProcessor processor = (ArbitraryGenProcessor) o;
                            String name = processor.getName();
                            if (getProcessor(name) != null) {
                                Log.i(TAG, "add external AGProcessor failed, duplicate processor name(%s).", name);
                                return;
                            }
                            JSONObject args = argsJson.optJSONObject(name);
                            if (args != null) {
                                mArgs.put(name, args);
                            }
                            addProcessor(processor);
                        }
                    }
                });
    }

    private JSONObject getAGProcessorArgs(JSONObject args, String name) {
        if (args == null || Util.isNullOrNil(name)) {
            return null;
        }
        JSONObject jsonObject = args.optJSONObject(name);
        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }
        int count = 0;
        String srcDir = jsonObject.optString(ArgsConstants.EXTERNAL_ARGS_KEY_SRC_DIR);
        String destDir = jsonObject.optString(ArgsConstants.EXTERNAL_ARGS_KEY_DEST_DIR);
        String templateDir = jsonObject.optString(ArgsConstants.EXTERNAL_ARGS_KEY_TEMPLATE_DIR);
        if (Util.isNullOrNil(srcDir)) {
            jsonObject.put(ArgsConstants.EXTERNAL_ARGS_KEY_SRC_DIR, args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_SRC_DIR));
            count++;
        }
        if (Util.isNullOrNil(destDir)) {
            jsonObject.put(ArgsConstants.EXTERNAL_ARGS_KEY_DEST_DIR, args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_DEST_DIR));
            count++;
        }
        if (Util.isNullOrNil(templateDir)) {
            jsonObject.put(ArgsConstants.EXTERNAL_ARGS_KEY_TEMPLATE_DIR, args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_TEMPLATE_DIR));
            count++;
        }
        if (count > 0) {
            args.put(name, jsonObject);
        }
        Log.v(TAG, "getAGProcessor(%s) args : %s", name, jsonObject);
        return jsonObject;
    }
}