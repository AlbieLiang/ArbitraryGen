package cc.suitalk.arbitrarygen.template.base;

import cc.suitalk.arbitrarygen.template.TemplateConfig;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BasePsychicWorker implements AGPsychicWorker {

	protected TemplateConfig mConfigArgs;
	
	public BasePsychicWorker(TemplateConfig cfg) {
		if (cfg == null) {
			throw new RuntimeException("Config args is null.");
		}
		setConfigArgs(cfg);
	}
	
	public BasePsychicWorker setConfigArgs(TemplateConfig cfg) {
		mConfigArgs = cfg;
		return this;
	}
}
