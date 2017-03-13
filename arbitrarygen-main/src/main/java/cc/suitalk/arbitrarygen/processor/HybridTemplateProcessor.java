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

package cc.suitalk.arbitrarygen.processor;

import net.sf.json.JSONObject;

import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import cc.suitalk.arbitrarygen.constant.ResConstants;
import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.template.DelayReadResFileTask;
import cc.suitalk.arbitrarygen.template.TemplateManager;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.HybridsTemplateUtils;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/16.
 */
public class HybridTemplateProcessor implements ArbitraryGenProcessor {

    private static final String TAG ="AG.HybridTemplateProcessor";

    private ScriptEngine mScriptEngine;
    private String mCoreScript;

    public HybridTemplateProcessor() {
        mScriptEngine = new ScriptEngineManager().getEngineByName("javascript");
    }

    @Override
    public String getName() {
        return "hybrid-template-processor";
    }

    @Override
    public void initialize(AGCore core, JSONObject args) {
        mCoreScript = TemplateManager.getImpl().get(
                ResConstants.PATH_CORE_SCRIPT, new DelayReadResFileTask(ResConstants.PATH_CORE_SCRIPT));
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        String templatePath = args.optString("template");
        if (Util.isNullOrNil(templatePath)) {
            Log.w(TAG, "exec failed, template path is null or nil.");
            return null;
        }
        String template = FileOperation.read(templatePath);
        if (Util.isNullOrNil(template)) {
            Log.i(TAG, "template(path: %s) is null or nil.", templatePath);
            return null;
        }
        final ScriptEngine engine = mScriptEngine;
        String jsonStr = args.toString();
        String script = mCoreScript + "\nparseHybridsTemplate(\"" + HybridsTemplateUtils.escape(template) + "\"," + jsonStr + ");";
        final String path = templatePath;
//		Log.d(TAG, "jsonStr : %s\n", jsonStr);
//		Log.d(TAG, "script : %s\n", script);
        Log.d(TAG, "path : %s\n", path);
        try {
            FileOperation.write(path, HybridsTemplateUtils.format(HybridsTemplateUtils.unescape((String) engine.eval(script))));
            Log.i(TAG, "genCode into file %s successfully.", path);
        } catch (ScriptException e) {
            Log.e(TAG, "eval script error, exception : %s", e);
        }
        return null;
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }
}
