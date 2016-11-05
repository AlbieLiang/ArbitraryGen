package cc.suitalk.gradle.plugin

import groovy.json.JsonBuilder
//import org.gradle.internal.impldep.com.amazonaws.util.json.JSONObject
//import org.gradle.internal.impldep.com.google.gson.JsonObject;

/**
 *
 * @Author AlbieLiang
 *
 */
public class LoggerArgs implements Serializable {

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

    public String getName() {
        return "logger"
    }
//
//    public JsonObject toJson() {
//        JsonObject json = new JsonObject();
//        json.addProperty("debug", debug);
//        json.addProperty("printArgs", printArgs);
//        json.addProperty("logToFile", logToFile);
//        json.addProperty("printSeparator", printSeparator);
//        json.addProperty("printTag", printTag);
//        json.addProperty("printLevel", printLevel);
//        json.addProperty("path", path);
//        return json;
//    }
//
//    public JsonBuilder toJsonBuilder() {
//        JsonBuilder builder = new JsonBuilder();
////        builder {
////            debug: debug
////            printArgs: printArgs
////            logToFile: logToFile
////            printSeparator: printSeparator
////            printTag: printTag
////            printLevel: printLevel
////            path: path
////        }
//        builder.debug debug
//        builder.printArgs printArgs
//        builder.logToFile logToFile
//        builder.printSeparator printSeparator
//        builder.printTag printTag
//        builder.printLevel printLevel
//        builder.path path
//        return builder;
//    }
}