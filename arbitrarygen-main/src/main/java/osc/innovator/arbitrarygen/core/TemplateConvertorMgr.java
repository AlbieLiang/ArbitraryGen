package osc.innovator.arbitrarygen.core;

import java.util.LinkedList;
import java.util.List;

import osc.innovator.arbitrarygen.extension.ICustomizeConvertor;
import osc.innovator.arbitrarygen.template.RawTemplate;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TemplateConvertorMgr {

	private List<ICustomizeConvertor> mConvertors;
	private static TemplateConvertorMgr mgr;

	public static TemplateConvertorMgr getMgr() {
		if (mgr == null) {
			mgr = new TemplateConvertorMgr();
		}
		return mgr;
	}

	public TemplateConvertorMgr() {
		mConvertors = new LinkedList<ICustomizeConvertor>();
	}

	public ICustomizeConvertor getFirstMatchConvertor(RawTemplate rawTemplate) {
		for (int i = 0; i < mConvertors.size(); i++) {
			ICustomizeConvertor a = mConvertors.get(i);
			if (a.canConvert(rawTemplate)) {
				return a;
			}
		}
		return null;
	}

	public List<ICustomizeConvertor> getConvertors() {
		return mConvertors;
	}

	public void addConvertor(ICustomizeConvertor analyzer) {
		if (analyzer != null) {
			this.mConvertors.add(0, analyzer);
		}
	}

	public void removeConvertor(ICustomizeConvertor analyzer) {
		mConvertors.remove(analyzer);
	}
}
