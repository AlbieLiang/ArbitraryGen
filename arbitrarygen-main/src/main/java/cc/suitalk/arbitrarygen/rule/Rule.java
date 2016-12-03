package cc.suitalk.arbitrarygen.rule;

/**
 * Created by AlbieLiang on 2016/11/26.
 */
public class Rule {

    public static final int TYPE_RULE = 0;
    public static final int TYPE_FILE = 1;
    public static final int TYPE_DIRECTORY = 2;
    public static final int TYPE_RECURSION_DIRECTORY = 3;

    private int type;

    private String content;

    public Rule() {
    }

    public Rule(String content) {
        setContent(content);
    }

    public Rule(int type, String content) {
        setType(type);
        setContent(content);
    }

    public int getType() {
        return type;
    }

    public Rule setType(int type) {
        this.type = type;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Rule setContent(String content) {
        this.content = content;
        return this;
    }
}
