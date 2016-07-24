package osc.innovator.arbitrarygen.engine;

import java.io.File;

import osc.innovator.arbitrarygen.core.BaseGenCodeEngine;
import osc.innovator.arbitrarygen.core.IGenCodeEngine;
import osc.innovator.arbitrarygen.core.SourceFileInfo;
import osc.innovator.arbitrarygen.template.TemplateConfig;
import osc.innovator.arbitrarygen.template.base.ITemplateProcessor;
import osc.innovator.arbitrarygen.utils.FileOperation;
import osc.innovator.arbitrarygen.utils.Log;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ScriptTemplateGenCodeEngine extends BaseGenCodeEngine {

	private static final String TAG = "CodeGen.ScriptTemplateGenCodeEngine";
	
	private ITemplateProcessor mTemplateProcessor;
	private TemplateConfig mTemplateConfig;
	
	public ScriptTemplateGenCodeEngine() {
	}
	
	@Override
	public void doScanFiles(IGenCodeEngine engine) {
		mSourceFileInfos.addAll(FileOperation.scan(mConfigInfo.getSrcPath(), mTemplateProcessor.getSupportSuffixList()));
	}

	@Override
	public void doParseFiles(IGenCodeEngine engine) {
		ITemplateProcessor tp = mTemplateProcessor;
		tp.prepare(mTemplateConfig);
		for (int i = 0; i < mSourceFileInfos.size(); i++) {
			SourceFileInfo info = mSourceFileInfos.get(i);
			File file = info.file;
			String path = file.getAbsolutePath();
			Log.v(TAG, "do process file(%s).", path);
			tp.process(path, mConfigInfo.getDestPath());
		}
	}

	@Override
	public void doConvertTemplates(IGenCodeEngine engine) {
	}

	@Override
	public synchronized void doGenCode(IGenCodeEngine engine) {
//		super.doGenCode(engine);
	}

	public void setTemplateConfig(TemplateConfig cfg) {
		mTemplateConfig = cfg;
	}
	
	public void setTemplateProcessor(ITemplateProcessor processor) {
		mTemplateProcessor = processor;
	}
	
	/**
	 * 
	 * @author AlbieLiang
	 *
	 */
	public static class TaskInfo {
//		public String template;
		public String transfer;
		public String utils;
		public String destPath;
		public String coreLibs;
		public String templateLibs;
		public String templateSuffix;
	}
}
