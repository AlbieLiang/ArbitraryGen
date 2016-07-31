package cc.suitalk.arbitrarygen.template;

import cc.suitalk.arbitrarygen.core.KeyWords;
import cc.suitalk.arbitrarygen.core.TemplateConstants;
import cc.suitalk.arbitrarygen.utils.TemplateAttributeHelper;

/**
 * 
 * @author AlbieLiang
 *
 */
public class FastAssignRawTemplate extends RawTemplate {

	public FastAssignRawTemplate() {
	}

	public FastAssignRawTemplate(String type, String name) {
		setAttrModifier(KeyWords.V_JAVA_KEYWORDS_PUBLIC);
		setAttrType(type);
		setAttrName(name);
	}

	public FastAssignRawTemplate(String modifier, boolean isStatic,
			boolean isFinal, String type, String name) {
		setAttrModifier(modifier);
		setAttrIsStatic(isStatic);
		setAttrIsFinal(isFinal);
		setAttrType(type);
		setAttrName(name);
	}

	public FastAssignRawTemplate setAttrDefault(String value) {
		TemplateAttributeHelper.setAttribute(this,
				TemplateConstants.TEMPLATE_KEYWORDS_DEFAULT, value);
		return this;
	}

	public FastAssignRawTemplate setAttrName(String name) {
		TemplateAttributeHelper.setAttribute(this,
				TemplateConstants.TEMPLATE_KEYWORDS_NAME, name);
		return this;
	}

	public FastAssignRawTemplate setAttrType(String type) {
		TemplateAttributeHelper.setAttribute(this,
				TemplateConstants.TEMPLATE_KEYWORDS_TYPE, type);
		return this;
	}

	public FastAssignRawTemplate setAttrModifier(String modifier) {
		TemplateAttributeHelper.setAttribute(this,
				TemplateConstants.TEMPLATE_KEYWORDS_MODIFIER, modifier);
		return this;
	}

	public FastAssignRawTemplate setAttrIsFinal(boolean isFinal) {
		TemplateAttributeHelper.setAttribute(this,
				TemplateConstants.TEMPLATE_KEYWORDS_IS_FINAL,
				isFinal ? KeyWords.V_JAVA_KEYWORDS_TRUE
						: KeyWords.V_JAVA_KEYWORDS_FALSE);
		return this;
	}

	public FastAssignRawTemplate setAttrIsStatic(boolean isStatic) {
		TemplateAttributeHelper.setAttribute(this,
				TemplateConstants.TEMPLATE_KEYWORDS_IS_STATIC,
				isStatic ? KeyWords.V_JAVA_KEYWORDS_TRUE
						: KeyWords.V_JAVA_KEYWORDS_FALSE);
		return this;
	}
}
