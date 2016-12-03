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
import cc.suitalk.arbitrarygen.extension.psychic.PsychicTask;
import cc.suitalk.arbitrarygen.processor.ScannerAGProcessor;
import cc.suitalk.arbitrarygen.statement.AnnotationStatement;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.JSONArgsUtils;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/16.
 */
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
        return new String[] { "scanner", "script-executor", "hybrid-template-processor", "parse-java", "parse-rule", "template-processor", "psychic-processor" };
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        Log.v(TAG, "execute Psychic engine, args(%s)", args);
        JSONArray suffixList = new JSONArray();
        suffixList.add("psychic-define");
        suffixList.add("psychic-rule");
        JSONObject argsJSONObject = new JSONObject();
        argsJSONObject.put(ScannerAGProcessor.KEY_SCAN_MODE, ScannerAGProcessor.SCAN_MODE_CLASSIFY);
        argsJSONObject.put(ScannerAGProcessor.KEY_SRC_DIR, args.getString(ArgsConstants.EXTERNAL_ARGS_KEY_SRC));
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
            JSONObject json = (JSONObject) ss.read(FileOperation.read(path));
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
                    continue;
                }
                TypeDefineCodeBlock typeDefine = javaFileObject.getTheFileCodeBlock(true);
                if (typeDefine == null) {
                    continue;
                }
                if (!typeDefine.containsAnnotation(PsychicTask.class.getSimpleName())) {
                    continue;
                }
                AnnotationStatement ruleStm = typeDefine.getAnnotation(ParseJavaRule.class.getSimpleName());
                AnnotationStatement dependsStm = typeDefine.getAnnotation(DependsOn.class.getSimpleName());
                if (ruleStm == null && dependsStm == null) {
                    continue;
                }
                JSONObject psychicTask = new JSONObject();
                JSONArray dependsOnArray = new JSONArray();
                if (ruleStm != null) {
                    JSONObject ruleInfo = new JSONObject();
                    ruleInfo.put("_name", ruleStm.getArg("name").getValue());
                    ruleInfo.put("_processor", "parse-java");
                    ruleInfo.put("_type", "input");
                    // TODO: 2016/12/3 albieliang, resolve rule array case.
                    ruleInfo.put("rule", ruleStm.getArg("rule").toString());
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
            psychicProcessor.exec(core, processors, psychicArgs);
        }
    }
}