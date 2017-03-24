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

package cc.suitalk.arbitrarygen.statement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Environment.EnvironmentArgs;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class SwitchStatement extends BaseStatement {

	private Expression mExpression;
	private List<CaseStatement> mCaseStatements;
	private DefaultStatement mDefaultStatement;
	private Word mLeftBrace;
	private Word mRightBrack;

	public SwitchStatement() {
		this(null);
	}
	
	public SwitchStatement(Expression conditionExpression) {
		mExpression = conditionExpression;
		mCaseStatements = new LinkedList<CaseStatement>();
	}

	@Override
	public void onAttachEnvironmentArgs(EnvironmentArgs args) {
		for (int i = 0; i < mCaseStatements.size(); i++) {
			BaseStatement stm = mCaseStatements.get(i);
			stm.attachEnvironmentArgs(args);
		}
		if (mDefaultStatement != null) {
			mDefaultStatement.attachEnvironmentArgs(args);
		}
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "switch"));
		builder.append(blank);
		builder.append(Util.getLeftBracket(this));
		builder.append(mExpression.genCode(linefeed));
		builder.append(Util.getRightBracket(this));
		builder.append(blank);
		builder.append(mLeftBrace != null ? mLeftBrace : "{");
		for (int i = 0; i < mCaseStatements.size(); i++) {
			CaseStatement s = mCaseStatements.get(i);
			if (s != null) {
				builder.append(getLinefeed(linefeed));
				builder.append(s.genCode(linefeed));
			}
		}
		if (mDefaultStatement != null) {
			builder.append(getLinefeed(linefeed));
			builder.append(mDefaultStatement.genCode(linefeed));
		}
		builder.append(getLinefeed(linefeed));
		builder.append(mRightBrack != null ? mRightBrack : "}");
		return builder.toString();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject o = super.toJSONObject();
		o.put("_type", "switch");
		o.put("_condition", mExpression.toString());
		JSONArray caseArray = new JSONArray();
		for (int i = 0; i < mCaseStatements.size(); i++) {
			CaseStatement s = mCaseStatements.get(i);
			if (s == null) {
				continue;
			}
			caseArray.add(s.toJSONObject());
		}
		if (!caseArray.isEmpty()) {
			o.put("_case", caseArray);
		}
		if (mDefaultStatement != null) {
			o.put("_default", mDefaultStatement.toJSONObject());
		}
		return o;
	}

	public boolean addCaseStatement(CaseStatement statement) {
		if (statement == null) {
			return false;
		}
		statement.attachEnvironmentArgs(getEnvironmentArgs());
		statement.setBelongStatement(this);
		mCaseStatements.add(statement);
		return true;
	}

	public void setDefaultStatement(DefaultStatement statement) {
		mDefaultStatement = statement;
		if (mDefaultStatement != null) {
			mDefaultStatement.setBelongStatement(this);
			mDefaultStatement.attachEnvironmentArgs(getEnvironmentArgs());
		}
	}

	public Word getLeftBrace() {
		return mLeftBrace;
	}

	public void setLeftBrace(Word leftBrace) {
		this.mLeftBrace = leftBrace;
	}

	public Word getRightBrace() {
		return mRightBrack;
	}

	public void setRightBrace(Word rightBrace) {
		this.mRightBrack = rightBrace;
	}
	
	public void setConditionExpression(Expression condition) {
		this.mExpression = condition;
	}
}
