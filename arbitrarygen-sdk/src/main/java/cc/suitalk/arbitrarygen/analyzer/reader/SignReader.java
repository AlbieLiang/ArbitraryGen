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
import java.util.HashMap;
import java.util.Map;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.core.KeyWords;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.KeyWords.Sign;
import cc.suitalk.arbitrarygen.core.KeyWords.Sign.Type;
import cc.suitalk.arbitrarygen.core.Word.WordType;

/**
 * 
 * @author AlbieLiang
 *
 */
public class SignReader extends BaseWordReader {

	public static Map<String, Sign> sSignTypes;
	static {
		sSignTypes = new HashMap<String, KeyWords.Sign>();
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_LEFT_SEQUARE_BRACKET, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_LEFT_SEQUARE_BRACKET, Sign.Type.BEGIN));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_RIGHT_SEQUARE_BRACKET, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_RIGHT_SEQUARE_BRACKET, Sign.Type.END));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_LEFT_BRACKET, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_LEFT_BRACKET, Sign.Type.BEGIN));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_RIGHT_BRACKET, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_RIGHT_BRACKET, Sign.Type.END));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_LEFT_BRACE, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_LEFT_BRACE, Sign.Type.BEGIN));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_RIGHT_BRACE, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_RIGHT_BRACE, Sign.Type.END));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_EQUAL, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_EQUAL, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_ASSIGNMENT, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_ASSIGNMENT, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_DOT, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_DOT, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_COLON, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_COLON, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_SEMICOLON, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_SEMICOLON, Sign.Type.END));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_QUESTION_MARK, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_QUESTION_MARK, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_BIT_AND, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_BIT_AND, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_NOR, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_NOR, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_BIT_OR, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_BIT_OR, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_BIT_NOT, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_BIT_NOT, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_NOT, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_NOT, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_AND, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_AND, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_OR, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_OR, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_MULTIPLE, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_MULTIPLE, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_PERCENT, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_PERCENT, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_DOUBLE_DIVISION, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_DOUBLE_DIVISION, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_MINUS, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_MINUS, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_PLUS, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_PLUS, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_DOUBLE_MINUS, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_DOUBLE_MINUS, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_DOUBLE_PLUS, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_DOUBLE_PLUS, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_LT, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_LT, Sign.Type.BEGIN));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_GT, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_GT, Sign.Type.END));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_LE, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_LE, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_GE, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_GE, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_NE, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_NE, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_LEFT_SHIFT, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_LEFT_SHIFT, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_RIGHT_SHIFT, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_RIGHT_SHIFT, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_PLUS_EQUAL, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_PLUS_EQUAL, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_MINUS_EQUAL, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_MINUS_EQUAL, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_BIT_NOT_EQUAL, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_BIT_NOT_EQUAL, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_COMMA, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_COMMA, Sign.Type.NORMAL));
		sSignTypes.put(KeyWords.V_JAVA_KEYWORDS_SIGN_OR_ASSIGNMENT, new Sign(KeyWords.V_JAVA_KEYWORDS_SIGN_OR_ASSIGNMENT, Sign.Type.NORMAL));
	}

	@Override
	public Word read(IReader reader, char prefix) {
		Word word = new Word();
		word.type = Word.WordType.SIGN;
		char c = '\0';
		Sign s =  null;
		try {
			if ((c = nextChar(reader)) == DOC_END_MARK) {
				s = sSignTypes.get("" + prefix);
				if (s != null) {
					word.mark = s.getType();
					word.value = s.getValue();
					word.sign = s;
				} else {
					word.type = Word.WordType.ERROR;
				}
			} else {
				String temp = prefix + "" + c;
				s = sSignTypes.get(temp);
				if (s != null) {
					word.mark = s.getType();
					word.value = s.getValue();
					word.sign = s;
					nextChar(reader);
				} else {
					if ("/*".equals(temp) || "//".equals(temp)) {
						// commend "/**/" or "//"
						StringBuilder builder = new StringBuilder();
						builder.append(temp);
						if ("/*".equals(temp)) {
							while ((c = nextChar(reader)) != DOC_END_MARK) {
								builder.append(c);
								if (c == '*') {
									c = nextChar(reader);
									builder.append(c);
									if (c == '/') {
										break;
									} else if (c == DOC_END_MARK){
										word.type = WordType.ERROR;
										break;
									}
								}
							}
							word.type = Word.WordType.COMMEND_STRING;
						} else if ("//".equals(temp)) {
							while (((c = nextChar(reader)) != DOC_END_MARK) && (c != '\n' && c != '\r')) {
								builder.append(c);
							}
							// add '\n' or 'r\'
							builder.append(c);
							word.type = Word.WordType.COMMEND_STRING;
						}
						word.value = builder.toString();
						word.mark = Type.NORMAL;
						nextChar(reader);
					} else {
						s = sSignTypes.get("" + prefix);
						if (s != null) {
							if (("-".equals(s.getValue()) || "+".equals(s.getValue())) && (c >= '0' && c <= '9')) {
								NumberReader numberReader = new NumberReader();
								word = numberReader.read(reader, c);
								word.value = s.getValue() + word.value;
								setLastChar(numberReader.getLastChar());
								return word;
							}
							word.mark = s.getType();
							word.value = s.getValue();
							word.sign = s;
						} else {
							word.type = Word.WordType.ERROR;
						}
					}
				}
			}
		} catch (IOException e) {
			word.type = WordType.ERROR;
			e.printStackTrace();
		}
		return word;
	}
}
