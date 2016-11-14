package cc.suitalk.arbitrarygen.template.base;

import net.sf.json.JSONObject;

import javax.script.ScriptEngine;

import cc.suitalk.arbitrarygen.template.TaskInfo;

/**
 * Created by albieliang on 16/7/24.
 */
public interface PsychicGenerator {

    String genCode(ScriptEngine engine, JSONObject jsonObj, TaskInfo info);
}
