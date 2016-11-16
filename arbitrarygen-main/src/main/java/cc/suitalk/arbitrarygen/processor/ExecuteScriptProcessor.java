package cc.suitalk.arbitrarygen.processor;

import net.sf.json.JSONObject;

import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/16.
 */
public class ExecuteScriptProcessor implements ArbitraryGenProcessor {

    private static final String TAG ="AG.ExecuteScriptProcessor";

    private ScriptEngine mScriptEngine;

    public ExecuteScriptProcessor() {
        mScriptEngine = new ScriptEngineManager().getEngineByName("javascript");
    }

    @Override
    public String getName() {
        return "script-executor";
    }

    @Override
    public void initialize(AGCore core, JSONObject args) {
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        String path = args.optString("script");
        if (Util.isNullOrNil(path)) {
            Log.w(TAG, "exec failed, script path is null or nil.");
            return null;
        }
        String script = FileOperation.read(path);
        if (Util.isNullOrNil(script)) {
            Log.i(TAG, "script is null or nil.");
            return null;
        }
        JSONObject jsonObject = null;
        ScriptEngine engine = mScriptEngine;
        try {
            Object o = engine.eval(script);
            if (o instanceof JSONObject) {
                jsonObject = (JSONObject) o;
            }
        } catch (ScriptException e) {
            Log.e(TAG, "eval script error, exception : %s", e);
        }
        return jsonObject;
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }
}
