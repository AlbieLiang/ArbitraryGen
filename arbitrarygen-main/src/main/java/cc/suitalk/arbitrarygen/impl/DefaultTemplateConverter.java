package cc.suitalk.arbitrarygen.impl;

import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ContextInfo;
import cc.suitalk.arbitrarygen.extension.ICustomizeConverter;
import cc.suitalk.arbitrarygen.template.RawTemplate;
import cc.suitalk.arbitrarygen.utils.AnalyzerHelper;

/**
 * 
 * @author AlbieLiang
 * 
 */
public class DefaultTemplateConverter implements ICustomizeConverter {

	public DefaultTemplateConverter() {
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
