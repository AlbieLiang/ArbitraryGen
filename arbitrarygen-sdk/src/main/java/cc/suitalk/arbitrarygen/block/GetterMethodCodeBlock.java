package cc.suitalk.arbitrarygen.block;

import cc.suitalk.arbitrarygen.core.KeyWords;
import cc.suitalk.arbitrarygen.core.OutcomeConstants;
import cc.suitalk.arbitrarygen.statement.NormalStatement;
import cc.suitalk.arbitrarygen.utils.MethodCreatorHelper;
import cc.suitalk.arbitrarygen.utils.Util;

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
