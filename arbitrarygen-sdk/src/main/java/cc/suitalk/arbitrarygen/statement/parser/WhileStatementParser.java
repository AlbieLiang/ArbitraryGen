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
import cc.suitalk.arbitrarygen.statement.WhileStatement;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class WhileStatementParser extends BaseStatementParser {

	public WhileStatementParser() {
		super("while");
	}

	@Override
	public WhileStatement parse(IReader reader, Lexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "while".equals(curWord.value)) {
				WhileStatement whileStm = new WhileStatement();
				whileStm.setPrefixWord(curWord);
				Word word = nextWord(reader, lexer);
				whileStm.setWordLeftBracket(word);
//				whileStm.setCommendBlock(getCommendStr());
				Expression condition = Util.extractExpressionFromBracket(reader, lexer, word, this);
				if (condition == null) {
					throw new RuntimeException("extract expression from bracket failed.");
				}
//				whileStm.setWordRightBracket(word);
				whileStm.setConditionExpression(condition);
				word = getLastWord();
				Util.getAndAttachCodeBlock(reader, lexer, word, whileStm, this);
				word = getLastWord();
				return whileStm;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
