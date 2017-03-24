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
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.model.TypeName;
import cc.suitalk.arbitrarygen.statement.DefinitionStatement;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DefinitionStatementParser extends BaseStatementParser {

	public DefinitionStatementParser() {
		super("");
	}

	@Override
	public DefinitionStatement parse(IReader reader, Lexer lexer, Word curWord) {
		if (curWord != null) {
			try {
				super.parse(reader, lexer, curWord);
				curWord = getLastWord();
				boolean isFinal = false;
				Word word = curWord;
				if ("final".equals(word.value)) {
					isFinal = true;
					word = nextWord(reader, lexer);
				}
				TypeName.Parser tnParser = ParserFactory.getTypeNameParser();
				TypeName tn = tnParser.parse(reader, lexer, word);
				if (tn == null) {
					throw new RuntimeException("parse Definition statement error.");
				}
				word = tnParser.getLastWord();
//				PlainExpressionParser parser = ParserFactory.getPlainExpressionParser();
//				Expression e = parser.parse(reader, lexer, word);
//				String expression = Util.extractExpressionWithEndSign(reader, lexer, this, ")");
//				if (Util.isNullOrNil(expression)) {
//					throw new RuntimeException("parse return expression error.");
//				}
				if (word.type != WordType.STRING) {
					throw new RuntimeException("parse return expression error.(current word : " + word.value + ")");
				}
				Expression e = new Expression();
				e.setVariable(word.value);
				DefinitionStatement s = new DefinitionStatement(tn, isFinal, e);
				if (isFinal) {
					s.setWordFinal(curWord);
				}
//				s.setCommendBlock(getCommendStr());
				word = nextWord(reader, lexer);
				return s;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
