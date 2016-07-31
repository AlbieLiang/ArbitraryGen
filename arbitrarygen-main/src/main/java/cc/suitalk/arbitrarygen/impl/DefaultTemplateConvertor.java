package cc.suitalk.arbitrarygen.impl;

import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ContextInfo;
import cc.suitalk.arbitrarygen.extension.ICustomizeConvertor;
import cc.suitalk.arbitrarygen.template.RawTemplate;
import cc.suitalk.arbitrarygen.utils.AnalyzerHelper;

/**
 * 
 * @author AlbieLiang
 * 
 */
public class DefaultTemplateConvertor implements ICustomizeConvertor {

	public DefaultTemplateConvertor() {
	}

	@Override
	public TypeDefineCodeBlock convert(ContextInfo contextInfo, RawTemplate rawTemplate) {
		if (rawTemplate == null) {
			return null;
		}
		return AnalyzerHelper.createTypeDefineCodeBlock(rawTemplate);
	}

	@Override
	public boolean canConvert(RawTemplate template) {
		return template != null;
	}
}
