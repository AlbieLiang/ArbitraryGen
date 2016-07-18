package osc.innovator.arbitrarygen.impl;

import osc.innovator.arbitrarygen.extension.ICustomizeConvertor;
import osc.innovator.arbitrarygen.extension.ICustomizeParser;
import osc.innovator.arbitrarygen.extension.IDirector;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DefaultTemplateDirector implements IDirector {

	@Override
	public String getFileSuffix() {
		return "xml";
	}

	@Override
	public boolean useCustomizeParser() {
		return true;
	}

	@Override
	public ICustomizeParser getCustomizeParser() {
		return new DefaultRawTemplateParser();
	}

	@Override
	public ICustomizeConvertor getCustomizeAnalyzer() {
		return new DefaultTemplateConvertor();
	}

}
