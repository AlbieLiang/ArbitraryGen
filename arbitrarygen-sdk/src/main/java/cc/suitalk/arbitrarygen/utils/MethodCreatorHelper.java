package cc.suitalk.arbitrarygen.utils;

import cc.suitalk.arbitrarygen.block.FieldCodeBlock;
import cc.suitalk.arbitrarygen.core.OutcomeConstants;

/**
 * 
 * @author AlbieLiang
 *
 */
public final class MethodCreatorHelper {

	public static final String joint(String str1, String str2) {
		StringBuilder builder = new StringBuilder();
		builder.append(str1);
		builder.append(str2);
		return builder.toString();

	}

	public static final String genGetterName(FieldCodeBlock template) {
		if (template != null) {
			return joint(OutcomeConstants.GET,
					Util.changeFirstChatToUpper(template.getName().getName()));
		}
		return null;

	}

	public static final String genSetterName(FieldCodeBlock template) {
		if (template != null) {
			return joint(OutcomeConstants.SET,
					Util.changeFirstChatToUpper(template.getName().getName()));
		}
		return null;

	}
}