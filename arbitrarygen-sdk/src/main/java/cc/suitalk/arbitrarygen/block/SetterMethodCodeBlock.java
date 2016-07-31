package cc.suitalk.arbitrarygen.block;

import cc.suitalk.arbitrarygen.core.KeyWords;
import cc.suitalk.arbitrarygen.core.OutcomeConstants;
import cc.suitalk.arbitrarygen.model.DefaultKeyValuePair;
import cc.suitalk.arbitrarygen.statement.NormalStatement;
import cc.suitalk.arbitrarygen.utils.MethodCreatorHelper;
import cc.suitalk.arbitrarygen.utils.Util;

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
