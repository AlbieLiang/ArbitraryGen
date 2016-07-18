package osc.innovator.arbitrarygen.template.hybrids;

import java.io.File;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import osc.innovator.arbitrarygen.engine.ScriptTemplateGenCodeEngine.TaskInfo;
import osc.innovator.arbitrarygen.template.DelayReadFileTask;
import osc.innovator.arbitrarygen.template.TemplateConfig;
import osc.innovator.arbitrarygen.template.TemplateManager;
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
public class GenHybridsTask extends BaseGenCodeWorker {

	private static final String TAG = "CodeGen.GenHybridsTask";
	
	public static final String LINEFEED_CODE = "\\x0a";
	public static final String CARRIAGE_RETURN_CODE = "\\x0d";
	
	public GenHybridsTask(TemplateConfig cfg) {
		super(cfg);
	}
	
	@Override
	public String genCode(ScriptEngine engine, JSONObject jsonObj, TaskInfo info) {
		String pkg = jsonObj.optString("@package", "");
		String delegate = jsonObj.optString("@delegate", "");
		String delegateDest = jsonObj.optString("@delegateDest", "");
//		String delegatePkg = jsonObj.optString("@delegatePkg", "");
		String delegateSuffix = jsonObj.optString("@delegateSuffix", "");
		String rawTags = jsonObj.optString("@tag", "");

//		if (Util.isNullOrNil(delegate)) {
//			throw new RuntimeException("delegate is null or nil.");
//		}
//		if (Util.isNullOrNil(delegateDest)) {
//			throw new RuntimeException("delegate dest is null or nil.");
//		}
		if (Util.isNullOrNil(rawTags)) {
			throw new RuntimeException("tag is null or nil.");
		}
//		if (Util.isNullOrNil(delegatePkg)) {
//			delegatePkg = pkg;
//		}
		if (Util.isNullOrNil(delegateSuffix)) {
			delegateSuffix = "java";
		}

		JSONObject delegateJson = new JSONObject();
		delegateJson.put("@package", pkg);
		delegateJson.put("@name", delegate);
		delegateJson.put("@suffix", delegateSuffix);
		
		String[] tags = rawTags.split(",");
		for (int i = 0; i < tags.length; i++) {
			String tagName = tags[i];
			Object tagObj = jsonObj.get(tagName);
			if (tagObj == null) {
				Log.w(TAG, "the JSON object do not exist with tag : %s", tagName);
				continue;
			}
			JSONArray list = new JSONArray();
			try {
				String template = TemplateManager.getImpl().get(tagName);
				if (Util.isNullOrNil(template)) {
					Log.w(TAG, "the template do not exist with tag : %s", tagName);
					continue;
				}
				Log.i(TAG, "start gen code, tagName : %s, template : %s.", tagName, template);
				if (tagObj instanceof JSONArray) {
					JSONArray arr = (JSONArray) tagObj;
					for (int j = 0, len = arr.size(); j < len; j++) {
						JSONObject obj = arr.getJSONObject(j);
						obj.put("@package", pkg);
						if (!Util.isNullOrNil(template)) {
							genCode(engine, template, info.transfer, info.utils, info.destPath, obj);
						}
//						list.add(obj.getString("@name"));
						list.add(obj);
					}
				} else {
					JSONObject json = (JSONObject) tagObj;
					json.put("@package", pkg);
					if (!Util.isNullOrNil(template)) {
						genCode(engine, template, info.transfer, info.utils, info.destPath, json);
					}
//					list.add(json.getString("@name"));
					list.add(json);
				}
			} catch (ScriptException e) {
				e.printStackTrace();
			}
			delegateJson.put("@" + getValidateTag(tagName) + "s", list);
		}
		
		if (!Util.isNullOrNil(delegate)) {
			String template = FileOperation.read(delegateDest + "/" + delegate + "." + delegateSuffix);
			String path = info.coreLibs + "/Hybrids-TransferTools.js";
			String transferTools = TemplateManager.getImpl().get(path, new DelayReadFileTask(path));
			try {
				genCodeAndPrint(engine, template, transferTools, "", delegateDest, delegateJson);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getSupportSuffix() {
		return "hybrids-define";
	}

	private String getValidateTag(String tag) {
		if (Util.isNullOrNil(tag)) {
			return tag;
		}
		return tag.replaceAll("-", "_");
	}
	
	private void genCodeAndPrint(ScriptEngine engine, String template,
			String transfer, String utils, String destPath, JSONObject obj) throws ScriptException {
		String jsonStr = obj.toString().replace("@", "_");
		String script = transfer + utils + "\nparseTemplate(\"" + escape(template) + "\"," + jsonStr + ");";
//		String dest = destPath + "/" + obj.getString("@package").replace('.', '/');
		String path = destPath + "/" + obj.getString("@name") + "." + obj.getString("@suffix");

		File destFolder = new File(destPath);
		if (!destFolder.exists()) {
			destFolder.mkdirs();
		}
		Log.v(TAG, "genCode, jsonStr : %s", jsonStr);
		Log.v(TAG, "genCode, script : %s", script);
		Log.v(TAG, "genCode, dest : %s", destPath);
		Log.v(TAG, "genCode, path : %s", path);
		String outStr = unescape((String) engine.eval(script));
//		String outStr = format(unescape((String) engine.eval(script)));
		Log.v(TAG, "genCode, outStr : %s", outStr);
		
//		Log.v(TAG, "\n\n\n\n\nTransfer result : \n\n\n\n\n\n" + unescape((String) engine.eval(transfer + utils + "\ntransfer(\"" + escape(template) + "\"," + jsonStr + ");")));
		FileOperation.write(path, "" + outStr);
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
		Log.d(TAG, "jsonStr : %s\n", jsonStr);
		Log.d(TAG, "script : %s\n", script);
		Log.d(TAG, "dest : %s\n", dest);
		Log.d(TAG, "path : %s\n", path);
		FileOperation.write(path, format(unescape((String) engine.eval(script))));
	}

	public static String escape(String str) {
//		return str.replaceAll("(\r\n)+", "\\x0a").replaceAll("\"", "\\\\\"").replaceAll("\'", "\\x29");
		return str.replaceAll("\r", CARRIAGE_RETURN_CODE)
				.replaceAll("\n", LINEFEED_CODE)
				.replaceAll("\"", "\\\\\"")
				.replaceAll("\'", "\\x29");
	}
	
	public static String unescape(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
//		return str.replaceAll("(x0a[ ]*)+", "\r\n").replace("x29", "'");
		return str.replaceAll("x0d", "\r").replaceAll("x0a", "\n").replace("x29", "'");
	}

	public static String format(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
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
