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

import java.util.Map;

import cc.suitalk.arbitrarygen.extension.AGContext;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.utils.JSONArgsUtils;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 2016/12/2.
 */
public class PsychicTaskProcessor implements ArbitraryGenProcessor {

    private static final String TAG = "AG.PsychicTaskProcessor";

    @Override
    public String getName() {
        return "psychic-processor";
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
        JSONArray taskArray = JSONArgsUtils.getJSONArray(args, "PsychicTask", true);
        if (taskArray == null) {
            Log.i(TAG, "task array is null.");
            return null;
        }
        Log.d(TAG, "exec psychic task(%s)", args);
        for (int t = 0; t < taskArray.size(); t++) {
            JSONObject taskInfo = taskArray.optJSONObject(t);
            if (taskInfo == null) {
                Log.i(TAG, "task info is null.");
                continue;
            }
            String name = taskInfo.optString("_name");
            String processor = taskInfo.optString("_processor");
            if (Util.isNullOrNil(name) || Util.isNullOrNil(processor)) {
                Log.i(TAG, "task name(%s) or processor(%s) is null or nil.", name, processor);
                continue;
            }
            JSONArray dependsOnArray = JSONArgsUtils.getJSONArray(taskInfo, "dependsOn", true);
            if (dependsOnArray != null && !dependsOnArray.isEmpty()) {
                JSONObject contextJson = new JSONObject();
                for (int d = 0; d < dependsOnArray.size(); d++) {
                    JSONObject dependsOnInfo = dependsOnArray.optJSONObject(d);
                    if (dependsOnInfo == null) {
                        Log.i(TAG, "dependsOn info is null.");
                        continue;
                    }
                    String n = dependsOnInfo.optString("_name");
                    String p = dependsOnInfo.optString("_processor");
                    String type = dependsOnInfo.optString("_type");
                    if (Util.isNullOrNil(n) || Util.isNullOrNil(p)) {
                        Log.i(TAG, "the name(%s) or processor(%s) of dependsOn info is null or nil.", n, p);
                        continue;
                    }
                    Log.i(TAG, "the name(%s), processor(%s) and type(%s) of dependsOn info.", n, p, type);
                    if ("input".equals(type)) {
                        JSONObject r = context.execProcess(processors, p, dependsOnInfo);
                        if (r != null) {
                            contextJson.putAll(r);
                        }
                    }
                }
                taskInfo.put("context", contextJson);
            } else {
                Log.i(TAG, "dependsOn array is null.");
            }
            JSONObject result = context.execProcess(processors, processor, taskInfo);
            if (result == null) {
                Log.d(TAG, "execute task processor result is null, switch to next task.");
                continue;
            }
            result.putAll(taskInfo);
            JSONArray resultToArray = JSONArgsUtils.getJSONArray(taskInfo, "resultTo", true);
            if (resultToArray != null && !resultToArray.isEmpty()) {
                for (int d = 0; d < resultToArray.size(); d++) {
                    JSONObject resultToInfo = resultToArray.optJSONObject(d);
                    if (resultToInfo == null) {
                        Log.i(TAG, "resultTo info is null.");
                        continue;
                    }
                    String p = resultToInfo.optString("_processor");
                    if (Util.isNullOrNil(p)) {
                        Log.i(TAG, "the processor(%s) of resultTo info is null or nil.", p);
                        continue;
                    }
                    resultToInfo.putAll(result);
                    JSONObject r = context.execProcess(processors, p, resultToInfo);
                    Log.i(TAG, "the processor(%s) of resultTo info, execute result is : %s.", p, r);
                }
            } else {
                Log.i(TAG, "resultTo array is null.");
            }
        }
        return null;
    }

    @Override
    public void onError(AGContext context, int errorCode, String message) {
        Log.e(TAG, "do psychic processor error, code is '%d', message is '%s'", errorCode, message);
    }
}
