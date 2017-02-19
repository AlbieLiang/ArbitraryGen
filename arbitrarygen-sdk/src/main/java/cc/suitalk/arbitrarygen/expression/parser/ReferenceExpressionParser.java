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

package cc.suitalk.arbitrarygen.expression.parser;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseExpressionParser;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.expression.ReferenceExpression;
import cc.suitalk.arbitrarygen.extension.Lexer;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ReferenceExpressionParser extends BaseExpressionParser {

	private boolean mVariableOnly;

	public ReferenceExpressionParser(boolean variableOnly) {
		mVariableOnly = variableOnly;
	}

	@Override
	public ReferenceExpression parse(IReader reader, Lexer lexer, Word curWord) throws IOException {
		ReferenceExpression expression = new ReferenceExpression();
		Word word = null;
		if (curWord != null) {
			if (curWord.type != WordType.STRING) {
				throw new RuntimeException("parse ReferenceExpression error.(current word is : " + curWord.value + ").");
			}
			expression.appendNode(curWord);
			word = nextWord(reader, lexer);
			if (!".".equals(word.value)) {
				return expression;
			}
			expression.appendNode(word);
		}
		while ((word = nextWord(reader, lexer)) != null && word.type == WordType.STRING) {
			expression.appendNode(word);
			if ((word = nextWord(reader, lexer)) != null) {
				if (".".equals(word.value)) {
					expression.appendNode(word);
					continue;
				} else if ("*".equals(word.value)) {
					if (!mVariableOnly) {
						expression.appendNode(word);
						nextWord(reader, lexer);
						break;
					} else {
						throw new RuntimeException("'*' can not place here.");
					}
				}
			}
			break;
		}
		return expression;
	}
}
