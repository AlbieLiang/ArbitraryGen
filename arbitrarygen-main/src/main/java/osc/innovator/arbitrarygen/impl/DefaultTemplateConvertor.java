package osc.innovator.arbitrarygen.impl;

import osc.innovator.arbitrarygen.block.TypeDefineCodeBlock;
import osc.innovator.arbitrarygen.core.ContextInfo;
import osc.innovator.arbitrarygen.extension.ICustomizeConvertor;
import osc.innovator.arbitrarygen.template.RawTemplate;
import osc.innovator.arbitrarygen.utils.AnalyzerHelper;

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
