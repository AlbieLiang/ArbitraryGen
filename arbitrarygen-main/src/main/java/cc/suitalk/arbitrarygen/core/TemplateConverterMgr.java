package cc.suitalk.arbitrarygen.core;

import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.extension.ICustomizeConverter;
import cc.suitalk.arbitrarygen.template.RawTemplate;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TemplateConverterMgr {

	private List<ICustomizeConverter> mConverterList;
	private static TemplateConverterMgr sMgr;

	public static TemplateConverterMgr getMgr() {
		if (sMgr == null) {
			sMgr = new TemplateConverterMgr();
		}
		return sMgr;
	}

	public TemplateConverterMgr() {
		mConverterList = new LinkedList<>();
	}

	public ICustomizeConverter getFirstMatchConverter(RawTemplate rawTemplate) {
		for (int i = 0; i < mConverterList.size(); i++) {
			ICustomizeConverter a = mConverterList.get(i);
			if (a.canConvert(rawTemplate)) {
				return a;
			}
		}
		return null;
	}

	public List<ICustomizeConverter> getConverterList() {
		return mConverterList;
	}

	public void addConverter(ICustomizeConverter converter) {
		if (converter != null) {
			this.mConverterList.add(0, converter);
		}
	}

	public void removeConverter(ICustomizeConverter converter) {
		mConverterList.remove(converter);
	}
}
