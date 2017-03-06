/*
 *  Copyright (C) 2016-present Albie Liang. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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

import cc.suitalk.arbitrarygen.template.base.BasePyroWorker;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.TemplateUtils;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class PyroGenTask extends BasePyroWorker {

	private static final String TAG = "AG.PyroGenTask";

	private List<String> mSupportSuffixList;

	public PyroGenTask(TemplateConfig cfg) {
		super(cfg);
		mSupportSuffixList = new LinkedList<>();
	}

	public PyroGenTask(TemplateConfig cfg, List<String> supportSuffixList) {
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
								genCode(engine, template, info.script, info.destPath, obj);
							}
							list.add(obj);
						}
					} else {
						JSONObject json = (JSONObject) tagObj;
						json.put("@package", pkg);
						if (!Util.isNullOrNil(template)) {
							genCode(engine, template, info.script, info.destPath, json);
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
			try {
				genCode(engine, template, info.script, info.destPath, delegateJson);
			} catch (ScriptException e) {
				Log.e(TAG, "gen code error : %s", e);
			}
			break;
		}
		return null;
	}

	@Override
	public List<String> getSupportSuffixList() {
		return mSupportSuffixList;
	}

	public void addSupportSuffixList(List<String> suffixList) {
		mSupportSuffixList.addAll(suffixList);
	}

	private String getValidateTag(String tag) {
		if (Util.isNullOrNil(tag)) {
			return tag;
		}
		return tag.replaceAll("-", "_");
	}

	private void genCode(ScriptEngine engine, String template, String script, String destPath, JSONObject obj) throws ScriptException {
		String jsonStr = obj.toString().replace("@", "_");
		String s = script + "\nparseTemplate(\"" + TemplateUtils.escape(template) + "\"," + jsonStr + ");";
		String dest = destPath + "/" + obj.getString("@package").replace('.', '/');
		String path = dest + "/" + obj.getString("@name") + ".java";

		File destFolder = new File(dest);
		if (!destFolder.exists()) {
			destFolder.mkdirs();
		}
		FileOperation.write(path, TemplateUtils.format(TemplateUtils.unescape((String) engine.eval(s))));
	}

}
