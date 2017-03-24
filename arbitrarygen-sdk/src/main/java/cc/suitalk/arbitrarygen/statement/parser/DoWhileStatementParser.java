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
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.statement.DoWhileStatement;
import cc.suitalk.arbitrarygen.statement.PlainStatement;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DoWhileStatementParser extends BaseStatementParser {

	public DoWhileStatementParser() {
		super("do");
	}

	@Override
	public DoWhileStatement parse(IReader reader, Lexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "do".equals(curWord.value)) {
				PlainStatementParser parser = ParserFactory.getPlainStatementParser();
				Word word = curWord;
				PlainStatement pstm = parser.parse(reader, lexer, word);
				if (pstm != null) {
					DoWhileStatement stm = new DoWhileStatement();
					stm.setWordDo(curWord);
//					s.setCommendBlock(getCommendStr());
					stm.setCodeBlock(pstm.getCodeBlock());
					word = parser.getLastWord();
					if (!"while".equals(word.value)) {
						throw new RuntimeException("missed 'while' when parse do-while statement.");
					}
					stm.setWordWhile(word);
					word = nextWord(reader, lexer);
					Expression e = Util.extractExpressionFromBracket(reader, lexer, word, this);
					if (e == null) {
						throw new RuntimeException("extract Expression from bracket failed.");
					}
					stm.setConditionExpression(e);
					word = nextWord(reader, lexer);
					if (!";".equals(word.value)) {
						throw new RuntimeException("missing a ';' sign when parse do-while statement.");
					}
					return stm;
				} else {
					throw new RuntimeException("Parse do-while statement failed.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
