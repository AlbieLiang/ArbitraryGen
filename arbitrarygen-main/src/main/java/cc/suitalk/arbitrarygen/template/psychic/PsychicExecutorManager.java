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

package cc.suitalk.arbitrarygen.template.psychic;

import java.util.HashMap;
import java.util.Map;

import cc.suitalk.arbitrarygen.template.psychic.base.PsychicExecutor;

/**
 * Created by AlbieLiang on 16/11/14.
 */
public class PsychicExecutorManager {

    private Map<String, PsychicExecutor> mPsychicExecutorMap;

    public PsychicExecutorManager() {
        mPsychicExecutorMap = new HashMap<>();
    }

    public boolean addPsychicExecutor(PsychicExecutor executor) {
        if (executor == null || mPsychicExecutorMap.containsKey(executor.getName())) {
            return false;
        }
        mPsychicExecutorMap.put(executor.getName(), executor);
        return true;
    }

    public PsychicExecutor removePsychicExecutor(String name) {
        if (name == null) {
            return null;
        }
        return mPsychicExecutorMap.remove(name);
    }

    public PsychicExecutor getPsychicExecutor(String name) {
        return mPsychicExecutorMap.get(name);
    }
}
