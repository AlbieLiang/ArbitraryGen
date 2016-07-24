package osc.innovator.arbitrarygen.template.base;

import osc.innovator.arbitrarygen.template.TemplateConfig;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BasePsychicWorker implements IAGPsychicWorker {

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
