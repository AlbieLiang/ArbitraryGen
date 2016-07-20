package osc.innovator.tools.arbitrarygen.demo.director;

import osc.innovator.arbitrarygen.extension.ICustomizeConvertor;
import osc.innovator.arbitrarygen.extension.ICustomizeParser;
import osc.innovator.arbitrarygen.extension.IDirector;


public class ExternalTemplateDirector implements IDirector {

	@Override
	public String getFileSuffix() {
		return "ext";
	}

	@Override
	public boolean useCustomizeParser() {
		return true;
	}

	@Override
	public ICustomizeParser getCustomizeParser() {
		return new ExternalTemplateParser();
	}

	@Override
	public ICustomizeConvertor getCustomizeAnalyzer() {
		return new ExternalTemplateAnalyzer();
	}

}
