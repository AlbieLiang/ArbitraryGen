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

package cc.suitalk.arbitrarygen.block;

import net.sf.json.JSONObject;

import cc.suitalk.arbitrarygen.base.BaseDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.KeyWords;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class FieldCodeBlock extends BaseDefineCodeBlock {

	private String Default;
	private boolean genGetter;
	private boolean genSetter;
	private Word mWordAssignment;

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
//		builder.append(genCommendBlock(linefeed));
//		builder.append(genAnnotationBlock(linefeed));
		builder.append(genDefCode(this, linefeed));
		String blank = getWordBlank(BLANK_1);
		if (!Util.isNullOrNil(Default)) {
			builder.append(blank);
			builder.append(mWordAssignment != null ? mWordAssignment : KeyWords.V_JAVA_KEYWORDS_SIGN_ASSIGNMENT);
			builder.append(blank);
			builder.append(Default);
		}
		builder.append(Util.getSuffix(this, KeyWords.V_JAVA_KEYWORDS_SIGN_SEMICOLON));
		return builder.toString();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = super.toJSONObject();
		jsonObject.put("_default", Default);
		return jsonObject;
	}

	public String getDefault() {
		return Default;
	}

	public void setDefault(String default1) {
		Default = default1;
	}

	public boolean isGenGetter() {
		return genGetter;
	}

	public void setGenGetter(boolean genGetter) {
		this.genGetter = genGetter;
	}

	public boolean isGenSetter() {
		return genSetter;
	}

	public void setGenSetter(boolean genSetter) {
		this.genSetter = genSetter;
	}

	public Word getWordAssignment() {
		return mWordAssignment;
	}

	public void setWordAssignment(Word wordAssignment) {
		this.mWordAssignment = wordAssignment;
	}
}