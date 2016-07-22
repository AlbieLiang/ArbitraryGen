package osc.innovator.tools.arbitrarygen.demo.director;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import osc.innovator.arbitrarygen.block.TypeDefineCodeBlock;
import osc.innovator.arbitrarygen.core.ContextInfo;
import osc.innovator.arbitrarygen.core.TemplateConstants;
import osc.innovator.arbitrarygen.extension.ICustomizeConvertor;
import osc.innovator.arbitrarygen.template.RawTemplate;
import osc.innovator.arbitrarygen.utils.AnalyzerHelper;

/**
 * 
 * @author albieliang
 * 
 */
public class ExternalTemplateAnalyzer implements ICustomizeConvertor {

	private static final String TAG_NAME = "Ext";

	public ExternalTemplateAnalyzer() {
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

	@Override
	public List<String> getSupportSuffixList() {
		List<String> supportSuffixs = new LinkedList<String>();
		supportSuffixs.add("ext");
		return supportSuffixs;
	}
}
