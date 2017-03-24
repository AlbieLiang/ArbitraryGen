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
import net.sf.json.xml.XMLSerializer;

import java.io.File;
import java.util.Map;

import cc.suitalk.arbitrarygen.analyzer.JavaFileLexer;
import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenEngine;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.extension.psychic.DependsOn;
import cc.suitalk.arbitrarygen.extension.psychic.ParseJavaRule;
import cc.suitalk.arbitrarygen.extension.psychic.ParseJsonRule;
import cc.suitalk.arbitrarygen.extension.psychic.ParseXmlRule;
import cc.suitalk.arbitrarygen.extension.psychic.PsychicTask;
import cc.suitalk.arbitrarygen.processor.ScannerAGProcessor;
import cc.suitalk.arbitrarygen.statement.AnnotationStatement;
import cc.suitalk.arbitrarygen.tools.RuntimeContextHelper;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.JSONArgsUtils;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/16.
 */
@ParseJavaRule(name = "processorList", rule = "${project.projectDir}/src/main/java/cc/suitalk/arbitrarygen/processor/*")
@PsychicTask
public class PsychicAGEngine implements ArbitraryGenEngine {

    private static final String TAG = "AG.PsychicAGEngine";

    @Override
    public String getName() {
        return "PsychicEngine";
    }

    @Override
    public void initialize(AGCore core, JSONObject args) {
    }

    @Override
    public String[] getDependencies() {
        return new String[] {
                /*@@@#SCRIPT-BEGIN#
                <%processorList = context.processorList;
                if (processorList && processorList.length > 0) {
                    var list = [];
                    for (var i = 0; i < processorList.length; i++) {
                       var methodArray = processorList[i]._class[0].method;
                       for (var m = 0; m < methodArray.length; m++) {
                           var method = methodArray[m];
                           if (method && method._name == "getName" && method.codeBlock) {
                                if (method.codeBlock.statement && method.codeBlock.statement.length > 0) {
                                    var s = method.codeBlock.statement[method.codeBlock.statement.length - 1];
                                    if (s._type == "return") {
                                        list[list.length] = s._expression;break;
                                    }
                                }
                           }
                       }
                    }
                    if (list.length > 0) {
                        for (var i = 0; i < list.length - 1; i++) {%>
                <%=list[i].replace(";", "")%>,<%
                        }%>
                <%=list[list.length - 1].replace(";", "")%>
                    <%}
                }%>
                #SCRIPT-END#@@@*///@@@#AUTO-GEN-BEGIN#
                
                 "script-executor",
                 "hybrid-template-processor",
                 "logger",
                 "parse-java",
                 "parse-json",
                 "parse-rule",
                 "parse-xml",
                 "psychic-processor",
                 "scanner",
                 "statistic",
                 "template-processor"
                    
                
                //@@@#AUTO-GEN-END#
        };
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        Log.v(TAG, "execute Psychic engine, args(%s)", args);
        JSONArray suffixList = new JSONArray();
        suffixList.add("psychic-define");
        suffixList.add("psychic-rule");
        JSONObject argsJSONObject = new JSONObject();
        argsJSONObject.put(ScannerAGProcessor.KEY_SCAN_MODE, ScannerAGProcessor.SCAN_MODE_CLASSIFY);
        argsJSONObject.put(ScannerAGProcessor.KEY_SRC_DIR, args.getString(ArgsConstants.EXTERNAL_ARGS_KEY_SRC_DIR));
        argsJSONObject.put(ScannerAGProcessor.KEY_SUFFIX_LIST, suffixList);

        JSONObject jsonObject = core.execProcess(processors, "scanner", argsJSONObject);
        if (jsonObject == null) {
            Log.i(TAG, "exec failed, scan out file list is null.");
            return null;
        }
        dealPsychicDefine(core, processors, jsonObject.optJSONArray("psychic-define"));
        dealPsychicRule(core, processors, jsonObject.optJSONArray("psychic-rule"));
        return null;
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }

    private void dealPsychicDefine(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONArray fileArray) {
        if (fileArray == null || fileArray.isEmpty()) {
            Log.i(TAG, "scan out psychic define file list is nil.");
            return;
        }
        ArbitraryGenProcessor psychicProcessor = processors.get("psychic-processor");
        for (int i = 0; i < fileArray.size(); i++) {
            String path = fileArray.optString(i);
            if (Util.isNullOrNil(path)) {
                Log.i(TAG, "file path is null or nil.");
                continue;
            }
            XMLSerializer ss = new XMLSerializer();
            ss.setTypeHintsEnabled(false);
            ss.setTypeHintsCompatibility(false);
            Log.i(TAG, "process src : %s", path);
            String rawContent = FileOperation.read(path);
            JSONObject json = (JSONObject) ss.read(RuntimeContextHelper.replace(rawContent));
            if (json == null) {
                Log.i(TAG, "read JSONObject from XML file(%s) failed.", path);
                continue;
            }
            json = JSONArgsUtils.validKey(json);
            psychicProcessor.exec(core, processors, json);
        }
    }

    private void dealPsychicRule(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONArray ruleFileArray) {
        if (ruleFileArray == null || ruleFileArray.isEmpty()) {
            Log.i(TAG, "scan out psychic rule file list is nil.");
            return;
        }
        ArbitraryGenProcessor parseRuleProcessor = processors.get("parse-rule");
        ArbitraryGenProcessor psychicProcessor = processors.get("psychic-processor");
        JSONObject args = new JSONObject();
        for (int i = 0; i < ruleFileArray.size(); i++) {
            String path = ruleFileArray.optString(i);
            if (Util.isNullOrNil(path)) {
                Log.i(TAG, "file path is null or nil.");
                continue;
            }
            args.put(ArgsConstants.EXTERNAL_ARGS_KEY_RULE_FILE, path);
            JSONObject result = parseRuleProcessor.exec(core, processors, args);
            if (result == null) {
                Log.i(TAG, "parse rule result is null.");
                continue;
            }
            JSONArray fileArray = result.optJSONArray("fileArray");
            if (fileArray == null) {
                Log.i(TAG, "file array is null.");
                continue;
            }
            JSONObject psychicArgs = new JSONObject();
            JSONArray taskArray = new JSONArray();
            for (int j = 0; j < fileArray.size(); j++) {
                File file = new File(fileArray.getString(j));
                JavaFileLexer lexer = new JavaFileLexer(file);
                JavaFileObject javaFileObject = lexer.start();
                if (javaFileObject == null) {
                    Log.i(TAG, "JavaFileObject is null.");
                    continue;
                }
                TypeDefineCodeBlock typeDefine = javaFileObject.getTheFileCodeBlock(true);
                if (typeDefine == null) {
                    Log.i(TAG, "type define code block is null.");
                    continue;
                }
                if (!typeDefine.containsAnnotation(PsychicTask.class.getSimpleName())) {
                    Log.v(TAG, "type define do not contains PsychicTask annotation.");
                    continue;
                }
                AnnotationStatement parseJavaRuleStm = typeDefine.getAnnotation(ParseJavaRule.class.getSimpleName());
                AnnotationStatement parseXmlRuleStm = typeDefine.getAnnotation(ParseXmlRule.class.getSimpleName());
                AnnotationStatement parseJsonRuleStm = typeDefine.getAnnotation(ParseJsonRule.class.getSimpleName());
                AnnotationStatement dependsStm = typeDefine.getAnnotation(DependsOn.class.getSimpleName());
                if (parseJavaRuleStm == null && dependsStm == null && parseXmlRuleStm == null && parseJsonRuleStm == null) {
                    Log.i(TAG, "Annotation ParseJavaRule and DependsOn is null.");
                    continue;
                }
                JSONObject psychicTask = new JSONObject();
                JSONArray dependsOnArray = new JSONArray();
                if (parseJavaRuleStm != null) {
                    JSONObject ruleInfo = new JSONObject();
                    ruleInfo.put("_name", parseJavaRuleStm.getArg("name").getValue());
                    ruleInfo.put("_processor", "parse-java");
                    ruleInfo.put("_type", "input");
                    // TODO: 2016/12/3 albieliang, resolve rule array case.
                    String rule = (String) parseJavaRuleStm.getArg("rule").getValue();
                    ruleInfo.put("rule", RuntimeContextHelper.replace(rule));
                    dependsOnArray.add(ruleInfo);

                }
                if (parseXmlRuleStm != null) {
                    JSONObject ruleInfo = new JSONObject();
                    ruleInfo.put("_name", parseXmlRuleStm.getArg("name").getValue());
                    ruleInfo.put("_processor", "parse-xml");
                    ruleInfo.put("_type", "input");
                    String rule = (String) parseXmlRuleStm.getArg("rule").getValue();
                    ruleInfo.put("rule", RuntimeContextHelper.replace(rule));
                    dependsOnArray.add(ruleInfo);
                }
                if (parseJsonRuleStm != null) {
                    JSONObject ruleInfo = new JSONObject();
                    ruleInfo.put("_name", parseJsonRuleStm.getArg("name").getValue());
                    ruleInfo.put("_processor", "parse-json");
                    ruleInfo.put("_type", "input");
                    String rule = (String) parseJsonRuleStm.getArg("rule").getValue();
                    ruleInfo.put("rule", RuntimeContextHelper.replace(rule));
                    dependsOnArray.add(ruleInfo);
                }
                if (dependsStm != null) {
                    JSONObject dependsOnInfo = new JSONObject();
                    String argsStr = dependsStm.getArg("args").getValue().toString();
                    if (!Util.isNullOrNil(argsStr)) {
                        JSONObject argsJSONObject = JSONObject.fromObject(argsStr);
                        if (argsJSONObject != null) {
                            dependsOnInfo.putAll(argsJSONObject);
                        }
                    }
                    dependsOnInfo.put("_name", dependsStm.getArg("name").getValue());
                    dependsOnInfo.put("_processor", dependsStm.getArg("processor").getValue());
                    dependsOnInfo.put("_type", "input");
                    dependsOnArray.add(dependsOnInfo);
                }
                psychicTask.put("_name", "AGPsychicTask_" + javaFileObject.getFileName());
                psychicTask.put("_processor", "hybrid-template-processor");
                psychicTask.put("dependsOn", dependsOnArray);
                psychicTask.put("template", file.getAbsolutePath());
                taskArray.add(psychicTask);
            }
            psychicArgs.put("PsychicTask", taskArray);
            Log.i(TAG, "execute task path(%s)", path);
//            Log.i(TAG, "execute task array(%s)", psychicArgs);
//            Log.i(TAG, "execute task fileArray(%s)", fileArray);
            psychicProcessor.exec(core, processors, psychicArgs);
        }
    }
}
