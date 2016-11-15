package cc.suitalk.arbitrarygen.template;

import net.sf.json.JSONObject;

import javax.script.ScriptEngine;

import cc.suitalk.arbitrarygen.template.base.BasePsychicWorker;

/**
 * Created by AlbieLiang on 16/11/14.
 */
public class PsychicGenTask extends BasePsychicWorker {

    public PsychicGenTask(TemplateConfig cfg) {
        super(cfg);
    }

    @Override
    public String getSupportSuffix() {
        return "psychic-define";
    }

    @Override
    public String genCode(ScriptEngine engine, JSONObject jsonObj, TaskInfo info) {
        return null;
    }
}
