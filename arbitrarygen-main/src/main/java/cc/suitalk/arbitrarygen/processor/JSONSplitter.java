package cc.suitalk.arbitrarygen.processor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Map;

import cc.suitalk.arbitrarygen.extension.AGContext;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.utils.Log;

/**
 * Created by albieliang on 2018/6/30.
 */

public class JSONSplitter implements ArbitraryGenProcessor {

    private static final String TAG = "AG.JSONSplitter";

    @Override
    public String getName() {
        return "json-splitter";
    }

    @Override
    public void initialize(AGContext context, JSONObject args) {
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public JSONObject exec(AGContext context, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        String resultKey = args.optString("_resultKey");
        String srcKey = args.optString("_srcKey");

        JSONObject contextJSON = args.optJSONObject("context");
        if (contextJSON == null) {
            Log.w(TAG, "context JSON is null.");
            return null;
        }
        JSONArray dataArray = contextJSON.optJSONArray(srcKey);
        if (dataArray == null) {
            Log.i(TAG, "dataArray is null");
            return null;
        }
        JSONArray resultArray = new JSONArray();
        for (int i = 0; i < dataArray.size(); i++) {
            Object o = dataArray.get(i);
            if (o instanceof JSONArray) {
                resultArray.addAll((JSONArray) o);
            } else if (o instanceof JSONObject) {
                resultArray.add(o);
            }
        }
        JSONObject result = new JSONObject();
        result.put(resultKey, resultArray);
        return result;
    }

    @Override
    public void onError(AGContext context, int errorCode, String message) {
        Log.e(TAG, "onError, code is '%d', message is '%s'", errorCode, message);
    }
}
