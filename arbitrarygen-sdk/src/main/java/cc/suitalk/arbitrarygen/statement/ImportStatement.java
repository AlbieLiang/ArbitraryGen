package cc.suitalk.arbitrarygen.statement;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.expression.ReferenceExpression;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ImportStatement extends BaseStatement {

	private ReferenceExpression mImport;

	public ImportStatement(ReferenceExpression importName) {
		mImport = importName;
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "import"));
		builder.append(blank);
		builder.append(mImport.genCode(linefeed));
		builder.append(Util.getSuffix(this, ";"));
		return builder.toString();
	}

	public ReferenceExpression getImportRefExpression() {
		return mImport;
	}

	public String getImport() {
		return mImport != null ? mImport.genCode("") : "";
	}
}
