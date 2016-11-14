package cc.suitalk.arbitrarygen.core;

import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.extension.CustomizeConverter;
import cc.suitalk.arbitrarygen.template.RawTemplate;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TemplateConverterMgr {

	private List<CustomizeConverter> mConverterList;
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

	public CustomizeConverter getFirstMatchConverter(RawTemplate rawTemplate) {
		for (int i = 0; i < mConverterList.size(); i++) {
			CustomizeConverter a = mConverterList.get(i);
			if (a.canConvert(rawTemplate)) {
				return a;
			}
		}
		return null;
	}

	public List<CustomizeConverter> getConverterList() {
		return mConverterList;
	}

	public void addConverter(CustomizeConverter converter) {
		if (converter != null) {
			this.mConverterList.add(0, converter);
		}
	}

	public void removeConverter(CustomizeConverter converter) {
		mConverterList.remove(converter);
	}
}
