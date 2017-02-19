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
public class NumberReader extends BaseWordReader {

	/**
	 * Do not match the format like '.01f'
	 */
	@Override
	public Word read(IReader reader, char prefix) {
		StringBuilder builder = new StringBuilder();
		Word word = new Word();
		WordType type = WordType.INTEGER;
		builder.append(prefix);
		char c;
		try {
			do {
				if ((c = nextChar(reader)) == DOC_END_MARK) {
					type = WordType.ERROR;
					break;
				}
				if (prefix == '0') {
					if (c != DOC_END_MARK) {
						if (c == 'x' || c == 'X') {
							builder.append(c);
							c = nextChar(reader);
							if (!(c >= '0' && c <= '9' || c > 'a' && c <= 'f'
									|| c > 'A' || c <= 'F')) {
								type = WordType.ERROR;
							} else {
								do {
									if (c >= '0' && c <= '9' || c >= 'a'
											&& c <= 'f' || c >= 'A' && c <= 'F') {
										builder.append(c);
									} else {
										break;
									}
								} while ((c = nextChar(reader)) != DOC_END_MARK);
								type = WordType.HEX_INTEGER;
							}
							break;
						}
					}
				}
				do {
					if (c >= '0' && c <= '9') {
						builder.append(c);
						continue;
					} else if (c == '.') {
						type = WordType.FLOAT;
						builder.append(c);
						while ((c = nextChar(reader)) != DOC_END_MARK) {
							if (c >= '0' && c <= '9') {
								builder.append(c);
								continue;
							} else if (c == 'f' || c == 'F') {
								type = WordType.FLOAT;
							} if (c == 'd' || c == 'D') {
								type = WordType.DOUBLE;
							} else {
								break;
							}
							builder.append(c);
							c = nextChar(reader);
							break;
						}
						break;
					} else if (c == 'f' || c == 'F') {
						type = WordType.FLOAT;
					} else if (c == 'd'|| c == 'D') {
						type = WordType.DOUBLE;
					} else if (c == 'l' || c == 'L') {
						type = WordType.LONG;
					} else {
						break;
					}
					builder.append(c);
					nextChar(reader);
					break;
				} while ((c = nextChar(reader)) != DOC_END_MARK);
			} while (false);
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