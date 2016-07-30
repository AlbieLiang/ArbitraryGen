package osc.innovator.arbitrarygen.core;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TemplateDirectorMgr {

	private static TemplateDirectorMgr sMgr;

	private List<String> mSuffixList;
	private TemplateParserMgr mParserMgr;
	private TemplateConvertorMgr mConvertorMgr;

	public static TemplateDirectorMgr getMgr() {
		if (sMgr == null) {
			sMgr = new TemplateDirectorMgr();
		}
		return sMgr;
	}

	public TemplateDirectorMgr() {
		mSuffixList = new LinkedList<>();
	}

	public void addAllSuffixList(List<String> suffixList) {
		if (suffixList == null || suffixList.size() == 0) {
			return;
		}
		for (int i = 0; i < suffixList.size(); i++) {
			String s = suffixList.get(i);
			if (!mSuffixList.contains(s)) {
				mSuffixList.add(s);
			}
		}
	}

	public List<String> getSupportSuffixList() {
		return mSuffixList;
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
