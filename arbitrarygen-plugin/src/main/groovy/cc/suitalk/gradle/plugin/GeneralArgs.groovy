package cc.suitalk.gradle.plugin

import groovy.json.JsonBuilder
//import org.gradle.internal.impldep.com.google.gson.JsonArray
//import org.gradle.internal.impldep.com.google.gson.JsonObject
//import org.gradle.internal.impldep.com.google.gson.JsonPrimitive;

class GeneralArgs implements Serializable {

    boolean enable;
    String[] format;
    String src;
    String dest;

    Closure[] parser;
    Closure[] extension;

    public GeneralArgs() {
        enable = true;
        format = new String[0];
        src = "";
        dest = "";
        parser = new Closure[0];
        extension = new Closure[0];
    }

    public String getName() {
        return "general"
    }
//
//    public JsonObject toJson() {
//        JsonObject json = new JsonObject();
//        json.addProperty("enable", enable);
//        json.addProperty("format", format);
//        json.addProperty("src", src);
//        json.addProperty("dest", dest);
//        json.addProperty("rule", rule);
//        JsonArray jsonArray = new JsonArray();
//        for (int i = 0; i < parser.length; i++) {
//            jsonArray.add(new JsonPrimitive(parser[i]));
//        }
//        json.addProperty("parser", jsonArray);
//
//        JsonArray extensionArray = new JsonArray();
//        for (int i = 0; i < extension.length; i++) {
//            extensionArray.add(new JsonPrimitive(extension[i]));
//        }
//        json.addProperty("extension", extensionArray);
//        return json;
//    }
//
//    public JsonBuilder toJsonBuilder() {
//        JsonBuilder builder = new JsonBuilder();
////        builder {
////            enable: enable
////            format: format
////            src: src
////            dest: dest
////            rule: rule
////            parser: parser
////            extension: extension
////        }
//        builder.enable enable
//        builder.format format
//        builder.src src
//        builder.dest dest
//        builder.rule rule
//        builder.parser parser
//        builder.extension extension
//        return builder;
//    }
}