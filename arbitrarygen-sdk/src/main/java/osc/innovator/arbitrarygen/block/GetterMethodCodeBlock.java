package osc.innovator.arbitrarygen.block;

import osc.innovator.arbitrarygen.core.KeyWords;
import osc.innovator.arbitrarygen.core.OutcomeConstants;
import osc.innovator.arbitrarygen.statement.NormalStatement;
import osc.innovator.arbitrarygen.utils.MethodCreatorHelper;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class GetterMethodCodeBlock extends DefaultMethodCodeBlock {
	
	public GetterMethodCodeBlock(FieldCodeBlock template) {
		this(template.getName().getName(), template.getType().genCode(""));
	}

	public GetterMethodCodeBlock(String fieldName, String type) {
		super(MethodCreatorHelper.joint(OutcomeConstants.GET,
				Util.changeFirstChatToUpper(fieldName)), type);
		NormalStatement stm = new NormalStatement();
		stm.setStatementStr(String.format("%s %s.%s",
				KeyWords.V_JAVA_KEYWORDS_RETURN, KeyWords.V_JAVA_KEYWORDS_THIS,
				fieldName));
		addStatement(stm);
	}
}
