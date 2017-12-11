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

import cc.suitalk.arbitrarygen.core.JarClassLoaderWrapper;

/**
 * Created by AlbieLiang on 16/11/6.
 */
public interface AGContext {

    void initialize(JSONObject jsonObject);

    void start();

    void addProcessor(ArbitraryGenProcessor processor);

    ArbitraryGenProcessor removeProcessor(String name);

    ArbitraryGenProcessor getProcessor(String name);

    JSONObject execProcess(Map<String, ArbitraryGenProcessor> processors, String processorName, JSONObject args);

    JSONObject execProcess(ArbitraryGenProcessor processor, JSONObject args, Map<String, ArbitraryGenProcessor> deliverDepends);

    JSONObject execProcess(ArbitraryGenProcessor processor, JSONObject args);

    JarClassLoaderWrapper getJarClassLoader();
}
