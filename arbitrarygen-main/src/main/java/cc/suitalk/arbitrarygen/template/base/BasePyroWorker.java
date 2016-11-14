package cc.suitalk.arbitrarygen.template.base;

import cc.suitalk.arbitrarygen.template.TemplateConfig;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BasePyroWorker implements AGPyroWorker {

	protected TemplateConfig mConfigArgs;

	public BasePyroWorker(TemplateConfig cfg) {
		if (cfg == null) {
			throw new RuntimeException("Config args is null.");
		}
		setConfigArgs(cfg);
	}
	
	public BasePyroWorker setConfigArgs(TemplateConfig cfg) {
		mConfigArgs = cfg;
		return this;
	}
}
