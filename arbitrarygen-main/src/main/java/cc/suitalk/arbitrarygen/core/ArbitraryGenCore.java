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
import cc.suitalk.arbitrarygen.processor.ScannerAGProcessor;

/**
 * Created by albieliang on 16/10/27.
 */
public class ArbitraryGenCore {

    private Map<String, ArbitraryGenProcessor> mProcessors;
    private List<ArbitraryGenEngine> mEngines;
    private volatile boolean mInitialized;
    private JSONObject mArgs;

    private JarClassLoaderWrapper mJarClassLoader;

    public ArbitraryGenCore() {
        mProcessors = new ConcurrentHashMap<>();
        mEngines = new LinkedList<>();
        mJarClassLoader = new JarClassLoaderWrapper();
    }

    public void initialize(JSONObject jsonObject) {
        if (jsonObject == null) {
            throw new NullPointerException("jsonObject can't be null.");
        }
        mArgs = jsonObject;
        prepare(jsonObject);
        for (ArbitraryGenProcessor engine : mProcessors.values()) {
            engine.initialize(this, mArgs.optJSONObject(engine.getName()));
        }
        List<ArbitraryGenEngine> engines = new LinkedList<>(mEngines);
        for (ArbitraryGenEngine engine : engines) {
            execProcess(engine, mArgs.optJSONObject(engine.getName()));
        }
        mInitialized = true;
    }

    public void addProcessor(ArbitraryGenProcessor processor) {
        if (processor == null) {
            return;
        }
        if (mInitialized) {
            processor.initialize(this, mArgs.optJSONObject(processor.getName()));
        }
        mProcessors.put(processor.getName(), processor);
        // For auto execute engine
        if (processor instanceof ArbitraryGenEngine) {
            mEngines.add((ArbitraryGenEngine) processor);
            if (mInitialized) {
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
            ArbitraryGenProcessor e = mProcessors.get(dependencies[i]);
            if (e == null) {
                processor.onError(ErrorCode.MISSING_DEPENDENCIES, String.format("Missing dependencies engine '%s", dependencies[i]));
            }
            engines.put(dependencies[i], e);
        }
        return processor.exec(this, engines, args);
    }

    public JarClassLoaderWrapper getJarClassLoader() {
        return mJarClassLoader;
    }

    private void prepare(JSONObject jsonObject) {
        // Resolve hardcode Engine
        addProcessor(new ScannerAGProcessor());
        // Add more extension Engine by arguments
        addProcessor(new DefaultAGEngine());
        addProcessor(new ScriptTemplateAGEngine());
        addProcessor(new JavaCodeAGEngine());
    }
}
