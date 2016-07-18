package osc.innovator.arbitrarygen.block;

import osc.innovator.arbitrarygen.core.KeyWords;
import osc.innovator.arbitrarygen.core.OutcomeConstants;
import osc.innovator.arbitrarygen.model.DefaultKeyValuePair;
import osc.innovator.arbitrarygen.statement.NormalStatement;
import osc.innovator.arbitrarygen.utils.MethodCreatorHelper;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class SetterMethodCodeBlock extends DefaultMethodCodeBlock {
	
	public SetterMethodCodeBlock(FieldCodeBlock template) {
		this(template.getName().getName(), template.getType().getName());
	}

	public SetterMethodCodeBlock(String fieldName, String returnType) {
		super(MethodCreatorHelper.joint(OutcomeConstants.SET, Util.changeFirstChatToUpper(fieldName)),
				KeyWords.V_JAVA_KEYWORDS_DATA_BASE_TYPE_VOID,
				new DefaultKeyValuePair(Util.createKeyWord(fieldName), Util.createSimpleTypeName(returnType)));
		NormalStatement stm = new NormalStatement();
		stm.setStatementStr(String.format("%s.%s %s %s",
				KeyWords.V_JAVA_KEYWORDS_THIS, fieldName,
				KeyWords.V_JAVA_KEYWORDS_SIGN_ASSIGNMENT, fieldName));
		addStatement(stm);
	}
}
