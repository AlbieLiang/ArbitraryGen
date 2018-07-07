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

import cc.suitalk.arbitrarygen.extension.AGContext;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 2016/12/2.
 */

public class PsychicTaskProcessor implements ArbitraryGenProcessor {

    private static final String TAG = "AG.PsychicTaskProcessor";

    @Override
    public String getName() {
        return "psychic-task-processor";
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
        JSONObject dependsOn = args.optJSONObject("context");
        if (dependsOn == null) {
            Log.w(TAG, "dependsOn is null");
            return null;
        }
        JSONObject result = new JSONObject();
        result.putAll(args);
        JSONObject splitter = args.optJSONObject("splitter");
        if (splitter != null) {
            String resultKey = splitter.optString("_resultKey");
            String processor = splitter.optString("_processor");
            if (!Util.isNullOrNil(resultKey) && !Util.isNullOrNil(processor)) {
                JSONObject splitArgs = new JSONObject();
                splitArgs.putAll(splitter);
                splitArgs.put("context", dependsOn);
                JSONObject splitResult = context.execProcess(context.getAllProcessor(), processor, splitArgs);
                Log.i(TAG, "processor : %s, splitResult : %s", processor, splitResult);
                if (splitResult != null) {
                    dependsOn.putAll(splitResult);
                }
                result.put("context", dependsOn);
            }
        }
        Log.i(TAG, "result : %s", result);
        return result;
    }

    @Override
    public void onError(AGContext context, int errorCode, String message) {
        Log.e(TAG, "do psychic processor error, code is '%d', message is '%s'", errorCode, message);
    }
}
