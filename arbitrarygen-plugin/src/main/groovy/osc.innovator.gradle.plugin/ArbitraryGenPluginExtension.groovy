package osc.innovator.gradle.plugin;

class ArbitraryGenPluginExtension {
    String libsDir;
    String inputDir;
    String outputDir;

    boolean enable;

//    GeneralArgs generalArgs;

//    LoggerArgs loggerArgs;

    public ArbitraryGenPluginExtension() {
//        generalArgs = new GeneralArgs();
//        loggerArgs = new LoggerArgs();
    }

    //该函数，允许我们通过配置传入闭包，来给容器添加对象
//    def generalArgs(Closure closure) {
//        generalArgs.configure(closure)
//    }
}
