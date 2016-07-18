package osc.innovator.arbitrarygen.expression;

import osc.innovator.arbitrarygen.base.Expression;

public class SingleEyeExpression extends Expression {

	private boolean mPrefixOperator;

	public SingleEyeExpression(Expression expression, String operator, boolean prefixOperator) {
		// TODO Check the expression
		mPrefixOperator = prefixOperator;
		setOperator2(operator);
		if (mPrefixOperator) {
			setRightExpression(expression);
		} else {
			setLeftExpression(expression);
		}
	}
	
	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		if (needSurround()) {
			builder.append("(");
		}
		if (!mPrefixOperator) {
			builder.append(getLeftExpression().genCode(linefeed));
		}
		builder.append(getOperator2());
		if (mPrefixOperator) {
			builder.append(getRightExpression().genCode(linefeed));
		}
		if (needSurround()) {
			builder.append(")");
		}
		return builder.toString();
	}
}
