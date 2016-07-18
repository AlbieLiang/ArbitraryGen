package osc.innovator.arbitrarygen.statement;

import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.base.Expression;
import osc.innovator.arbitrarygen.base.PlainCodeBlock;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class WhileStatement extends BaseStatement {

	private Expression mExpression;

	public WhileStatement() {
		this(null);
	}

	public WhileStatement(Expression condition) {
		mExpression = condition;
		setCodeBlock(new PlainCodeBlock());
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "while"));
		builder.append(blank);
		builder.append(Util.getLeftBlacket(this));
		builder.append(mExpression.genCode(linefeed));
		builder.append(Util.getRightBlacket(this));
		builder.append(blank);
		builder.append(genPlainCodeBlock(linefeed));
		return builder.toString();
	}

	public void setConditionExpression(Expression condition) {
		this.mExpression = condition;
	}
}
