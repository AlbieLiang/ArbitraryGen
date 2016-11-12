package cc.suitalk.arbitrarygen.template;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import cc.suitalk.arbitrarygen.template.base.BasePsychicWorker;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class PsychicGenTask extends BasePsychicWorker {

	private static final String TAG = "AG.PsychicGenTask";

	private List<String> mSupportSuffixList;

	public PsychicGenTask(TemplateConfig cfg) {
		super(cfg);
		mSupportSuffixList = new LinkedList<>();
	}

	public PsychicGenTask(TemplateConfig cfg, List<String> supportSuffixList) {
		this(cfg);
		addSupportSuffixList(supportSuffixList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String genCode(ScriptEngine engine, JSONObject jsonObj, TaskInfo info) {
		String pkg = jsonObj.optString("@package", "");
		String delegate = jsonObj.optString("@delegate", "");
		String rawTags = jsonObj.optString("@tag", "");
		String rootTag = jsonObj.optString("@delegateTag", null);

		JSONObject delegateJson = new JSONObject();
		delegateJson.put("@package", pkg);
		delegateJson.put("@name", delegate);

		String[] tags = null;

		if (!Util.isNullOrNil(rawTags)) {
			tags = rawTags.split(",");
		} else {
			Set<String> list = new HashSet<>();
			for (String key : (Set<String>) jsonObj.keySet()) {
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
		while (!Util.isNullOrNil(delegate)) {
			String template = TemplateManager.getImpl().get(rootTag);
			if (Util.isNullOrNil(template)) {
				Log.w(TAG, "the template do not exist with tag : %s", rootTag);
				break;
			}
			String path = info.coreLibs + "/TransferTools.js";
			String transferTools = TemplateManager.getImpl().get(path, new DelayReadFileTask(path));
			try {
				genCode(engine, template, transferTools, "", info.destPath, delegateJson);
			} catch (ScriptException e) {
				Log.e(TAG, "gen code error : %s", e);
			}
			break;
		}
		return null;
	}

	private String getValidateTag(String tag) {
		if (Util.isNullOrNil(tag)) {
			return tag;
		}
		return tag.replaceAll("-", "_");
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

	@Override
	public List<String> getSupportSuffixList() {
		return mSupportSuffixList;
	}

	public void addSupportSuffixList(List<String> suffixList) {
		mSupportSuffixList.addAll(suffixList);
	}
}
