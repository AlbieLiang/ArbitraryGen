package osc.innovator.arbitrarygen.template.base;

import net.sf.json.JSONObject;

import javax.script.ScriptEngine;

import osc.innovator.arbitrarygen.engine.ScriptTemplateGenCodeEngine.TaskInfo;

/**
 * Created by albieliang on 16/7/24.
 */
public interface IPsychicGenerator {

    String genCode(ScriptEngine engine, JSONObject jsonObj, TaskInfo info);
}
