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

package cc.suitalk.arbitrarygen.core;

import net.sf.json.JSONObject;

import cc.suitalk.arbitrarygen.extension.AGContext;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;

/**
 * Created by albieliang on 2017/11/13.
 */

public class Core {

    private static AGApplication sAGApplication;
    private static AGCore sAGCore;

    private static boolean sInitialized;

    public static final void initialize(ArbitraryGenInitializer initializer) {
        if (sInitialized) {
            return;
        }
        sInitialized = true;
        if (initializer == null) {
            return;
        }
        initializer.initialize();
        sAGApplication = initializer.getAGApplication();
        sAGCore = initializer.getAGCore();
    }

    public static void startTask(AGContext agContext, JSONObject args) {
        sAGCore.startTask(agContext, args);
    }

    public static JSONObject exec(ArbitraryGenProcessor processor, JSONObject args) {
        return sAGApplication.execProcess(processor, args);
    }

    public static JSONObject exec(String processorName, JSONObject args) {
        return sAGApplication.execProcess(sAGApplication.getProcessor(processorName), args);
    }

    public static ArbitraryGenProcessor getProcessor(String processorName) {
        return sAGApplication.getProcessor(processorName);
    }
}
