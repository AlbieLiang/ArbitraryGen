package cc.suitalk.arbitrarygen.core;

import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.extension.CustomizeParser;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TemplateParserMgr {

	private List<CustomizeParser> mParsers;
	private static TemplateParserMgr mgr;

	public static TemplateParserMgr getMgr() {
		if (mgr == null) {
			mgr = new TemplateParserMgr();
		}
		return mgr;
	}

	public TemplateParserMgr() {
		mParsers = new LinkedList<CustomizeParser>();
	}

	public CustomizeParser getFirstMatchParser(String suffix) {
		for (int i = 0; i < mParsers.size(); i++) {
			CustomizeParser p = mParsers.get(i);
			if (p.canParse(suffix)) {
				return p;
			}
		}
		return null;
	}

	public List<CustomizeParser> getParsers() {
		return mParsers;
	}

	public void addParser(CustomizeParser parser) {
		if (parser != null) {
			this.mParsers.add(0, parser);
		}
	}

	public void removeParser(CustomizeParser parser) {
		mParsers.remove(parser);
	}
}
