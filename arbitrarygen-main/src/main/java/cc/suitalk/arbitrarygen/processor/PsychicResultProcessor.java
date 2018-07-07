package cc.suitalk.arbitrarygen.processor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import cc.suitalk.arbitrarygen.constant.ResConstants;
import cc.suitalk.arbitrarygen.extension.AGContext;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.template.DelayReadResFileTask;
import cc.suitalk.arbitrarygen.template.TemplateManager;
import cc.suitalk.arbitrarygen.tools.RuntimeContextHelper;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.TemplateUtils;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by albieliang on 2018/6/30.
 */

public class PsychicResultProcessor implements ArbitraryGenProcessor {

    private static final String TAG = "AG.PsychicResultProcessor";

    private ScriptEngine mScriptEngine;
    private String mCoreScript;

    public PsychicResultProcessor() {
        mScriptEngine = new ScriptEngineManager().getEngineByName("javascript");
    }
    @Override
    public String getName() {
        return "psychic-result-processor";
    }

    @Override
    public void initialize(AGContext context, JSONObject args) {
        mCoreScript = TemplateManager.getImpl().get(
                ResConstants.PATH_CORE_SCRIPT, new DelayReadResFileTask(ResConstants.PATH_CORE_SCRIPT));
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public JSONObject exec(AGContext context, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        String template = getTemplate(args);
        if (Util.isNullOrNil(template)) {
            Log.i(TAG, "template is null or nil.");
            return null;
        }
        JSONObject contextJSON = args.optJSONObject("context");
        if (contextJSON == null) {
            Log.w(TAG, "context JSON is null.");
            return null;
        }
        String dataKey = args.optString("_dataKey");
        if (Util.isNullOrNil(dataKey)) {
            Log.w(TAG, "dataKey is null or nil.");
            return null;
        }
        String toFileArgs = args.optString("args");
        String toFile = args.optString("toFile");
        if (Util.isNullOrNil(toFile)) {
            Log.w(TAG, "toFile is null.");
            return null;
        }

        JSONArray dataArray =  contextJSON.optJSONArray(dataKey);
        if (dataArray == null || dataArray.isEmpty()) {
            Log.i(TAG, "data(key : %s) is null", dataKey);
            Log.d(TAG, "args : %s", args);
            return null;
        }
        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject taskInfo = dataArray.optJSONObject(i);
            if (taskInfo == null) {
                Log.i(TAG, "resultTo task info is null.");
                continue;
            }
            Map<String, Object> extMap = new HashMap<>();
            extMap.putAll(taskInfo);
            if (!Util.isNullOrNil(toFileArgs)) {
                JSONObject argsJSON = JSONObject.fromObject(RuntimeContextHelper.replace(toFileArgs, extMap));
                if (argsJSON != null) {
                    extMap.putAll(argsJSON);
                    taskInfo.putAll(argsJSON);
                }
            }
            String filePath = RuntimeContextHelper.replace(toFile, extMap);
            Log.d(TAG, "taskInfo : %s", taskInfo);
            generateCode(taskInfo, template, filePath);
        }
        return null;
    }

    @Override
    public void onError(AGContext context, int errorCode, String message) {
        Log.e(TAG, "do psychic result processor error, code is '%d', message is '%s'", errorCode, message);
    }

    private static String getTemplate(JSONObject args) {
        String templatePath = args.optString("template");
        String templateTag = args.optString("templateTag");
        if (Util.isNullOrNil(templatePath) && Util.isNullOrNil(templateTag)) {
            Log.w(TAG, "exec failed, template path and templateTag is null or nil.");
            return null;
        }
        String template = null;
        if (!Util.isNullOrNil(templateTag)) {
            template = RuntimeContextHelper.replace(TemplateManager.getImpl().get(templateTag));
        }
        if (Util.isNullOrNil(template)) {
            template = RuntimeContextHelper.replace(FileOperation.read(templatePath));
        }
        return template;
    }

    private void generateCode(JSONObject args, String template, String toFile) {
        final ScriptEngine engine = mScriptEngine;
        String jsonStr = args.toString();
        String script = mCoreScript + "\nparseTemplate(\"" + TemplateUtils.escape(template) + "\"," + jsonStr + ");";

        File destFolder = new File(toFile).getParentFile();
        if (!destFolder.exists()) {
            destFolder.mkdirs();
        }
        Log.d(TAG, "path : %s\n", toFile);
//        Log.d(TAG, "script : %s\n", script);
        try {
            FileOperation.write(toFile, TemplateUtils.format(TemplateUtils.unescape((String) engine.eval(script))));
            Log.i(TAG, "genCode into file %s successfully.", toFile);
        } catch (ScriptException e) {
            Log.e(TAG, "eval script error, exception : %s", e);
        }

    }
}
