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

package cc.suitalk.arbitrarygen.base;

import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class Expression implements ICodeGenerator {

	private String mVariable;
	private Expression mLeftExpression;
	private Expression mRightExpression;
	private String mOperator1;
	private String mOperator2;
	
	protected String mBlankStr;

	protected boolean mNeedSurround;

	public String getVariable() {
		return mVariable;
	}

	public void setVariable(String variable) {
		this.mVariable = variable;
	}

	public Expression getLeftExpression() {
		return mLeftExpression;
	}

	public void setLeftExpression(Expression leftExpression) {
		this.mLeftExpression = leftExpression;
	}

	public Expression getRightExpression() {
		return mRightExpression;
	}

	public void setRightExpression(Expression rightExpression) {
		this.mRightExpression = rightExpression;
	}

	public String getOperator1() {
		return mOperator1;
	}

	public void setOperator1(String operator1) {
		this.mOperator1 = operator1;
	}

	public String getOperator2() {
		return mOperator2;
	}

	public void setOperator2(String operator2) {
		this.mOperator2 = operator2;
	}

	public boolean needSurround() {
		return mNeedSurround;
	}

	public void setSurround(boolean surround) {
		mNeedSurround = surround;
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		if (!Util.isNullOrNil(mBlankStr)) {
			builder.append(mBlankStr);
		}
		if (needSurround()) {
			builder.append("(");
		}
		if (!Util.isNullOrNil(mVariable)) {
			builder.append(mVariable);
		}
		if (!Util.isNullOrNil(mOperator1)) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(mOperator1);
		}
		String expression = null;
		if (mLeftExpression != null && !Util.isNullOrNil(expression = mLeftExpression.genCode(linefeed))) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(expression);
		}
		if (!Util.isNullOrNil(mOperator2)) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(mOperator2);
		}
		if (mRightExpression != null && !Util.isNullOrNil(expression = mRightExpression.genCode(linefeed))) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(expression);
		}
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
		if (!Util.isNullOrNil(mVariable)) {
			builder.append(mVariable);
		}
		if (!Util.isNullOrNil(mOperator1)) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(mOperator1);
		}
		String expression = null;
		if (mLeftExpression != null && !Util.isNullOrNil(expression = mLeftExpression.toString())) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(expression);
		}
		if (!Util.isNullOrNil(mOperator2)) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(mOperator2);
		}
		if (mRightExpression != null && !Util.isNullOrNil(expression = mRightExpression.toString())) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(expression);
		}
		if (needSurround()) {
			builder.append(")");
		}
		return builder.toString();
	}

	public String getBlankStr() {
		return mBlankStr;
	}

	public void setBlankStr(String blankStr) {
		this.mBlankStr = blankStr;
	}
}
