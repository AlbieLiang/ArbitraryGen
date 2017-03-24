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

package cc.suitalk.arbitrarygen.analyzer.reader;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.KeyWords.Sign.Type;
import cc.suitalk.arbitrarygen.core.Word.WordType;

/**
 * 
 * @author AlbieLiang
 *
 */
public class StringReader extends BaseWordReader {

	@Override
	public Word read(IReader reader, char prefix) {
		StringBuilder builder = new StringBuilder();
		Word word = new Word();
		WordType type = Word.WordType.STRING;
		char c = '\0';
		builder.append(prefix);
		try {
			while ((c = nextChar(reader)) != DOC_END_MARK) {
				if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
						|| (c == '_') || (c == '$') || (c >= '0' && c <= '9')) {
					builder.append(c);
				} else {
					break;
				}
			}
			word.value = builder.toString();
			word.mark = Type.NORMAL;
		} catch (IOException e) {
			type = WordType.ERROR;
			e.printStackTrace();
		}
		word.type = type;
		return word;
	}

}
