package cc.suitalk.arbitrarygen.engine;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import java.util.Map;

import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenEngine;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.processor.ScannerAGProcessor;
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
        return new String[] { "scanner", "script-executor", "hybrid-template-processor", "parse-java", "parse-rule", "template-processor" };
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        Log.v(TAG, "execute Psychic engine, args(%s)", args);
        JSONObject argsJSONObject = new JSONObject();
        argsJSONObject.put(ScannerAGProcessor.KEY_SCAN_MODE, ScannerAGProcessor.SCAN_MODE_NORMAL);
        argsJSONObject.put(ScannerAGProcessor.KEY_SRC_DIR, args.getString(ArgsConstants.EXTERNAL_ARGS_KEY_SRC));
        argsJSONObject.put(ScannerAGProcessor.KEY_SUFFIX_LIST, "psychic-define");

        JSONObject jsonObject = core.execProcess(processors, "scanner", argsJSONObject);
        if (jsonObject == null) {
            Log.i(TAG, "exec failed, scan out file list is null.");
            return null;
        }
        JSONArray fileArray = jsonObject.optJSONArray(ScannerAGProcessor.KEY_RESULT_FILE_PATH);
        if (fileArray == null) {
            Log.i(TAG, "scan out file list is nil.");
            return null;
        }
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
            JSONArray taskArray = JSONArgsUtils.getJSONArray(json, "PsychicTask", true);
            if (taskArray == null) {
                Log.i(TAG, "task array is null.");
                continue;
            }
            for (int t = 0; t < taskArray.size(); t++) {
                JSONObject taskInfo = taskArray.optJSONObject(t);
                if (taskInfo == null) {
                    Log.i(TAG, "task info is null.");
                    continue;
                }
                String name = taskInfo.optString("_name");
                String processor = taskInfo.optString("_processor");
                if (Util.isNullOrNil(name) || Util.isNullOrNil(processor)) {
                    Log.i(TAG, "task name(%s) or processor(%s) is null or nil.", name, processor);
                    continue;
                }
                JSONArray dependsOnArray = JSONArgsUtils.getJSONArray(taskInfo, "dependsOn", true);
                if (dependsOnArray != null) {
                    for (int d = 0; d < dependsOnArray.size(); d++) {
                        JSONObject dependsOnInfo = dependsOnArray.optJSONObject(d);
                        if (dependsOnInfo == null) {
                            Log.i(TAG, "dependsOn info is null.");
                            continue;
                        }
                        String n = dependsOnInfo.optString("_name");
                        String p = dependsOnInfo.optString("_processor");
                        String type = dependsOnInfo.optString("_type");
                        if (Util.isNullOrNil(n) || Util.isNullOrNil(p)) {
                            Log.i(TAG, "the name(%s) or processor(%s) of dependsOn info is null or nil.", n, p);
                            continue;
                        }
                        Log.i(TAG, "the name(%s), processor(%s) and type(%s) of dependsOn info.", n, p, type);
                        if ("input".equals(type)) {
                            JSONObject r = core.execProcess(processors, p, dependsOnInfo);
                            taskInfo.put(n, r);
                        }
                    }
                } else {
                    Log.i(TAG, "dependsOn array is null.");
                }
                JSONObject result = core.execProcess(processors, processor, taskInfo);
                if (result == null) {
                    Log.i(TAG, "execute task processor result is null, switch to next task.");
                    continue;
                }
                JSONArray resultToArray = JSONArgsUtils.getJSONArray(taskInfo, "resultTo", true);
                if (resultToArray != null) {
                    for (int d = 0; d < resultToArray.size(); d++) {
                        JSONObject resultToInfo = resultToArray.optJSONObject(d);
                        if (resultToInfo == null) {
                            Log.i(TAG, "resultTo info is null.");
                            continue;
                        }
                        String p = resultToInfo.optString("_processor");
                        if (Util.isNullOrNil(p)) {
                            Log.i(TAG, "the processor(%s) of resultTo info is null or nil.", p);
                            continue;
                        }
                        JSONObject r = core.execProcess(processors, p, resultToInfo);
                        Log.i(TAG, "the processor(%s) of resultTo info, execute result is : %s.", p, r);
                    }
                } else {
                    Log.i(TAG, "resultTo array is null.");
                }
            }
        }
        return null;
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }
}
