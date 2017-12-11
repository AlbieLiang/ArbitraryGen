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

package cc.suitalk.arbitrarygen.extension;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by AlbieLiang on 16/10/27.
 */
public interface ArbitraryGenProcessor {

    /**
     * Get Engine name.
     *
     * @return can not be null or nil
     */
    String getName();

    /**
     * Initialize the Engine with arguments.
     *
     * @param core the ArbitraryGen Engine Core
     * @param args input arguments
     */
    void initialize(AGContext core, JSONObject args);

    /**
     * Get dependencies {@link ArbitraryGenProcessor}'s name array.
     *
     * @return the dependencies processors of this processor
     */
    String[] getDependencies();

    /**
     * The real time to execute the Engine logic.
     *
     * @param core the ArbitraryGen Engine Core
     * @param processors dependencies processors
     * @param args input arguments
     * @return if has result then return a {@link JSONObject}, otherwise return null.
     */
    JSONObject exec(AGContext core, Map<String, ArbitraryGenProcessor> processors, JSONObject args);

    /**
     * When error occur during the process.
     *
     * @param errorCode code for different error case
     * @param message error message
     */
    void onError(int errorCode, String message);

    interface ErrorCode {
        int MISSING_DEPENDENCIES = 1;
    }
}
