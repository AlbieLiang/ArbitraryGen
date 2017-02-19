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

package cc.suitalk.arbitrarygen.engine;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.Map;

import cc.suitalk.arbitrarygen.analyzer.JavaFileLexer;
import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.core.ConfigInfo;
import cc.suitalk.arbitrarygen.core.JarClassLoaderWrapper;
import cc.suitalk.arbitrarygen.core.TypeDefineWrapperMgr;
import cc.suitalk.arbitrarygen.extension.AGAnnotationProcessor;
import cc.suitalk.arbitrarygen.extension.TypeDefineWrapper;
import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenEngine;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.impl.AGAnnotationWrapper;
import cc.suitalk.arbitrarygen.utils.ExtJarClassLoaderTools;
import cc.suitalk.arbitrarygen.utils.JSONArgsUtils;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/2.
 */
public class JavaCodeAGEngine implements ArbitraryGenEngine {

    private static final String TAG = "AG.JavaCodeAGEngine";

    private volatile boolean mEnable;

    private TypeDefineWrapperMgr mTypeDefWrapper;

    @Override
    public String getName() {
        return "javaCodeEngine";
    }

    @Override
    public void initialize(AGCore core, JSONObject args) {
        if (args == null) {
            Log.i(TAG, "initialize failed, args is null.");
            return;
        }
        mTypeDefWrapper = new TypeDefineWrapperMgr();
        // Extract ArbitraryEnable flag
        mEnable = args.optBoolean(ArgsConstants.EXTERNAL_ARGS_KEY_ENABLE, true);
        if (mEnable) {
            final AGAnnotationWrapper annWrapper = new AGAnnotationWrapper();
            addTypeDefWrapper(annWrapper);
            // load extension jar
            JSONObject extensionJson = args.optJSONObject(ArgsConstants.EXTERNAL_ARGS_KEY_EXTENSION);
            if (extensionJson != null) {
                JarClassLoaderWrapper loader = core.getJarClassLoader();
                ExtJarClassLoaderTools.loadJar(loader,
                        JSONArgsUtils.getJSONArray(extensionJson, ArgsConstants.EXTERNAL_ARGS_KEY_JAR, true));
                ExtJarClassLoaderTools.loadClass(loader,
                        JSONArgsUtils.getJSONArray(extensionJson, ArgsConstants.EXTERNAL_ARGS_KEY_CLASS, true),
                        new ExtJarClassLoaderTools.OnLoadedClass() {
                            @Override
                            public void onLoadedClass(Object o) {
                                if (o instanceof TypeDefineWrapper) {
                                    addTypeDefWrapper((TypeDefineWrapper) o);
                                }
                            }
                        });
                ExtJarClassLoaderTools.loadClass(loader,
                        JSONArgsUtils.getJSONArray(extensionJson, ArgsConstants.EXTERNAL_ARGS_KEY_PROCESSOR_CLASS, true),
                        new ExtJarClassLoaderTools.OnLoadedClass() {
                            @Override
                            public void onLoadedClass(Object o) {
                                if (o instanceof AGAnnotationProcessor) {
                                    annWrapper.addAnnotationProcessor((AGAnnotationProcessor) o);
                                }
                            }
                        });
            }
//                TypeDefineWrapper wrapper = new DefaultTypeDefineWrapper();
            // TODO: 16/11/2 albieliang, Add more type worker here
//                wrapper.addIAGTaskWorker(worker);
//                addTypeDefWrapper(wrapper);
        }
    }

    @Override
    public String[] getDependencies() {
        return new String[] { "parse-rule" };
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        if (!mEnable) {
            return null;
        }
        ConfigInfo configInfo = new ConfigInfo();
        // Extract the destination path arg
        String dest = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_DEST);
        if (!Util.isNullOrNil(dest)) {
            configInfo.setDestPath(dest);
        }
        // Extract the source template path arg
        String src = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_SRC);
        if (!Util.isNullOrNil(src)) {
            configInfo.setSrcPath(src);
        }
        JSONObject result = core.execProcess(processors, "parse-rule", args);
        if (result == null) {
            Log.i(TAG, "parse rule result is null.");
            return null;
        }
        JSONArray fileArray = result.optJSONArray("fileArray");
        if (fileArray == null) {
            Log.i(TAG, "file array is null.");
            return null;
        }
        for (int i = 0; i < fileArray.size(); i++) {
            File file = new File(fileArray.getString(i));
            JavaFileLexer lexer = new JavaFileLexer(file);
            JavaFileObject javaFileObject = lexer.start();
            configInfo.setFile(file);
            mTypeDefWrapper.doWrap(configInfo, javaFileObject);
        }
        return null;
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }

    public void addTypeDefWrapper(TypeDefineWrapper wrapper) {
        this.mTypeDefWrapper.addWrapper(wrapper);
    }

    public void removeTypeDefWrapper(TypeDefineWrapper wrapper) {
        this.mTypeDefWrapper.removeWrapper(wrapper);
    }
}
