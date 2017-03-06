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

package cc.suitalk.arbitrarygen.engine;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenEngine;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.processor.ScannerAGProcessor;
import cc.suitalk.arbitrarygen.template.GenVigorDBTask;
import cc.suitalk.arbitrarygen.template.JsTemplateProcessor;
import cc.suitalk.arbitrarygen.template.PsychicGenTask;
import cc.suitalk.arbitrarygen.template.PyroGenTask;
import cc.suitalk.arbitrarygen.template.TemplateConfig;
import cc.suitalk.arbitrarygen.template.base.TemplateProcessor;
import cc.suitalk.arbitrarygen.template.hybrids.GenHybridsTask;
import cc.suitalk.arbitrarygen.utils.JSONArgsUtils;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/2.
 */
public class ScriptTemplateAGEngine implements ArbitraryGenEngine {

    private static final String TAG = "AG.ScriptTemplateAGEngine";

    private TemplateProcessor mTemplateProcessor;
    private TemplateConfig mTemplateConfig;

    @Override
    public String getName() {
        return "scriptEngine";
    }

    @Override
    public void initialize(AGCore core, JSONObject args) {
        if (args == null) {
            return;
        }
        // For script template engine
        boolean enable = args.optBoolean(ArgsConstants.EXTERNAL_ARGS_KEY_ENABLE, true);
        if (!enable) {
            return;
        }
        String libsDir = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_LIBS_DIR);
        String templateLibs = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_TEMPLATE_LIBS, libsDir + "/template-libs");

        JSONArray suffixList = JSONArgsUtils.getJSONArray(args, ArgsConstants.EXTERNAL_ARGS_KEY_FORMAT, true);
        List<String> list = new LinkedList<>();
        if (suffixList != null) {
            for (int i = 0; i < suffixList.size(); i++) {
                String suffix = suffixList.optString(i);
                if (Util.isNullOrNil(suffix)) {
                    continue;
                }
                list.add(suffix);
            }
        }
        if (!Util.isNullOrNil(templateLibs)) {
            mTemplateConfig = new TemplateConfig(templateLibs);
            mTemplateProcessor = new JsTemplateProcessor();

            mTemplateProcessor.addTaskWorker(new GenVigorDBTask(mTemplateConfig));
            mTemplateProcessor.addTaskWorker(new GenHybridsTask(mTemplateConfig));
            mTemplateProcessor.addTaskWorker(new PsychicGenTask(mTemplateConfig));
            mTemplateProcessor.addTaskWorker(new PyroGenTask(mTemplateConfig, list));
        }
    }

    @Override
    public String[] getDependencies() {
        return new String[] { "scanner" };
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        if (mTemplateProcessor == null) {
            return null;
        }
        final TemplateProcessor tp = mTemplateProcessor;
        JSONArray formatArray = new JSONArray();
        List<String> suffixList = tp.getSupportSuffixList();
        for (String suffix : suffixList) {
            formatArray.add(suffix);
        }
        Log.v(TAG, "execute, args(%s)", args);
        JSONObject argsJSONObject = new JSONObject();
        argsJSONObject.put(ScannerAGProcessor.KEY_SCAN_MODE, ScannerAGProcessor.SCAN_MODE_CLASSIFY);
        argsJSONObject.put(ScannerAGProcessor.KEY_SRC_DIR, args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_SRC));
        argsJSONObject.put(ScannerAGProcessor.KEY_SUFFIX_LIST, formatArray);

        JSONObject jsonObject = core.execProcess(processors, "scanner", argsJSONObject);
        if (jsonObject == null) {
            return null;
        }
        Set<String> keySet = jsonObject.keySet();
        if (keySet == null || keySet.isEmpty()) {
            return null;
        }
        final String destPath = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_DEST);
        tp.prepare(mTemplateConfig);
        for (String suffix : keySet) {
            JSONArray pathArray = jsonObject.optJSONArray(suffix);
            if (pathArray == null || pathArray.isEmpty()) {
                continue;
            }
            for (int i = 0; i < pathArray.size(); i++) {
                String path = pathArray.optString(i);
                if (Util.isNullOrNil(path)) {
                    continue;
                }
                File file = new File(path);
                path = file.getAbsolutePath();
                Log.v(TAG, "do process file(%s).", path);
                tp.process(path, destPath);
            }
        }
        return null;
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }
}
