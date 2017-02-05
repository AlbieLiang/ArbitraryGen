package cc.suitalk.arbitrarygen.processor;

import net.sf.json.JSONObject;

import java.util.Map;

import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.StatisticManager;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 17/2/5.
 */
public class StatisticProcessor implements ArbitraryGenProcessor {

    private static final String TAG = "AG.StatisticProcessor";

    @Override
    public String getName() {
        return "statistic";
    }

    @Override
    public void initialize(AGCore core, JSONObject args) {
        boolean toFile = args == null || args.optBoolean(ArgsConstants.EXTERNAL_ARGS_KEY_TO_FILE, true);
        if (toFile) {
            String logFile = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_PATH);
            if (Util.isNullOrNil(logFile)) {
                logFile = "./output/statistic.log";
            }
            StatisticManager.setPath(logFile);
            StatisticManager.prepare();
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
        Log.e(TAG, "do Statistic process error, code is '%d', message is '%s'", errorCode, message);
    }
}
