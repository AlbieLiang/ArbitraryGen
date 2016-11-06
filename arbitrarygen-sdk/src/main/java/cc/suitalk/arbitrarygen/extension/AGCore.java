package cc.suitalk.arbitrarygen.extension;

import net.sf.json.JSONObject;

import java.util.Map;

import cc.suitalk.arbitrarygen.core.JarClassLoaderWrapper;

/**
 * Created by AlbieLiang on 16/11/6.
 */
public interface AGCore {

    void initialize(JSONObject jsonObject);

    void start();

    void addProcessor(ArbitraryGenProcessor processor);

    ArbitraryGenProcessor removeProcessor(String name);

    ArbitraryGenProcessor getProcessor(String name);

    JSONObject execProcess(Map<String, ArbitraryGenProcessor> processors, String processorName, JSONObject args);

    JSONObject execProcess(ArbitraryGenProcessor processor, JSONObject args);

    JarClassLoaderWrapper getJarClassLoader();
}
