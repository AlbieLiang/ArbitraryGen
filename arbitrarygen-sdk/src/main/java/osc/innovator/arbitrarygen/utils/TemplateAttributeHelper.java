package osc.innovator.arbitrarygen.utils;

import java.util.Map;

import osc.innovator.arbitrarygen.template.RawTemplate;

/**
 * 
 * @author AlbieLiang
 * 
 */
public class TemplateAttributeHelper {

	public static void setAttribute(RawTemplate template, String key,
			String value) {
		if (template == null || Util.isNullOrNil(key)) {
			return;
		}
		Map<String, String> attrs = template.getAttributes();
		if (attrs != null) {
			attrs.put(key, value);
		}
	}

	public static boolean appendAttribute(RawTemplate template, String key,
			String value) {
		return appendAttribute(template, key, value, "");
	}

	public static boolean appendAttribute(RawTemplate template, String key,
			String value, String separator) {
		if (template == null || Util.isNullOrNil(key)
				|| Util.isNullOrNil(value)) {
			return false;
		}
		Map<String, String> attrs = template.getAttributes();
		if (attrs != null) {
			String originValue = attrs.get(key);
			if (Util.isNullOrNil(originValue)) {
				originValue = value;
			} else {
				originValue += separator + value;
			}
			attrs.put(key, originValue);
		}
		return true;
	}
}
