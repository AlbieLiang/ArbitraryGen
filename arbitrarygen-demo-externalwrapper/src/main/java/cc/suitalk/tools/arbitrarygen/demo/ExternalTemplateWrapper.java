package cc.suitalk.tools.arbitrarygen.demo;

import java.util.Map;

import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ContextInfo;
import cc.suitalk.arbitrarygen.core.TemplateConstants;
import cc.suitalk.arbitrarygen.extension.TemplateWrapper;
import cc.suitalk.arbitrarygen.template.RawTemplate;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ExternalTemplateWrapper implements TemplateWrapper {

	private static final String TAG_NAME = "Wrapper";

	@Override
	public boolean doWrap(ContextInfo contextInfo, RawTemplate template) {
		if (template != null && TAG_NAME.equalsIgnoreCase(template.getName())) {
			Map<String, String> attrs = template.getAttributes();
			String name = attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_NAME);
			attrs.put(TemplateConstants.TEMPLATE_KEYWORDS_NAME, name + "Wrapper");
			attrs.put(TemplateConstants.TEMPLATE_KEYWORDS_PARENT, "Activity");
			String imports = attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_IMPORT);
			// TODO :
			if (Util.isNullOrNil(imports)) {
				imports = "android.app.Activity";
			} else {
				imports += ",android.app.Activity";
			}
			attrs.put(TemplateConstants.TEMPLATE_KEYWORDS_IMPORT, imports);
			return true;
		}
		return false;
	}

	@Override
	public boolean doWrap(ContextInfo contextInfo, TypeDefineCodeBlock template) {
		return false;
	}

}
