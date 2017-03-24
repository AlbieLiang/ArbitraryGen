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

import net.sf.json.JSONObject;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DoWhileStatement extends BaseStatement {

	private Expression mExpression;
	private Word mWordDo;
	private Word mWordWhile;
	
	public DoWhileStatement() {
		this(null);
	}

	public DoWhileStatement(Expression condition) {
		mExpression = condition;
		setCodeBlock(new PlainCodeBlock());
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);

		builder.append(genCommendBlock(linefeed));
		builder.append(mWordDo != null ? mWordDo : "do");
		builder.append(blank);
		builder.append(genPlainCodeBlock(linefeed));
		builder.append(blank);
		builder.append(mWordWhile != null ? mWordWhile : "while");
		builder.append(blank);
		builder.append(Util.getLeftBracket(this));
		builder.append(mExpression.genCode(linefeed));
		builder.append(Util.getRightBracket(this));
		builder.append(Util.getSuffix(this, ";"));
		return builder.toString();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject o = super.toJSONObject();
		o.put("_type", "doWhile");
		o.put("_condition", mExpression.toString());
		return o;
	}

	public void setConditionExpression(Expression condition) {
		this.mExpression = condition;
	}

	public Word getWordDo() {
		return mWordDo;
	}

	public void setWordDo(Word wordDo) {
		this.mWordDo = wordDo;
	}

	public Word getWordWhile() {
		return mWordWhile;
	}

	public void setWordWhile(Word wordWhile) {
		this.mWordWhile = wordWhile;
	}
}
