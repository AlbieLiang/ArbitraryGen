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
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.statement.ForStatement;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ForStatementParser extends BaseStatementParser {

	public ForStatementParser() {
		super("for");
	}

	@Override
	public ForStatement parse(IReader reader, Lexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "for".equals(curWord.value)) {
				Word word = nextWord(reader, lexer);
				ForStatement forStm = new ForStatement();
//				forStm.setCommendBlock(getCommendStr());
				if (!"(".equals(word.value)) {
					throw new RuntimeException("miss '(' when parse for statement.");
				}
				forStm.setWordLeftBracket(word);
				word = nextWord(reader, lexer);
				// TODO
//				PlainExpressionParser parser = ParserFactory.getPlainExpressionParser();
//				Expression condition = parser.parse(reader, lexer, word);
				Expression condition = Util.extractExpression(reader, lexer, word, this, ")");
				// condition1
				if (condition == null) {
					throw new RuntimeException("parse for statement failed.");
				}
				forStm.setWordRightBracket(word);
				forStm.setExpression1(condition);
				// condition2
//				word = nextWord(reader, lexer);
//				if (!word.value.equals(")")) {
//					Expression condition2 = parser.parse(reader, lexer, word);
//					if (condition2 == null) {
//						throw new RuntimeException("parse for statement condition2 failed.");
//					}
//					forStm.setExpression2(condition2);
//					word = nextWord(reader, lexer);
//					Expression condition3 = parser.parse(reader, lexer, word);
//					forStm.setExpression3(condition3);
//					word = nextWord(reader, lexer);
//				}
				word = nextWord(reader, lexer);
				Util.getAndAttachCodeBlock(reader, lexer, word, forStm, this);
				word = getLastWord();
				return forStm;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
