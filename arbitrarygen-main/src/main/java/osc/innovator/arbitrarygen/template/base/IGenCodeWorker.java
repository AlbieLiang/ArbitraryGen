package osc.innovator.arbitrarygen.template.base;

import javax.script.ScriptEngine;

import osc.innovator.arbitrarygen.engine.ScriptTemplateGenCodeEngine.TaskInfo;
import net.sf.json.JSONObject;

/**
 * 
 * @author AlbieLiang
 *
 */
public interface IGenCodeWorker {
	
	String genCode(ScriptEngine engine, JSONObject jsonObj, TaskInfo info);
	
	String getSupportSuffix();
}