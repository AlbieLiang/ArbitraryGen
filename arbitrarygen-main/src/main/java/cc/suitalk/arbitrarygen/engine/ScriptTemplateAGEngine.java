package cc.suitalk.arbitrarygen.engine;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.suitalk.arbitrarygen.core.ArbitraryGenCore;
import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.core.base.ArbitraryGenEngine;
import cc.suitalk.arbitrarygen.core.base.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.processor.ScannerAGProcessor;
import cc.suitalk.arbitrarygen.template.GenVigorDBTask;
import cc.suitalk.arbitrarygen.template.JsTemplateProcessor;
import cc.suitalk.arbitrarygen.template.PsychicGenTask;
import cc.suitalk.arbitrarygen.template.TemplateConfig;
import cc.suitalk.arbitrarygen.template.base.ITemplateProcessor;
import cc.suitalk.arbitrarygen.template.hybrids.GenHybridsTask;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by albieliang on 16/11/2.
 */
public class ScriptTemplateAGEngine implements ArbitraryGenEngine {

    private static final String TAG = "AG.ScriptTemplateAGEngine";

    private ITemplateProcessor mTemplateProcessor;
    private TemplateConfig mTemplateConfig;

    @Override
    public String getName() {
        return "scriptEngine";
    }

    @Override
    public void initialize(ArbitraryGenCore core, JSONObject args) {
        // For script template engine
        String coreLibs = args.getString(ArgsConstants.EXTERNAL_ARGS_KEY_CORE_LIBS);
        String templateLibs = args.getString(ArgsConstants.EXTERNAL_ARGS_KEY_TEMPLATE_LIBS);
        JSONArray suffixList = args.getJSONArray(ArgsConstants.EXTERNAL_ARGS_KEY_FORMAT);
        List<String> list = new LinkedList<>();
        if (suffixList != null) {
            for (int i = 0; i < suffixList.size(); i++) {
                String suffix = suffixList.getString(i);
                if (Util.isNullOrNil(suffix)) {
                    continue;
                }
                list.add(suffix);
            }
        }
        if (!Util.isNullOrNil(coreLibs) && !Util.isNullOrNil(templateLibs)) {
            mTemplateConfig = new TemplateConfig(coreLibs, templateLibs);
            mTemplateProcessor = new JsTemplateProcessor();

            mTemplateProcessor.addTaskWorker(new GenVigorDBTask(mTemplateConfig));
            mTemplateProcessor.addTaskWorker(new GenHybridsTask(mTemplateConfig));
            mTemplateProcessor.addTaskWorker(new PsychicGenTask(mTemplateConfig, suffixList));
        }
    }

    @Override
    public String[] getDependencies() {
        return new String[] { "scanner" };
    }

    @Override
    public JSONObject exec(ArbitraryGenCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        if (mTemplateProcessor == null) {
            return null;
        }
        JSONObject argsJSONObject = new JSONObject();
        argsJSONObject.put(ScannerAGProcessor.KEY_SCAN_MODE, ScannerAGProcessor.SCAN_MODE_CLASSIFY);
        argsJSONObject.put(ScannerAGProcessor.KEY_SRC_DIR, args.getString(ArgsConstants.EXTERNAL_ARGS_KEY_SRC));
        argsJSONObject.put(ScannerAGProcessor.KEY_SUFFIX_LIST, args.getJSONArray(ArgsConstants.EXTERNAL_ARGS_KEY_FORMAT));

        JSONObject jsonObject = core.execProcess(processors, "scanner", argsJSONObject);
        if (jsonObject == null) {
            return null;
        }
        Set<String> keySet = jsonObject.keySet();
        if (keySet == null || keySet.isEmpty()) {
            return null;
        }
        final String destPath = args.getString(ArgsConstants.EXTERNAL_ARGS_KEY_DEST);
        final ITemplateProcessor tp = mTemplateProcessor;
        tp.prepare(mTemplateConfig);
        for (String key : keySet) {
            JSONArray array = jsonObject.getJSONArray(key);
            if (array == null || array.isEmpty()) {
                continue;
            }
            for (int i = 0; i < array.size(); i++) {
                String path = array.getString(i);
                if (Util.isNullOrNil(path)) {
                    continue;
                }
                File file = new File(path);
                path = file.getAbsolutePath();
                Log.v(TAG, "do process file(%s).", path);
                tp.process(path, destPath);
            }
        }
        return null;
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }
}
