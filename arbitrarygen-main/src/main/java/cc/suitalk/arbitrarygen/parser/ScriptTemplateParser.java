package cc.suitalk.arbitrarygen.parser;

import net.sf.json.JSONObject;

import java.io.File;

import cc.suitalk.arbitrarygen.core.base.SourceFileParser;
import cc.suitalk.arbitrarygen.template.TemplateConfig;
import cc.suitalk.arbitrarygen.template.base.ITemplateProcessor;

/**
 * Created by albieliang on 16/11/2.
 */
public class ScriptTemplateParser implements SourceFileParser<JSONObject> {

    private ITemplateProcessor mTemplateProcessor;
    private TemplateConfig mTemplateConfig;

    @Override
    public boolean match(String suffix) {
        return false;
    }

    @Override
    public JSONObject parse(File file) {
        return null;
    }
}
