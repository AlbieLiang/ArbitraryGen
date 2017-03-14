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

import java.io.File;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import cc.suitalk.arbitrarygen.constant.ResConstants;
import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.template.DelayReadResFileTask;
import cc.suitalk.arbitrarygen.template.TemplateManager;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.TemplateUtils;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/16.
 */
public class TemplateProcessor implements ArbitraryGenProcessor {

    private static final String TAG ="AG.TemplateProcessor";

    private ScriptEngine mScriptEngine;
    private String mCoreScript;

    public TemplateProcessor() {
        mScriptEngine = new ScriptEngineManager().getEngineByName("javascript");
    }

    @Override
    public String getName() {
        return "template-processor";
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
        String templateTag = args.optString("templateTag");
        if (Util.isNullOrNil(templatePath) && Util.isNullOrNil(templateTag)) {
            Log.w(TAG, "exec failed, template path and templateTag is null or nil.");
            return null;
        }
        String template = null;
        if (!Util.isNullOrNil(templateTag)) {
            template = TemplateManager.getImpl().get(templateTag);
        }
        if (Util.isNullOrNil(template)) {
            template = FileOperation.read(templatePath);
        }
        if (Util.isNullOrNil(template)) {
            Log.i(TAG, "template is null or nil.");
            return null;
        }
        final ScriptEngine engine = mScriptEngine;
        String destDir = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_DEST_DIR);
        String jsonStr = args.toString();
        String script = mCoreScript + "\nparseTemplate(\"" + TemplateUtils.escape(template) + "\"," + jsonStr + ");";
        String path = destDir + "/" + args.getString("toFile");

        File destFolder = new File(path).getParentFile();
        if (!destFolder.exists()) {
            destFolder.mkdirs();
        }
//		Log.d(TAG, "jsonStr : %s\n", jsonStr);
//		Log.d(TAG, "script : %s\n", script);
        Log.d(TAG, "dest : %s\n", destDir);
        Log.d(TAG, "path : %s\n", path);
        try {
            FileOperation.write(path, TemplateUtils.format(TemplateUtils.unescape((String) engine.eval(script))));
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
