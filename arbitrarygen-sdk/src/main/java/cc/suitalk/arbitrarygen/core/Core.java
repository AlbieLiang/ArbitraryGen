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

import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;

/**
 * Created by albieliang on 2017/11/13.
 */

public class Core {

    private static AGCore sAGCore;

    public static final void setAGCore(AGCore agCore) {
        sAGCore = agCore;
    }

    public static JSONObject exec(ArbitraryGenProcessor processor, JSONObject args) {
        return sAGCore.execProcess(processor, args);
    }

    public static JSONObject exec(String processorName, JSONObject args) {
        return sAGCore.execProcess(sAGCore.getProcessor(processorName), args);
    }
}
