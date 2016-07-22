package osc.innovator.arbitrarygen.core;

import java.util.LinkedList;
import java.util.List;

import osc.innovator.arbitrarygen.extension.ICustomizeConvertor;
import osc.innovator.arbitrarygen.extension.ICustomizeParser;
import osc.innovator.arbitrarygen.extension.IDirector;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TemplateDirectorMgr {

	private static TemplateDirectorMgr mgr;

	private List<IDirector> mDirectors;
	private List<String> mSuffixs;
	private TemplateParserMgr mParserMgr;
	private TemplateConvertorMgr mConvertorMgr;

	public static TemplateDirectorMgr getMgr() {
		if (mgr == null) {
			mgr = new TemplateDirectorMgr();
		}
		return mgr;
	}

	public TemplateDirectorMgr() {
		mDirectors = new LinkedList<IDirector>();
		mSuffixs = new LinkedList<String>();
	}

	public List<IDirector> getDirectors() {
		return mDirectors;
	}

	public void addDirector(IDirector director) {
		if (director == null) {
			return;
		}
		ICustomizeConvertor a = director.getCustomizeAnalyzer();
		ICustomizeParser p = director.getCustomizeParser();
		List<String> ss = null;
		if ((a == null || (ss = a.getSupportSuffixList()) == null || ss.size() == 0) && p == null) {
			return;
		}
		this.mDirectors.add(0, director);
		addAllSuffixs(ss);
		if (mParserMgr != null) {
			mParserMgr.addParser(p);
		}
		if (mConvertorMgr != null) {
			mConvertorMgr.addConvertor(a);
		}
	}

	public void removeDirector(IDirector director) {
		mDirectors.remove(director);
	}

	public void addAllSuffixs(List<String> suffixs) {
		if (suffixs == null || suffixs.size() == 0) {
			return;
		}
		for (int i = 0; i < suffixs.size(); i++) {
			String s = suffixs.get(i);
			if (!mSuffixs.contains(s)) {
				mSuffixs.add(s);
			}
		}
	}

	public List<String> getSupportSuffixs() {
		return mSuffixs;
	}

	public TemplateParserMgr getParserMgr() {
		return mParserMgr;
	}

	public void setParserMgr(TemplateParserMgr mgr) {
		mParserMgr = mgr;
	}

	public TemplateConvertorMgr getConvertorMgr() {
		return mConvertorMgr;
	}

	public void setConvertorMgr(TemplateConvertorMgr mgr) {
		mConvertorMgr = mgr;
	}
}
