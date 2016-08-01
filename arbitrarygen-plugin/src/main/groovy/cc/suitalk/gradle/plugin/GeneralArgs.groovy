package cc.suitalk.gradle.plugin;

class GeneralArgs {

    boolean enable;
    String format;
    String rule;

    String[] extParsers;

    public GeneralArgs() {
        enable = true;
        format = ""
        rule = "";
        extParsers = new String[0];
    }
}