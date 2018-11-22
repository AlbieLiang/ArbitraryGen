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
import cc.suitalk.arbitrarygen.statement.ElseIfStatement;
import cc.suitalk.arbitrarygen.statement.ElseStatement;
import cc.suitalk.arbitrarygen.statement.IfElseStatement;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class IfElseStatementParser extends BaseStatementParser {

	public IfElseStatementParser() {
		super("if");
	}

	@Override
	public IfElseStatement parse(IReader reader, Lexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "if".equals(curWord.value)) {
				Word word = nextWord(reader, lexer);
				IfElseStatement ifElseStatement = new IfElseStatement();
				ifElseStatement.setPrefixWord(curWord);
				ifElseStatement.setWordLeftBracket(word);
				Expression condition = Util.extractExpressionFromBracket(reader, lexer, word, this);
				if (condition == null) {
					throw new RuntimeException("illegal if statement, extract expression from bracket failed.");
				}
//				ifElseStatement.setWordRightBracket(word);
				ifElseStatement.setConditionExpression(condition);
				word = getLastWord();
				Util.getAndAttachCodeBlock(reader, lexer, word, ifElseStatement, this);
				word = getLastWord();
				while ("else".equals(word.value)) {
					Word tempWord = word;
					word = nextWord(reader, lexer);
					if ("if".equals(word.value)) {
						ElseIfStatement elseIfStatement = new ElseIfStatement();
						elseIfStatement.setWordElse(tempWord);
						elseIfStatement.setWordIf(word);
						word = nextWord(reader, lexer);
						elseIfStatement.setWordLeftBracket(word);
						Expression e = Util.extractExpressionFromBracket(reader, lexer, word, this);
						if (e == null) {
							throw new RuntimeException("extract expression from bracket failed.");
						}
//						elseIfStatement.setWordRightBracket(word);
						elseIfStatement.setConditionExpression(e);

						word = getLastWord();
						Util.getAndAttachCodeBlock(reader, lexer, word, elseIfStatement, this);
						word = getLastWord();
						ifElseStatement.addElseIfStatement(elseIfStatement);
					} else {
						ElseStatement elseStatement = new ElseStatement();
						elseStatement.setPrefixWord(tempWord);
						Util.getAndAttachCodeBlock(reader, lexer, word, elseStatement, this);
						ifElseStatement.setElseStatement(elseStatement);
						break;
					}
				}
				return ifElseStatement;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
