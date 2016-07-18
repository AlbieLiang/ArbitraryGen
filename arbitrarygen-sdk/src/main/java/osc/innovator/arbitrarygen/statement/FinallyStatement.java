package osc.innovator.arbitrarygen.statement;

import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.base.PlainCodeBlock;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class FinallyStatement extends BaseStatement {

	public FinallyStatement() {
		setCodeBlock(new PlainCodeBlock());
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "finally"));
		builder.append(getWordBlank(BLANK_1));
		builder.append(genPlainCodeBlock(linefeed));
		return builder.toString();
	}

}
