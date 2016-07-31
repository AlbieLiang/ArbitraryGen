package cc.suitalk.arbitrarygen.template.base;

import net.sf.json.JSONObject;

import javax.script.ScriptEngine;

import cc.suitalk.arbitrarygen.engine.ScriptTemplateGenCodeEngine.TaskInfo;

/**
 * Created by albieliang on 16/7/24.
 */
public interface IPsychicGenerator {

    String genCode(ScriptEngine engine, JSONObject jsonObj, TaskInfo info);
}
