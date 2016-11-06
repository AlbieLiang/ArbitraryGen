package cc.suitalk.arbitrarygen.core;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cc.suitalk.arbitrarygen.core.base.ArbitraryGenEngine;
import cc.suitalk.arbitrarygen.core.base.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.core.base.ArbitraryGenProcessor.ErrorCode;
import cc.suitalk.arbitrarygen.engine.DefaultAGEngine;
import cc.suitalk.arbitrarygen.engine.JavaCodeAGEngine;
import cc.suitalk.arbitrarygen.engine.ScriptTemplateAGEngine;
import cc.suitalk.arbitrarygen.processor.LoggerAGProcessor;
import cc.suitalk.arbitrarygen.processor.ScannerAGProcessor;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/10/27.
 */
public class ArbitraryGenCore {

    private static final String TAG = "AG.ArbitraryGenCore";

    private Map<String, ArbitraryGenProcessor> mProcessors;
    private Map<String, ArbitraryGenEngine> mEngines;
    private volatile boolean mInitialized;
    private volatile boolean mStarted;
    private JSONObject mArgs;

    private JarClassLoaderWrapper mJarClassLoader;

    public ArbitraryGenCore() {
        mProcessors = new ConcurrentHashMap<>();
        mEngines = new ConcurrentHashMap<>();
        mJarClassLoader = new JarClassLoaderWrapper();
    }

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
        for (ArbitraryGenProcessor processor : mProcessors.values()) {
            processor.initialize(this, getAGProcessorArgs(mArgs, processor.getName()));
        }
        mInitialized = true;
    }

    public void start() {
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

    public void addProcessor(ArbitraryGenProcessor processor) {
        if (processor == null) {
            Log.w(TAG, "the processor is null.");
            return;
        }
        if (mInitialized) {
            processor.initialize(this, getAGProcessorArgs(mArgs, processor.getName()));
        }
        mProcessors.put(processor.getName(), processor);
        // For auto execute engine
        if (processor instanceof ArbitraryGenEngine) {
            mEngines.put(processor.getName(), (ArbitraryGenEngine) processor);
            if (mStarted) {
                execProcess(processor, mArgs.optJSONObject(processor.getName()));
            }
        }
    }

    public ArbitraryGenProcessor removeProcessor(String name) {
        return mProcessors.remove(name);
    }

    /**
     *
     * @param processors
     * @param processorName
     * @param args
     * @return
     */
    public JSONObject execProcess(Map<String, ArbitraryGenProcessor> processors, String processorName, JSONObject args) {
        return execProcess(processors.get(processorName), args);
    }

    /**
     *
     * @param processor
     * @return
     */
    public JSONObject execProcess(ArbitraryGenProcessor processor, JSONObject args) {
        if (processor == null) {
            return null;
        }
        String[] dependencies = processor.getDependencies();
        Map<String, ArbitraryGenProcessor> engines = new HashMap<>();
        for (int i = 0; i < dependencies.length; i++) {
            ArbitraryGenProcessor p = mProcessors.get(dependencies[i]);
            if (p == null) {
                processor.onError(ErrorCode.MISSING_DEPENDENCIES, String.format("Missing dependencies engine '%s", dependencies[i]));
                return null;
            }
            engines.put(dependencies[i], p);
        }
        return processor.exec(this, engines, args);
    }

    public JarClassLoaderWrapper getJarClassLoader() {
        return mJarClassLoader;
    }

    private void prepare(JSONObject jsonObject) {
        // Resolve hardcode Engine
        addProcessor(new LoggerAGProcessor());
        addProcessor(new ScannerAGProcessor());
        // Add more extension Engine by arguments
        addProcessor(new DefaultAGEngine());
        addProcessor(new ScriptTemplateAGEngine());
        addProcessor(new JavaCodeAGEngine());
    }

    private JSONObject getAGProcessorArgs(JSONObject args, String name) {
        if (args == null || Util.isNullOrNil(name)) {
            return null;
        }
        JSONObject jsonObject = args.optJSONObject(name);
        if (jsonObject != null) {
            int count = 0;
            String src = jsonObject.optString(ArgsConstants.EXTERNAL_ARGS_KEY_SRC);
            String dest = jsonObject.optString(ArgsConstants.EXTERNAL_ARGS_KEY_DEST);
            String libsDir = jsonObject.optString(ArgsConstants.EXTERNAL_ARGS_KEY_LIBS_DIR);
            if (Util.isNullOrNil(src)) {
                jsonObject.put(ArgsConstants.EXTERNAL_ARGS_KEY_SRC, args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_SRC));
                count++;
            }
            if (Util.isNullOrNil(dest)) {
                jsonObject.put(ArgsConstants.EXTERNAL_ARGS_KEY_DEST, args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_DEST));
                count++;
            }
            if (Util.isNullOrNil(libsDir)) {
                jsonObject.put(ArgsConstants.EXTERNAL_ARGS_KEY_LIBS_DIR, args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_LIBS_DIR));
                count++;
            }
            if (count > 0) {
                args.put(name, jsonObject);
            }
        }
        Log.v(TAG, "AGProcessor(%s) args : %s", name, jsonObject);
        return jsonObject;
    }
}
