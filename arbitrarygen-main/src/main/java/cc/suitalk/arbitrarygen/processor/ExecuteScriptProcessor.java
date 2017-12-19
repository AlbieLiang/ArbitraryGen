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

import cc.suitalk.arbitrarygen.extension.AGContext;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/16.
 */
public class ExecuteScriptProcessor implements ArbitraryGenProcessor {

    private static final String TAG ="AG.ExecuteScriptProcessor";

    private ScriptEngine mScriptEngine;

    public ExecuteScriptProcessor() {
        mScriptEngine = new ScriptEngineManager().getEngineByName("javascript");
    }

    @Override
    public String getName() {
        return "script-executor";
    }

    @Override
    public void initialize(AGContext context, JSONObject args) {
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public JSONObject exec(AGContext context, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        String path = args.optString("script");
        if (Util.isNullOrNil(path)) {
            Log.w(TAG, "exec failed, script path is null or nil.");
            return null;
        }
        String script = FileOperation.read(path);
        if (Util.isNullOrNil(script)) {
            Log.i(TAG, "script is null or nil.");
            return null;
        }
        JSONObject jsonObject = null;
        ScriptEngine engine = mScriptEngine;
        try {
            Object o = engine.eval(script);
            if (o instanceof JSONObject) {
                jsonObject = (JSONObject) o;
            } else if (o instanceof String) {
                jsonObject = JSONObject.fromObject(o);
            }
        } catch (ScriptException e) {
            Log.e(TAG, "eval script error, exception : %s", e);
        }
        return jsonObject;
    }

    @Override
    public void onError(AGContext context, int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }
}
