package cc.suitalk.arbitrarygen.processor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.extension.AGCore;
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
    public void initialize(AGCore core, JSONObject args) {
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        JSONObject result = new JSONObject();
        JSONArray fileArray = new JSONArray();
        JSONArray ruleFileArray = JSONArgsUtils.getJSONArray(args, ArgsConstants.EXTERNAL_ARGS_KEY_RULE_FILE, true);
        if (ruleFileArray != null) {
            List<String> pathList = new LinkedList<>();
            for (int i = 0; i < ruleFileArray.size(); i++) {
                String ruleArg = ruleFileArray.optString(i);
                if (Util.isNullOrNil(ruleArg)) {
                    continue;
                }
                pathList.addAll(RuleParser.parseAndScan(ruleArg));
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
                    continue;
                }
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
