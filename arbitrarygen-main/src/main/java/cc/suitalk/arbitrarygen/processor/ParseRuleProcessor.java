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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.extension.AGContext;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.rule.Project;
import cc.suitalk.arbitrarygen.rule.Rule;
import cc.suitalk.arbitrarygen.rule.RuleParser;
import cc.suitalk.arbitrarygen.utils.JSONArgsUtils;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/16.
 */
public class ParseRuleProcessor implements ArbitraryGenProcessor {

    private static final String TAG = "AG.ParseRuleProcessor";

    @Override
    public String getName() {
        return "parse-rule";
    }

    @Override
    public void initialize(AGContext core, JSONObject args) {
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public JSONObject exec(AGContext core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        JSONObject result = new JSONObject();
        JSONArray fileArray = new JSONArray();
        JSONArray ruleFileArray = JSONArgsUtils.getJSONArray(args, ArgsConstants.EXTERNAL_ARGS_KEY_RULE_FILE, true);
        if (ruleFileArray != null) {
            List<String> pathList = new LinkedList<>();
            for (int i = 0; i < ruleFileArray.size(); i++) {
                String ruleFileArg = ruleFileArray.optString(i);
                if (Util.isNullOrNil(ruleFileArg)) {
                    Log.i(TAG, "rule file arg is null or nil.");
                    continue;
                }
                Log.i(TAG, "parse rule file(%s)", ruleFileArg);
                pathList.addAll(RuleParser.parseAndScan(ruleFileArg));
            }
            for (int j = 0; j < pathList.size(); j++) {
                String path = pathList.get(j);
                fileArray.add(path);
            }
        }
        JSONArray ruleArray = JSONArgsUtils.getJSONArray(args, ArgsConstants.EXTERNAL_ARGS_KEY_RULE, true);
        if (ruleArray != null) {
            List<String> pathList = new LinkedList<>();
            for (int i = 0; i < ruleArray.size(); i++) {
                String ruleArg = ruleArray.optString(i);
                if (Util.isNullOrNil(ruleArg)) {
                    Log.i(TAG, "rule arg is null or nil.");
                    continue;
                }
                Log.i(TAG, "parse rule(%s)", ruleArg);
                Project project = new Project();
                project.addRule(new Rule(ruleArg));
                pathList.addAll(RuleParser.scan(project));
            }
            for (int j = 0; j < pathList.size(); j++) {
                String path = pathList.get(j);
                fileArray.add(path);
            }
        }
        result.put("fileArray", fileArray);
        return result;
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }
}
