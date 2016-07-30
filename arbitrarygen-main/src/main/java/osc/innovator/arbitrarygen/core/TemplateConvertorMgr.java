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

	private List<ICustomizeConvertor> mConvertorList;
	private static TemplateConvertorMgr sMgr;

	public static TemplateConvertorMgr getMgr() {
		if (sMgr == null) {
			sMgr = new TemplateConvertorMgr();
		}
		return sMgr;
	}

	public TemplateConvertorMgr() {
		mConvertorList = new LinkedList<ICustomizeConvertor>();
	}

	public ICustomizeConvertor getFirstMatchConvertor(RawTemplate rawTemplate) {
		for (int i = 0; i < mConvertorList.size(); i++) {
			ICustomizeConvertor a = mConvertorList.get(i);
			if (a.canConvert(rawTemplate)) {
				return a;
			}
		}
		return null;
	}

	public List<ICustomizeConvertor> getConvertorList() {
		return mConvertorList;
	}

	public void addConvertor(ICustomizeConvertor convertor) {
		if (convertor != null) {
			this.mConvertorList.add(0, convertor);
		}
	}

	public void removeConvertor(ICustomizeConvertor convertor) {
		mConvertorList.remove(convertor);
	}
}
