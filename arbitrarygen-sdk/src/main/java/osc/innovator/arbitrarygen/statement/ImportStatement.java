package osc.innovator.arbitrarygen.statement;

import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.expression.ReferenceExpression;
import osc.innovator.arbitrarygen.utils.Util;

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
