package cc.suitalk.arbitrarygen.template;

import java.io.File;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import cc.suitalk.arbitrarygen.template.base.BasePsychicWorker;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.TemplateUtils;
import cc.suitalk.arbitrarygen.utils.Util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author AlbieLiang
 *
 */
public class GenVigorDBTask extends BasePsychicWorker {

	private static final String TAG = "AG.GenVigorDBTask";
	
	private String mVigorDBItemTmpl;
	private String mVigorDBInfoDelegateTmpl;
	private String mTemplateLibs;
	
	public GenVigorDBTask(TemplateConfig cfg) {
		super(cfg);
		mVigorDBItemTmpl = FileOperation.read(mConfigArgs.getTemplateLibs() + "/VigorDBItem.vigor-template");
	}
	
	@Override
	public String genCode(ScriptEngine engine, JSONObject jsonObj, TaskInfo info) {
		String pkg = jsonObj.optString("@package", "");
		String delegate = jsonObj.optString("@delegate", "");
		
		Object tbObj= jsonObj.get("table");
		JSONArray tables = new JSONArray();
		
		// Use default class name
		if (Util.isNullOrNil(delegate)) {
			delegate = "AG_VDBInfoDelegate";
		}
		try {
			if (tbObj instanceof JSONArray) {
				JSONArray arr = (JSONArray) tbObj;
				for (int i = 0, len = arr.size(); i < len; i++) {
					JSONObject obj = arr.getJSONObject(i);
					obj.put("@package", pkg);
					genCode(engine, mVigorDBItemTmpl, info.transfer, info.utils, info.destPath, obj);
					tables.add(obj.getString("@name"));
				}
			} else {
				JSONObject json = (JSONObject) tbObj;
				json.put("@package", pkg);
				genCode(engine, mVigorDBItemTmpl, info.transfer, info.utils, info.destPath, json);
				tables.add(json.getString("@name"));
			}
		} catch (ScriptException e) {
			Log.e(TAG, "gen item code error : %s", e);
		}
		if (mVigorDBInfoDelegateTmpl == null || !info.templateLibs.equals(mTemplateLibs)) {
			mVigorDBInfoDelegateTmpl = FileOperation.read(info.templateLibs + "/VDBInfoDelegate.vigor-template");
			mTemplateLibs = info.templateLibs;
		}
		JSONObject delegateJson = new JSONObject();
		delegateJson.put("@package", pkg);
		delegateJson.put("@name", delegate);
		delegateJson.put("@dbItems", tables);
		try {
			genCode(engine, mVigorDBInfoDelegateTmpl, info.transfer, "", info.destPath, delegateJson);
		} catch (ScriptException e) {
			Log.e(TAG, "gen delegate code error : %s", e);
		}
		return null;
	}

	@Override
	public String getSupportSuffix() {
		return "vigor-define";
	}
	
	private void genCode(ScriptEngine engine, String template, String transfer, String utils, String destPath, JSONObject obj) throws ScriptException {
		String jsonStr = obj.toString().replace("@", "_");
		String script = transfer + utils + "\nparseTemplate(\"" + TemplateUtils.escape(template) + "\"," + jsonStr + ");";
		String dest = destPath + "/" + obj.getString("@package").replace('.', '/');
		String path = dest + "/" + obj.getString("@name") + ".java";

		File destFolder = new File(dest);
		if (!destFolder.exists()) {
			destFolder.mkdirs();
		}
		FileOperation.write(path, TemplateUtils.format(TemplateUtils.unescape((String) engine.eval(script))));
	}
}
