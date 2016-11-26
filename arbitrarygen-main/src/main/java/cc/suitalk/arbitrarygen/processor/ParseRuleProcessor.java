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
import cc.suitalk.arbitrarygen.rule.Project;
import cc.suitalk.arbitrarygen.rule.Rule;
import cc.suitalk.arbitrarygen.rule.RuleFileObject;
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
        String ruleArg = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_RULE);
        List<String> pathList = new LinkedList<>();
        if (!Util.isNullOrNil(ruleArg)) {
            RuleParser parser = new RuleParser();
            RuleFileObject ruleFileObject = parser.parse(ruleArg);
            if (ruleFileObject != null) {
                List<Project> projects = ruleFileObject.getProjects();
                for (int i = 0; i < projects.size(); i++) {
                    pathList.addAll(scan(projects.get(i)));
                }
            }
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

    private List<String> scan(Project p) {
        List<String> fileList = new LinkedList<>();
        List<String> ruleFileList = new LinkedList<>();
        List<String> ruleList = new LinkedList<>();

        RuleFileObject fileObject = p.getRuleFileObject();
        final String dir = fileObject.getRoot() + File.separator + p.getName() + File.separator;
        for (Rule rule : p.getRuleList()) {
            final String content = rule.getContent();
            switch (rule.getType()) {
                case Rule.TYPE_RULE:
                    int index = content.indexOf("*");
                    if (index >= 0) {
                        File file = new File(content.substring(0, index));
                        if (file.isDirectory()) {
                            ruleFileList.addAll(FileOperation.listFilePaths(file, true));
                            ruleList.add(file.getAbsolutePath() + File.separator
                                    + content.substring(index).replaceAll("\\*", "(\\\\.)+"));
                        }
                        break;
                    }
                case Rule.TYPE_FILE:
                    File file = new File(dir + content);
                    if (file.isFile()) {
                        fileList.add(file.getAbsolutePath());
                    }
                    break;
                case Rule.TYPE_DIRECTORY:
                    file = new File(dir + content);
                    if (!file.isDirectory()) {
                        break;
                    }
                    fileList.addAll(FileOperation.listFilePaths(file, false));
                    break;
                case Rule.TYPE_RECURSION_DIRECTORY:
                    file = new File(dir + content);
                    if (!file.isDirectory()) {
                        break;
                    }
                    fileList.addAll(FileOperation.listFilePaths(file, true));
                    break;
            }
        }
        for (String path : ruleFileList) {
            for (String r : ruleList) {
                boolean match = path.matches(r);
                if (match) {
                    fileList.add(path);
                }
                Log.v(TAG, "rule(%s) match(%s) result : %b", r, path, match);
            }
        }
        return fileList;
    }
}
