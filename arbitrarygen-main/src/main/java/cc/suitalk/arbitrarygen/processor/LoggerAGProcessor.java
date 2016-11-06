package cc.suitalk.arbitrarygen.processor;

import net.sf.json.JSONObject;

import java.util.Map;

import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.debug.Debuger;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/6.
 */
public class LoggerAGProcessor implements ArbitraryGenProcessor {

    private static final String TAG = "AG.LoggerAGProcessor";

    @Override
    public String getName() {
        return "logger";
    }

    @Override
    public void initialize(AGCore core, JSONObject args) {
        boolean logToFile = args == null || args.optBoolean(ArgsConstants.EXTERNAL_ARGS_KEY_LOG_TO_FILE, true);
        if (logToFile) {
            String logFile = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_LOG_FILE);
            if (Util.isNullOrNil(logFile)) {
                logFile = "./output/ag.log";
            }
            Log.setPath(logFile);
            Log.prepare();
        }
        if (args != null) {
            // Common Arguments
            Debuger.debug = args.optBoolean(ArgsConstants.EXTERNAL_ARGS_KEY_LOG_DEBUG);
            Log.setPrintLogLevel(args.optInt(ArgsConstants.EXTERNAL_ARGS_KEY_LOG_LEVEL, Log.LOG_LEVEL_N));
            Log.setPrintTag(args.optBoolean(ArgsConstants.EXTERNAL_ARGS_KEY_LOG_PRINT_TAG));
            Log.setPrintLevel(args.optBoolean(ArgsConstants.EXTERNAL_ARGS_KEY_LOG_PRINT_LEVEL));
        }
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        return null;
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "do Logger process error, code is '%d', message is '%s'", errorCode, message);
    }
}
