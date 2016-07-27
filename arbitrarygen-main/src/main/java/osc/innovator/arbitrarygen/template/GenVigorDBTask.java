package osc.innovator.arbitrarygen.template;

import java.io.File;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import osc.innovator.arbitrarygen.engine.ScriptTemplateGenCodeEngine.TaskInfo;
import osc.innovator.arbitrarygen.template.base.BaseGenCodeWorker;
import osc.innovator.arbitrarygen.utils.FileOperation;
import osc.innovator.arbitrarygen.utils.Log;
import osc.innovator.arbitrarygen.utils.Util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author AlbieLiang
 *
 */
public class GenVigorDBTask extends BaseGenCodeWorker {

	private static final String TAG = "CodeGen.GenVigorDBTask";
	
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
			delegate = "AutoGen_VDBInfoDelegate";
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getSupportSuffix() {
		return "vigor-define";
	}
	
	private void genCode(ScriptEngine engine, String template, String transfer, String utils, String destPath, JSONObject obj) throws ScriptException {
		String jsonStr = obj.toString().replace("@", "_");
		String script = transfer + utils + "\nparseTemplate(\"" + escape(template) + "\"," + jsonStr + ");";
		String dest = destPath + "/" + obj.getString("@package").replace('.', '/');
		String path = dest + "/" + obj.getString("@name") + ".java";

		File destFolder = new File(dest);
		if (!destFolder.exists()) {
			destFolder.mkdirs();
		}
//		Log.d(TAG, "jsonStr : %s\n", jsonStr);
//		Log.d(TAG, "script : %s\n", script);
//		Log.d(TAG, "dest : %s\n", dest);
//		Log.d(TAG, "path : %s\n", path);
		FileOperation.write(path, format(unescape((String) engine.eval(script))));
	}

	public static String escape(String str) {
		return str.replaceAll("[\r\n]+", "\\x0a").replaceAll("\"", "\\\\\"").replaceAll("\'", "\\x29");
	}
	
	public static String unescape(String str) {
		return str.replaceAll("(x0a[ ]*)+", "\r\n").replace("x29", "'");
	}

	public static String format(String str) {
		String indent = "";
		String vary = "    ";
		StringBuilder sb = new StringBuilder();
		String[] sps = str.split("\r\n");
		
		for (int i = 0, len = sps.length; i < len; i++) {
			String sp = sps[i].trim();
			if (sp.startsWith("}}")) {
				indent = indent.replaceFirst(vary, "");
				indent = indent.replaceFirst(vary, "");
			} else if (sp.startsWith("}")) {
				indent = indent.replaceFirst(vary, "");
			}
			sb.append(indent);
			sb.append(sp);
			if (sp.endsWith("{")) {
				indent += vary;
			}
			sb.append("\r\n");
		}
		return sb.toString();
	}
}
