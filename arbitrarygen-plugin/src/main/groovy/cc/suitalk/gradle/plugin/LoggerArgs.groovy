package cc.suitalk.gradle.plugin;

/**
 *
 * @Author AlbieLiang
 *
 */
public class LoggerArgs {

    boolean debug;
    boolean printArgs;
    boolean logToFile;
    boolean printSeparator;
    boolean printTag;
    boolean printLevel;
    String path;

    // level: v-1, d-2, i-3, w-4, e-5, a-6
    int level;

    public LoggerArgs() {
        debug = false;
        printArgs = false;
        logToFile = true;
        printSeparator = false;
        printTag = false;
        printLevel = false;
        level = 1;
        path = null;
    }
}