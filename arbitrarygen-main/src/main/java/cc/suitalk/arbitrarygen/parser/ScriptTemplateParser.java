package cc.suitalk.arbitrarygen.parser;

import net.sf.json.JSONObject;

import java.io.File;

import cc.suitalk.arbitrarygen.extension.SourceFileParser;
import cc.suitalk.arbitrarygen.template.TemplateConfig;
import cc.suitalk.arbitrarygen.template.base.TemplateProcessor;

/**
 * Created by AlbieLiang on 16/11/2.
 */
public class ScriptTemplateParser implements SourceFileParser<JSONObject, JSONObject> {

    private TemplateProcessor mTemplateProcessor;
    private TemplateConfig mTemplateConfig;

    @Override
    public boolean match(String suffix) {
        return false;
    }

    @Override
    public JSONObject parse(JSONObject args, File file) {
        return null;
    }
}
