package osc.innovator.arbitrarygen.statement;

import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class NormalStatement extends BaseStatement {

	private String mStatementStr;

	public NormalStatement() {
	}

	public NormalStatement(String statementStr) {
		mStatementStr = statementStr;
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.nullAsNil(mStatementStr));
		String plainCodeBlock = genPlainCodeBlock(linefeed);
		if (Util.isNullOrNil(plainCodeBlock)) {
			if (!Util.isNullOrNil(mStatementStr)) {
				builder.append(Util.getSuffix(this, ";"));
			}
		} else {
			builder.append(plainCodeBlock);
		}
		return builder.toString();
	}

	public void setStatementStr(String statement) {
		mStatementStr = statement;
	}
}
