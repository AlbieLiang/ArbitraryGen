package cc.suitalk.gradle.plugin;

class ArbitraryGenPluginExtension {

    String libsDir;
    String inputDir;
    String outputDir;

    boolean enable;

    Closure logger
    Closure general
    Closure scriptEngine
    Closure javaCodeEngine
    Closure engine

    public ArbitraryGenPluginExtension() {
        enable = true
    }
}
