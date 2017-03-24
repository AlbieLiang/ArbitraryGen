/*
 *  Copyright (C) 2016-present Albie Liang. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package cc.suitalk.arbitrarygen.expression;

import cc.suitalk.arbitrarygen.base.Expression;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (needSurround()) {
			builder.append("(");
		}
		builder.append(getLeftExpression().toString());
		builder.append(getOperator2());
		builder.append(getRightExpression().toString());
		if (needSurround()) {
			builder.append(")");
		}
		return builder.toString();
	}
}
