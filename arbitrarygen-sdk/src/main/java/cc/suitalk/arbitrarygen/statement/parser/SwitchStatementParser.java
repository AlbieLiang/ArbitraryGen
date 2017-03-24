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
import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.statement.CaseStatement;
import cc.suitalk.arbitrarygen.statement.DefaultStatement;
import cc.suitalk.arbitrarygen.statement.SwitchStatement;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class SwitchStatementParser extends BaseStatementParser {

	private static final String TAG = "AG.SwitchStatementParser";

	public SwitchStatementParser() {
		super("switch");
	}

	@Override
	public SwitchStatement parse(IReader reader, Lexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "switch".equals(curWord.value)) {
				SwitchStatement switchStm = new SwitchStatement();
				switchStm.setPrefixWord(curWord);
				Word word = nextWord(reader, lexer);
				switchStm.setWordLeftBracket(word);
				Expression condition = Util.extractExpressionFromBracket(reader, lexer, word, this);
				if (condition == null) {
					throw new RuntimeException("extract expression from bracket failed.");
				}
				switchStm.setConditionExpression(condition);
//				switchStm.setCommendBlock(getCommendStr());
				word = getLastWord();
				if (!"{".equals(word.value)) {
					throw new RuntimeException("missing '{' when parse switch statement.");
				}
				switchStm.setLeftBrace(word);
				word = nextWord(reader, lexer);
				while ("case".equals(word.value)) {
					Word tempWord = word;
					Expression expression = new Expression();
					expression.setVariable(Util.extractExpressionWithEndSign(reader, lexer, this, ":"));
					CaseStatement caseStm = new CaseStatement(expression);
					caseStm.setPrefixWord(tempWord);
					caseStm.setWordColon(getLastWord());
					switchStm.addCaseStatement(caseStm);
					word = nextWord(reader, lexer);
					while (!"}".equals(word.value) && word.type != WordType.DOC_END) {
						if ("default".equals(word.value) || "case".equals(word.value)) {
							break;
						}
						NormalStatementParser parser = ParserFactory.getNormalStatementParser();
						BaseStatement nStm = parser.parse(reader, lexer, word);
						caseStm.addStatement(nStm);
						word = parser.getLastWord();
					}
				}
				if ("default".equals(word.value)) {
					DefaultStatement defaultStm = new DefaultStatement();
					switchStm.setDefaultStatement(defaultStm);
					defaultStm.setPrefixWord(word);
					word = nextWord(reader, lexer);
					if (!":".equals(word.value)) {
						throw new RuntimeException("missing ':' sign.");
					}
					defaultStm.setWordColon(word);
					word = nextWord(reader, lexer);
					while (!"}".equals(word.value) && word.type != WordType.DOC_END) {
						NormalStatementParser parser = ParserFactory.getNormalStatementParser();
						BaseStatement nStm = parser.parse(reader, lexer, word);
						defaultStm.addStatement(nStm);
						word = parser.getLastWord();
					}
				}
				if (!"}".equals(word.value)) {
					throw new RuntimeException("missing '}' sign when parse switch statement.");
				}
				switchStm.setRightBrace(word);
				nextWord(reader, lexer);
				return switchStm;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
