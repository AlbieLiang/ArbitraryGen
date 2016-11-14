package cc.suitalk.tools.arbitrarygen.demo;

import java.util.Map;

import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ContextInfo;
import cc.suitalk.arbitrarygen.core.TemplateConstants;
import cc.suitalk.arbitrarygen.extension.CustomizeConverter;
import cc.suitalk.arbitrarygen.template.RawTemplate;
import cc.suitalk.arbitrarygen.utils.AnalyzerHelper;

/**
 * 
 * @author albieliang
 * 
 */
public class ExternalTemplateConverter implements CustomizeConverter {

	private static final String TAG_NAME = "Ext";

	public ExternalTemplateConverter() {
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
		attrs.put(TemplateConstants.TEMPLATE_KEYWORDS_NAME, name + TAG_NAME);
		attrs.put(TemplateConstants.TEMPLATE_KEYWORDS_PARENT, "Object");
	}

	@Override
	public boolean canConvert(RawTemplate template) {
		return template != null && TAG_NAME.equalsIgnoreCase(template.getName());
	}
}
