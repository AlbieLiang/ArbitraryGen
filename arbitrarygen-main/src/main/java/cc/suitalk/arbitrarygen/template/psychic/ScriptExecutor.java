package cc.suitalk.arbitrarygen.template.psychic;

import net.sf.json.JSONObject;

import cc.suitalk.arbitrarygen.template.psychic.base.PsychicExecutor;

/**
 * Created by AlbieLiang on 16/11/14.
 */

public class ScriptExecutor implements PsychicExecutor {

    @Override
    public String getName() {
        return "script-executor";
    }

    @Override
    public JSONObject exec(JSONObject inputJSON) {
        return null;
    }
}
