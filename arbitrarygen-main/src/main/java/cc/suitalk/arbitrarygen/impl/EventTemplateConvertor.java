package cc.suitalk.arbitrarygen.impl;

import java.util.Map;

import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ContextInfo;
import cc.suitalk.arbitrarygen.core.TemplateConstants;
import cc.suitalk.arbitrarygen.extension.ICustomizeConvertor;
import cc.suitalk.arbitrarygen.template.RawTemplate;
import cc.suitalk.arbitrarygen.utils.AnalyzerHelper;

/**
 * 
 * @author AlbieLiang
 * 
 */
public class EventTemplateConvertor implements ICustomizeConvertor {

	private static final String TAG_NAME = "event";

	public EventTemplateConvertor() {
	}

	@Override
	public TypeDefineCodeBlock convert(ContextInfo contextInfo, RawTemplate rawTemplate) {
		if (rawTemplate == null) {
			return null;
		}
		doAttachDefaultValues(rawTemplate);
		return AnalyzerHelper.createTypeDefineCodeBlock(rawTemplate);
	}

	private void doAttachDefaultValues(RawTemplate rawTemplate) {
		Map<String, String> attrs = rawTemplate.getAttributes();
		String name = attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_NAME);
		attrs.put(TemplateConstants.TEMPLATE_KEYWORDS_NAME, name + "Event");
		attrs.put(TemplateConstants.TEMPLATE_KEYWORDS_PARENT, "Object");
	}

	@Override
	public boolean canConvert(RawTemplate template) {
		return template != null && TAG_NAME.equals(template.getName());
	}
}
