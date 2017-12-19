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

import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.extension.AGContext;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.debug.Debuger;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/6.
 */
public class LoggerAGProcessor implements ArbitraryGenProcessor {

    private static final String TAG = "AG.LoggerAGProcessor";

    @Override
    public String getName() {
        return "logger";
    }

    @Override
    public void initialize(AGContext context, JSONObject args) {
        boolean toFile = args == null || args.optBoolean(ArgsConstants.EXTERNAL_ARGS_KEY_TO_FILE, true);
        if (toFile) {
            String logFile = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_PATH);
            if (Util.isNullOrNil(logFile)) {
                logFile = "./output/ag.log";
            }
            Log.setPath(logFile);
            Log.prepare();
        }
        if (args != null) {
            // Common Arguments
            Debuger.debug = args.optBoolean(ArgsConstants.EXTERNAL_ARGS_KEY_LOG_DEBUG);
            Log.setPrintLogLevel(args.optInt(ArgsConstants.EXTERNAL_ARGS_KEY_LOG_LEVEL, Log.LOG_LEVEL_N));
            Log.setPrintTag(args.optBoolean(ArgsConstants.EXTERNAL_ARGS_KEY_LOG_PRINT_TAG));
            Log.setPrintLevel(args.optBoolean(ArgsConstants.EXTERNAL_ARGS_KEY_LOG_PRINT_LEVEL));
        }
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public JSONObject exec(AGContext context, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        return null;
    }

    @Override
    public void onError(AGContext context, int errorCode, String message) {
        Log.e(TAG, "do Logger process error, code is '%d', message is '%s'", errorCode, message);
    }
}
