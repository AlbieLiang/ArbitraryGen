package cc.suitalk.arbitrarygen.processor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.rule.RuleParser;
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
    public void initialize(AGCore core, JSONObject args) {
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        String ruleArg = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_RULE);
        List<String> pathList = new LinkedList<>();
        if (!Util.isNullOrNil(ruleArg)) {
            pathList.addAll(RuleParser.parseAndScan(ruleArg));
        }
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < pathList.size(); i++) {
            String path = pathList.get(i);
            jsonArray.add(path);
        }
        result.put("fileArray", jsonArray);
        return result;
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }
}
