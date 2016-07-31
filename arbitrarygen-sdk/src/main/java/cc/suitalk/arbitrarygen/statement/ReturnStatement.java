package cc.suitalk.arbitrarygen.statement;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ReturnStatement extends BaseStatement {

	private Expression mExpression;
	public ReturnStatement() {
	}

	public ReturnStatement(Expression expression) {
		mExpression = expression;
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "return"));
		builder.append(getWordBlank(BLANK_1));
		builder.append(mExpression.genCode(linefeed));
		builder.append(Util.getSuffix(this, ";"));
		return builder.toString();
	}

	public void setStatement(Expression expression) {
		mExpression = expression;
	}
	
	public Expression getExpression() {
		return mExpression;
	}
}
