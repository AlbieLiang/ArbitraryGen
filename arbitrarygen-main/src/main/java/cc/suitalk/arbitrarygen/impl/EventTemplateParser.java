package cc.suitalk.arbitrarygen.impl;

/**
 * 
 * @author AlbieLiang
 *
 */
public class EventTemplateParser extends DefaultRawTemplateParser {

	@Override
	public boolean canParse(String suffix) {
		return "event".equalsIgnoreCase(suffix);
	}

}
