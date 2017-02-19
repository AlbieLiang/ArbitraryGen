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
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.expression.ReferenceExpression;
import cc.suitalk.arbitrarygen.expression.parser.ReferenceExpressionParser;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.statement.ImportStatement;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ImportStatementParser extends BaseStatementParser {

	public ImportStatementParser() {
		super("import");
	}

	@Override
	public ImportStatement parse(IReader reader, Lexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "import".equals(curWord.value)) {
				ReferenceExpressionParser parser = ParserFactory.getRefExpressionParser(false);
				ReferenceExpression expression = parser.parse(reader, lexer, null);
				if (expression != null) {
					ImportStatement stm = new ImportStatement(expression);
					stm.setPrefixWord(curWord);
					stm.setSuffixWord(parser.getLastWord());
//					stm.setCommendBlock(getCommendStr());
					nextWord(reader, lexer);
					return stm;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
