package cc.suitalk.gradle.plugin

import groovy.json.JsonBuilder
//import org.gradle.internal.impldep.com.google.gson.JsonObject

class JavaCodeEngineArgs implements Serializable {

    boolean enable;
    String src;
    String dest;
    String rule;

    public JavaCodeEngineArgs() {
        enable = false;
        src = "";
        dest = "";
        rule = "";
    }

    public String getName() {
        return "javaCodeEngine"
    }
//
//    public JsonObject toJson() {
//        JsonObject json = new JsonObject();
//        json.addProperty("enable", enable);
//        json.addProperty("src", src);
//        json.addProperty("dest", dest);
//        json.addProperty("rule", rule);
//        return json;
//    }
//
//    public JsonBuilder toJsonBuilder() {
//        JsonBuilder builder = new JsonBuilder();
////        builder {
////            enable: enable
////            src: src
////            dest: dest
////            rule: rule
////        }
//        builder.enable enable
//        builder.src src
//        builder.dest dest
//        builder.rule rule
//        return builder;
//    }
}