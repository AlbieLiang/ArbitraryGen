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

package cc.suitalk.tools.arbitrarygen.demo;

import net.sf.json.JSONObject;

import java.util.Map;

import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenEngine;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.utils.Log;

/**
 * Created by AlbieLiang on 16/11/6.
 */
public class TestEngine implements ArbitraryGenEngine {

    private static final String TAG = "AG.TestEngine";

    @Override
    public String getName() {
        return "testEngine";
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        Log.i(TAG, "execute engine(%s), args(%s)", getName(), args);
        return null;
    }

    @Override
    public void initialize(AGCore core, JSONObject args) {
        Log.i(TAG, "initialize engine(%s), args(%s)", getName(), args);
    }
}
