package osc.innovator.arbitrarygen.template;

import java.io.File;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import osc.innovator.arbitrarygen.engine.ScriptTemplateGenCodeEngine.TaskInfo;
import osc.innovator.arbitrarygen.template.base.BaseTemplateProcessor;
import osc.innovator.arbitrarygen.template.base.IAGPsychicWorker;
import osc.innovator.arbitrarygen.template.base.IGenCodeWorker;
import osc.innovator.arbitrarygen.template.base.IPsychicGenerator;
import osc.innovator.arbitrarygen.utils.FileOperation;
import osc.innovator.arbitrarygen.utils.Log;
import osc.innovator.arbitrarygen.utils.Util;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

/**
 * 
 * @author AlbieLiang
 *
 */
public class JsTemplateProcessor extends BaseTemplateProcessor {

	private static final String TAG = "CodeGen.JsTemplateProcessor";
	
	private ScriptEngine mScriptEngine;
	private String mTransferTools;
	private String mUtils;
	private TemplateConfig mTemplateCfg;

	public JsTemplateProcessor() {
		mScriptEngine = new ScriptEngineManager().getEngineByName("javascript");
	}

	@Override
	public void prepare(TemplateConfig cfg) {
		mTemplateCfg = cfg;
		mTransferTools = FileOperation.read(cfg.getCoreLibs() + "/TransferTools.js");
		mUtils = FileOperation.read(cfg.getCoreLibs() + "/TypeUtils.js") + FileOperation.read(cfg.getCoreLibs() + "/utils.js");
	}

	@Override
	public void process(String src, String destPath) {
		if (src == null || destPath == null) {
			throw new RuntimeException(String.format("src file(%s) or dest path(%s) is null.", src, destPath));
		}
		File file = new File(src);
		if (!file.exists() || !file.isFile()) {
			throw new RuntimeException(String.format("src file(%s) do not exist.", src));
		}

		XMLSerializer ss = new XMLSerializer();
		ss.setTypeHintsEnabled(false);
		ss.setTypeHintsCompatibility(false);
		Log.i(TAG, "process src : " + src);
		JSONObject json = (JSONObject) ss.read(FileOperation.read(src));
		
		TaskInfo info = new TaskInfo();
		info.transfer = mTransferTools;
		info.utils = mUtils;
		info.destPath = destPath;
		info.coreLibs = mTemplateCfg.getCoreLibs();
		info.templateLibs = mTemplateCfg.getTemplateLibs();
		info.templateSuffix = Util.nullAsNil(Util.getSuffix(src));
		
		for (IPsychicGenerator worker : mWorkers) {
			if (worker instanceof IGenCodeWorker) {
				if (info.templateSuffix.equalsIgnoreCase(((IGenCodeWorker) worker).getSupportSuffix())) {
					worker.genCode(mScriptEngine, json, info);
					continue;
				}
			}
			if (worker instanceof IAGPsychicWorker) {
				if (isSupportSuffix((IAGPsychicWorker) worker, info.templateSuffix)) {
					worker.genCode(mScriptEngine, json, info);
					continue;
				}
			}
			// Add more case here
		}
	}

	private static boolean isSupportSuffix(IAGPsychicWorker worker, String suffix) {
		List<String> list = worker.getSupportSuffixList();
		return list != null && list.contains(suffix);
	}
}