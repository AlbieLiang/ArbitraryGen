package osc.innovator.arbitrarygen.template.base;

import osc.innovator.arbitrarygen.template.TemplateConfig;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BaseGenCodeWorker implements IGenCodeWorker {

	protected TemplateConfig mConfigArgs;
	
	public BaseGenCodeWorker(TemplateConfig cfg) {
		if (cfg == null) {
			throw new RuntimeException("Config args is null.");
		}
		setConfigArgs(cfg);
	}
	
	public BaseGenCodeWorker setConfigArgs(TemplateConfig cfg) {
		mConfigArgs = cfg;
		return this;
	}
}
