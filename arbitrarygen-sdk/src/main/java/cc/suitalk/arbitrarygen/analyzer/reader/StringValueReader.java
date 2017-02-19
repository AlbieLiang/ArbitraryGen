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
import cc.suitalk.arbitrarygen.core.Word.WordType;

/**
 * 
 * @author AlbieLiang
 *
 */
public class StringValueReader extends BaseWordReader {

	@Override
	public Word read(IReader reader, char prefix) {
		StringBuilder builder = new StringBuilder();
		Word word = new Word();
		WordType type = Word.WordType.STRING;
		char c = '\0';
		char preChar = prefix;
		builder.append(prefix);
		try {
			if (prefix == '\'') {
				while ((c = nextChar(reader)) != DOC_END_MARK) {
					builder.append(c);
					if (c == '\'') {
						if (preChar != '\\') {
							c = nextChar(reader);
							break;
						}
					} else if (c == '\n' || c == '\r') {
						new IOException("\"'\" do not closed.");
					}
					preChar = c;
				}
				type = WordType.CHAR_VALUE;
			} else if (prefix == '\"') {
				while ((c = nextChar(reader)) != DOC_END_MARK) {
					builder.append(c);
					if (c == '\"') {
						if (preChar != '\\') {
							c = nextChar(reader);
							break;
						}
					} else if (c == '\n' || c == '\r') {
						new IOException("\"\"\" do not closed.");
					}
					preChar = c;
				}
				type = WordType.STRING_VALUE;
			}
			word.value = builder.toString();
		} catch (IOException e) {
			type = WordType.ERROR;
			// TODO Delete
			e.printStackTrace();
		}
		word.type = type;
		return word;
	}

}
