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
import cc.suitalk.arbitrarygen.expression.parser.PlainExpressionParser;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.statement.ReturnStatement;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ReturnStatementParser extends BaseStatementParser {

	public ReturnStatementParser() {
		super("return");
	}

	@Override
	public ReturnStatement parse(IReader reader, Lexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "return".equals(curWord.value)) {
				Word word = nextWord(reader, lexer);
				PlainExpressionParser parser = ParserFactory.getPlainExpressionParser();
				ReturnStatement stm = new ReturnStatement();
				stm.setPrefixWord(curWord);
				Expression e = parser.parse(reader, lexer, word);
				if (e == null) {
					throw new RuntimeException("parse return expression error.");
				}
				stm.setStatement(e);
				stm.setSuffixWord(parser.getLastWord());
				nextWord(reader, lexer);
				return stm;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
