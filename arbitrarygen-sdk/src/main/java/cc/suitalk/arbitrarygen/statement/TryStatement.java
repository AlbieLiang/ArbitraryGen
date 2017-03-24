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
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.core.Environment.EnvironmentArgs;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TryStatement extends BaseStatement {

	private List<CatchStatement> mCatchStatements;
	private FinallyStatement mFinallyStatement;

	public TryStatement() {
		mCatchStatements = new LinkedList<CatchStatement>();
		setCodeBlock(new PlainCodeBlock());
	}

	@Override
	public void onAttachEnvironmentArgs(EnvironmentArgs args) {
		for (int i = 0; i < mCatchStatements.size(); i++) {
			BaseStatement stm = mCatchStatements.get(i);
			stm.attachEnvironmentArgs(args);
		}
		if (mFinallyStatement != null) {
			mFinallyStatement.attachEnvironmentArgs(args);
		}
	}
	
	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "try"));
		builder.append(blank);
		builder.append(genPlainCodeBlock(linefeed));
		for (int i = 0; i < mCatchStatements.size(); i++) {
			CatchStatement s = mCatchStatements.get(i);
			if (s != null) {
				builder.append(blank);
				builder.append(s.genCode(linefeed));
			}
		}
		if (mFinallyStatement != null) {
			builder.append(blank);
			builder.append(mFinallyStatement.genCode(linefeed));
		}
		return builder.toString();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject o = super.toJSONObject();
		o.put("_type", "try");
		JSONArray catchArray = new JSONArray();
		for (int i = 0; i < mCatchStatements.size(); i++) {
			CatchStatement s = mCatchStatements.get(i);
			if (s == null) {
				continue;
			}
			catchArray.add(s.toJSONObject());
		}
		if (!catchArray.isEmpty()) {
			o.put("_catch", catchArray);
		}
		if (mFinallyStatement != null) {
			o.put("_finally", mFinallyStatement.toJSONObject());
		}
		return o;
	}

	public boolean addCatchStatement(CatchStatement statement) {
		if (statement == null) {
			return false;
		}
		statement.attachEnvironmentArgs(getEnvironmentArgs());
		statement.setBelongStatement(this);
		mCatchStatements.add(statement);
		return true;
	}

	public void setFinallyStatement(FinallyStatement statement) {
		mFinallyStatement = statement;
		if (mFinallyStatement != null) {
			mFinallyStatement.attachEnvironmentArgs(getEnvironmentArgs());
			mFinallyStatement.setBelongStatement(this);
		}
	}
}
