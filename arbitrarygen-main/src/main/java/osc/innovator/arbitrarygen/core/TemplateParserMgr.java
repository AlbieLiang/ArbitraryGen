package osc.innovator.arbitrarygen.core;

import java.util.LinkedList;
import java.util.List;

import osc.innovator.arbitrarygen.extension.ICustomizeParser;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TemplateParserMgr {

	private List<ICustomizeParser> mParsers;
	private static TemplateParserMgr mgr;

	public static TemplateParserMgr getMgr() {
		if (mgr == null) {
			mgr = new TemplateParserMgr();
		}
		return mgr;
	}

	public TemplateParserMgr() {
		mParsers = new LinkedList<ICustomizeParser>();
	}

	public ICustomizeParser getFirstMatchParser(String suffix) {
		for (int i = 0; i < mParsers.size(); i++) {
			ICustomizeParser p = mParsers.get(i);
			if (p.canParse(suffix)) {
				return p;
			}
		}
		return null;
	}

	public List<ICustomizeParser> getParsers() {
		return mParsers;
	}

	public void addParser(ICustomizeParser parser) {
		if (parser != null) {
			this.mParsers.add(0, parser);
		}
	}

	public void removeParser(ICustomizeParser parser) {
		mParsers.remove(parser);
	}
}
