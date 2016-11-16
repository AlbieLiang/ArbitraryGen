package cc.suitalk.arbitrarygen.processor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.gencode.SourceFileInfo;
import cc.suitalk.arbitrarygen.rule.Project;
import cc.suitalk.arbitrarygen.rule.Rule;
import cc.suitalk.arbitrarygen.rule.RuleParser;
import cc.suitalk.arbitrarygen.utils.FileOperation;
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
        List<Rule> ruleList = new LinkedList<>();
        String ruleArg = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_RULE);
        if (!Util.isNullOrNil(ruleArg)) {
            RuleParser parser = new RuleParser();
            Rule rule = parser.parse(ruleArg);
            if (rule != null) {
                ruleList.add(rule);
            }
        }
        List<SourceFileInfo> srcFileInfoList = new LinkedList<>();
        for (Rule r : ruleList) {
            Project p = r.getDefaultProject();
            if (p != null) {
                srcFileInfoList.addAll(scan(p));
            }
            List<Project> projects = r.getProjects();
            for (int i = 0; i < projects.size(); i++) {
                srcFileInfoList.addAll(scan(projects.get(i)));
            }
        }
        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < srcFileInfoList.size(); i++) {
            SourceFileInfo info = srcFileInfoList.get(i);
            jsonArray.add(info.file.getAbsolutePath());
        }
        result.put("fileArray", jsonArray);
        return result;
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }

    private List<SourceFileInfo> scan(Project p) {
        List<SourceFileInfo> results = new LinkedList<>();
        for (String path : p.getSrcfiles()) {
            String absolutePath = p.getRoot() + File.separator + p.getName() + File.separator + p.getSrcs().get(0) + File.separator + Util.exchangeToPath(path) + "." + p.getFormats().get(0);
            Log.v(TAG, "path : " + absolutePath);
            List<SourceFileInfo> r = FileOperation.scan(absolutePath, p.getFormats());
            if (r != null && r.size() > 0) {
                results.addAll(r);
            }
        }
        return results;
    }
}
