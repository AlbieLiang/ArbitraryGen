package cc.suitalk.gradle.plugin;

class ArbitraryGenPluginExtension {

    String libsDir;
    String inputDir;
    String outputDir;

    boolean enable;

    Closure logger
    Closure statistic
    Closure general
    Closure scriptEngine
    Closure javaCodeEngine
    Closure engine
    Closure processor

    public ArbitraryGenPluginExtension() {
        enable = true
    }
}
