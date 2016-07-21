package osc.innovator.tools.arbitrarygen.demo.greendao;

import osc.innovator.arbitrarygen.extension.ICustomizeConvertor;
import osc.innovator.arbitrarygen.extension.ICustomizeParser;
import osc.innovator.arbitrarygen.extension.IDirector;
import osc.innovator.tools.arbitrarygen.demo.director.ExternalTemplateAnalyzer;
import osc.innovator.tools.arbitrarygen.demo.director.ExternalTemplateParser;

/**
 *
 * @author albieliang
 *
 */
public class GreenDaoDirector implements IDirector {

	@Override
	public String getFileSuffix() {
		return "db";
	}

	@Override
	public boolean useCustomizeParser() {
		return false;
	}

	@Override
	public ICustomizeParser getCustomizeParser() {
		return null;
	}

	@Override
	public ICustomizeConvertor getCustomizeAnalyzer() {
		return new GreenDaoAnalyzer();
	}

}
