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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.Map;

import cc.suitalk.arbitrarygen.analyzer.JavaFileLexer;
import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/16.
 */
public class ParseJavaFileProcessor implements ArbitraryGenProcessor {

    private static final String TAG = "AG.ParseJavaFileProcessor";

    @Override
    public String getName() {
        return "parse-java";
    }

    @Override
    public void initialize(AGCore core, JSONObject args) {
    }

    @Override
    public String[] getDependencies() {
        return new String[] { "parse-rule" };
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        String name = args.optString("_name");
        if (Util.isNullOrNil(name)) {
            Log.i(TAG, "exec failed, name is null.");
            return null;
        }
        JSONObject result = core.execProcess(processors, "parse-rule", args);
        if (result == null) {
            Log.i(TAG, "parse rule result is null.");
            return null;
        }
        JSONArray fileArray = result.optJSONArray("fileArray");
        if (fileArray == null) {
            Log.i(TAG, "file array is null.");
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < fileArray.size(); i++) {
            String path = fileArray.optString(i);
            JavaFileLexer lexer = new JavaFileLexer(new File(path));
            JavaFileObject javaFileObject = lexer.start();
            if (javaFileObject != null) {
                jsonArray.add(javaFileObject.toJSONObject());
            }
        }
        JSONObject r = new JSONObject();
        r.put(name, jsonArray);
        return r;
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }
}
