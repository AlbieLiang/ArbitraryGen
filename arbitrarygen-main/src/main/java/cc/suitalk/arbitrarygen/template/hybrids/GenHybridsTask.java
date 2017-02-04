package cc.suitalk.arbitrarygen.template.hybrids;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import cc.suitalk.arbitrarygen.template.DelayReadFileTask;
import cc.suitalk.arbitrarygen.template.TaskInfo;
import cc.suitalk.arbitrarygen.template.TemplateConfig;
import cc.suitalk.arbitrarygen.template.TemplateManager;
import cc.suitalk.arbitrarygen.template.base.BasePsychicWorker;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.HybridsTemplateUtils;
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
public class GenHybridsTask extends BasePsychicWorker {

	private static final String TAG = "AG.GenHybridsTask";
	
	public GenHybridsTask(TemplateConfig cfg) {
		super(cfg);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String genCode(ScriptEngine engine, JSONObject jsonObj, TaskInfo info) {
		String pkg = jsonObj.optString("@package", "");
		String delegate = jsonObj.optString("@delegate", "");
		String delegateDest = jsonObj.optString("@delegateDest", "");
		String delegateSuffix = jsonObj.optString("@delegateSuffix", "");
		String rawTags = jsonObj.optString("@tag", "");

		if (Util.isNullOrNil(delegateSuffix)) {
			delegateSuffix = "java";
		}

		JSONObject delegateJson = new JSONObject();
		delegateJson.put("@package", pkg);
		delegateJson.put("@name", delegate);
		delegateJson.put("@suffix", delegateSuffix);

		String[] tags = null;

		Log.d(TAG, "tags : %s", rawTags);
		if (!Util.isNullOrNil(rawTags)) {
			tags = rawTags.split(",");
		} else {
			Set<String> list = new HashSet<>();
			for (String key : (Set<String>) jsonObj.keySet()) {
				Log.d(TAG, "key : %s", key);
				if (key != null && !key.startsWith("@")) {
					list.add(key);
				}
			}
			if (!list.isEmpty()) {
				tags = new String[list.size()];
				int i = 0;
				for (String tag : list) {
					tags[i++] = tag;
				}
			}
		}
		if (tags != null) {
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
					Log.i(TAG, "start gen code, tagName : %s.", tagName);
					if (tagObj instanceof JSONArray) {
						JSONArray arr = (JSONArray) tagObj;
						for (int j = 0, len = arr.size(); j < len; j++) {
							JSONObject obj = arr.getJSONObject(j);
							obj.put("@package", pkg);
							if (!Util.isNullOrNil(template)) {
								genCode(engine, template, info.transfer, info.utils, info.destPath, obj);
							}
							list.add(obj);
						}
					} else {
						JSONObject json = (JSONObject) tagObj;
						json.put("@package", pkg);
						if (!Util.isNullOrNil(template)) {
							genCode(engine, template, info.transfer, info.utils, info.destPath, json);
						}
						list.add(json);
					}
				} catch (ScriptException e) {
					Log.e(TAG, "gen code error : %s", e);
				}
				delegateJson.put("@" + getValidateTag(tagName) + "s", list);
			}
		}
		if (!Util.isNullOrNil(delegate)) {
			String template = FileOperation.read(delegateDest + "/" + delegate + "." + delegateSuffix);
			String path = info.coreLibs + "/Hybrids-TransferTools.js";
			String transferTools = TemplateManager.getImpl().get(path, new DelayReadFileTask(path));
			try {
				genCodeAndPrint(engine, template, transferTools, "", delegateDest, delegateJson);
			} catch (ScriptException e) {
				Log.e(TAG, "gen code error : %s", e);
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
		String script = transfer + utils + "\nparseTemplate(\"" + HybridsTemplateUtils.escape(template) + "\"," + jsonStr + ");";
//		String dest = destPath + "/" + obj.getString("@package").replace('.', '/');
		String path = destPath + "/" + obj.getString("@name") + "." + obj.getString("@suffix");

		File destFolder = new File(destPath);
		if (!destFolder.exists()) {
			destFolder.mkdirs();
		}
		Log.v(TAG, "genCode, dest : %s, path : %s", destPath, path);
		String outStr = HybridsTemplateUtils.unescape((String) engine.eval(script));
		FileOperation.write(path, "" + outStr);
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
		Log.d(TAG, "dest : %s, path : %s", dest, path);
		FileOperation.write(path, TemplateUtils.format(TemplateUtils.unescape((String) engine.eval(script))));
	}
}
