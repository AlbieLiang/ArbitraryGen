package osc.innovator.gradle.plugin;

class GeneralArgs {

    boolean enable;
    String format;
    String rule;

    String[] extParsers;

    public GeneralArgs() {
        enable = true;
        format = "xml,event,db"
        rule = "";
        extParsers = new String[0];
    }
}