package cc.suitalk.arbitrarygen.processor;

import net.sf.json.JSONObject;

import java.io.File;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.template.DelayReadFileTask;
import cc.suitalk.arbitrarygen.template.TemplateManager;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.TemplateUtils;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/16.
 */
public class TemplateProcessor implements ArbitraryGenProcessor {

    private static final String TAG ="AG.TemplateProcessor";

    private ScriptEngine mScriptEngine;
    private String mTransferTools;
    private String mUtils;

    public TemplateProcessor() {
        mScriptEngine = new ScriptEngineManager().getEngineByName("javascript");
    }

    @Override
    public String getName() {
        return "template-processor";
    }

    @Override
    public void initialize(AGCore core, JSONObject args) {
        String libsDir = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_LIBS_DIR);
        String coreLibs = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_CORE_LIBS, libsDir + "/core-libs");
        String templateLibs = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_TEMPLATE_LIBS, libsDir + "/template-libs");

        final String path = coreLibs + "/TransferTools.js";
        mTransferTools = TemplateManager.getImpl().get(path, new DelayReadFileTask(path));
        mUtils = FileOperation.read(coreLibs + "/TypeUtils.js") + FileOperation.read(coreLibs + "/utils.js");
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        String templatePath = args.optString("template");
        String templateTag = args.optString("templateTag");
        if (Util.isNullOrNil(templatePath) && Util.isNullOrNil(templateTag)) {
            Log.w(TAG, "exec failed, template path and templateTag is null or nil.");
            return null;
        }
        String template = null;
        if (!Util.isNullOrNil(templateTag)) {
            template = TemplateManager.getImpl().get(templateTag);
        }
        if (Util.isNullOrNil(template)) {
            template = FileOperation.read(templatePath);
        }
        if (Util.isNullOrNil(template)) {
            Log.i(TAG, "template is null or nil.");
            return null;
        }


        final ScriptEngine engine = mScriptEngine;

        String dest = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_DEST);
        String jsonStr = args.toString();
        String script = mTransferTools + mUtils + "\nparseTemplate(\"" + TemplateUtils.escape(template) + "\"," + jsonStr + ");";
        String path = dest + "/" + args.getString("toFile");

        File destFolder = new File(path).getParentFile();
        if (!destFolder.exists()) {
            destFolder.mkdirs();
        }
//		Log.d(TAG, "jsonStr : %s\n", jsonStr);
//		Log.d(TAG, "script : %s\n", script);
        Log.d(TAG, "dest : %s\n", dest);
        Log.d(TAG, "path : %s\n", path);

        try {
            FileOperation.write(path, TemplateUtils.format(TemplateUtils.unescape((String) engine.eval(script))));
            Log.i(TAG, "genCode into file %s successfully.", path);
        } catch (ScriptException e) {
            Log.e(TAG, "eval script error, exception : %s", e);
        }
        return null;
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }
}
