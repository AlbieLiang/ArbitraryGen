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

import java.io.File;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import cc.suitalk.arbitrarygen.template.base.BasePsychicWorker;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.StatisticManager;
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
					long startTime = System.currentTimeMillis();
					genCode(engine, mVigorDBItemTmpl, info.script, info.destPath, obj);
					StatisticManager.mark("GenCode", "[VigorDB]", (System.currentTimeMillis() - startTime));
					tables.add(obj.getString("@name"));
				}
			} else {
				JSONObject json = (JSONObject) tbObj;
				json.put("@package", pkg);
				long startTime = System.currentTimeMillis();
				genCode(engine, mVigorDBItemTmpl, info.script, info.destPath, json);
				StatisticManager.mark("GenCode", "[VigorDB]", (System.currentTimeMillis() - startTime));
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
			long startTime = System.currentTimeMillis();
			genCode(engine, mVigorDBInfoDelegateTmpl, info.script, info.destPath, delegateJson);
			StatisticManager.mark("GenCode", "[VigorDB]", (System.currentTimeMillis() - startTime));
		} catch (ScriptException e) {
			Log.e(TAG, "gen delegate code error : %s", e);
		}
		return null;
	}

	@Override
	public String getSupportSuffix() {
		return "vigor-define";
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
