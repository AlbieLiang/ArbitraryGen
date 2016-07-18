package osc.innovator.arbitrarygen.expression;

import osc.innovator.arbitrarygen.base.Expression;

public class DoubleEyeExpression extends Expression {

	public DoubleEyeExpression(Expression expression1, Expression expression2,
			String operator) {
		setLeftExpression(expression1);
		setRightExpression(expression2);
		setOperator2(operator);
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		if (needSurround()) {
			builder.append("(");
		}
		builder.append(getLeftExpression().genCode(linefeed));
		builder.append(getOperator2());
		builder.append(getRightExpression().genCode(linefeed));
		if (needSurround()) {
			builder.append(")");
		}
		return builder.toString();
	}
}
