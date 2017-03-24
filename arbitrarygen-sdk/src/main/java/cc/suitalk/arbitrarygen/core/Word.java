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

package cc.suitalk.arbitrarygen.core;

import cc.suitalk.arbitrarygen.core.KeyWords.Sign;
import cc.suitalk.arbitrarygen.core.KeyWords.Sign.Type;

/**
 * 
 * @author AlbieLiang
 *
 */
public class Word {
	public String value = "";
	public String blankStr = "";
	public WordType type = WordType.DOC_END;
	public Type mark;
	public Sign sign;

	public static enum WordType {
		DOC_END, ERROR, STRING, INTEGER, HEX_INTEGER,  DOUBLE, FLOAT, LONG, CHAR_VALUE, STRING_VALUE, SIGN, COMMEND_STRING, ANNOTATION
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(blankStr);
		builder.append(value);
		return builder.toString();
	}
}