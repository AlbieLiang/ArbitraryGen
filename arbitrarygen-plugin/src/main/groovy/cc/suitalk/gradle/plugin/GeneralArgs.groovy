package cc.suitalk.gradle.plugin;

class GeneralArgs {

    boolean enable;
    String format;
    String rule;

    String[] extParsers;

    public GeneralArgs() {
        enable = true;
        format = "xml"
        rule = "";
        extParsers = new String[0];
    }
}