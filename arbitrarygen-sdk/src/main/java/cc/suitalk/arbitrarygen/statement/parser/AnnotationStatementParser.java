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

package cc.suitalk.arbitrarygen.statement.parser;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Value;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Value.ValueType;
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.expression.ReferenceExpression;
import cc.suitalk.arbitrarygen.expression.parser.ReferenceExpressionParser;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.model.TypeName;
import cc.suitalk.arbitrarygen.statement.AnnotationStatement;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class AnnotationStatementParser extends BaseStatementParser {

	public AnnotationStatementParser() {
		super("@");
	}

	@Override
	public AnnotationStatement parse(IReader reader, Lexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord == null || !"@".equals(curWord.value)) {
				return null;
			}
			Word word = nextWord(reader, lexer);
			TypeName.Parser tnp = ParserFactory.getTypeNameParser();
			AnnotationStatement annotation = new AnnotationStatement(tnp.parse(reader, lexer, word));
			annotation.setPrefixWord(curWord);
			setLastWord(tnp.getLastWord());
			word = getLastWord();
//			annotation.setCommendBlock(getCommendStr());
//			word = nextWord(reader, lexer);
			if (word != null && "(".equals(word.value)) {
				annotation.setWordLeftBracket(word);
				if ((word = nextWord(reader, lexer)) == null) {
					doThrow(annotation);
				}
				if (!")".equals(word.value)) {
					if (word.type == WordType.STRING) {
						String name = word.value;
						word = nextWord(reader, lexer);
						if (word.type != WordType.SIGN) {
							doThrow(annotation);
						}
						if (".".equals(word.value)) {
							ReferenceExpressionParser parser = ParserFactory.getRefExpressionParser(true);
							ReferenceExpression e = parser.parse(reader, lexer, null);
							Word lastWord = parser.getLastWord();
							if (")".equals(lastWord.value)) {
								StringBuilder builder = new StringBuilder();
								builder.append(name);
								builder.append(word.value);
								builder.append(e.genCode(""));
								annotation.setValue(new Value(null, ValueType.REFERENCE, builder.toString()));
								word = lastWord;
							} else {
								doThrow(annotation);
							}
						} else if ("=".equals(word.value)) {
							Value value = Util.getValue(reader, lexer, this);
							if (value == null) {
								doThrow(annotation);
							}
							annotation.setArg(name, value);
							word = getLastWord();
							while (",".equals(word.value)) {
								word = nextWord(reader, lexer);
								if (word.type != WordType.STRING) {
									doThrow(annotation);
									break;
								}
								String keyName = word.value;
								word = nextWord(reader, lexer);
								if (!"=".equals(word.value)) {
									doThrow(annotation);
									break;
								}
								value = Util.getValue(reader, lexer, this);
								if (value == null) {
									doThrow(annotation);
									break;
								}
								annotation.setArg(keyName, value);
								word = getLastWord();
								if (")".equals(word.value)) {
									break;
								}
							}
						}
					} else {
						annotation.setValue(Util.convertTo(word));
						word = nextWord(reader, lexer);
						if (!")".equals(word.value)) {
							doThrow(annotation);
						}
					}
				}
				if (!")".equals(word.value)) {
					doThrow(annotation);
				}
				annotation.setWordRightBracket(word);
				nextWord(reader, lexer);
			}
			return annotation;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void doThrow(AnnotationStatement s) {
		throw new RuntimeException("An error has occured when parse Annotation '@" + s.getName() + "'.");
	}
}