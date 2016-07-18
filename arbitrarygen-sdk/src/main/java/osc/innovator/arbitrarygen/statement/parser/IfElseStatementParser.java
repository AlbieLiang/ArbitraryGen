package osc.innovator.arbitrarygen.statement.parser;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.base.BaseStatementParser;
import osc.innovator.arbitrarygen.base.Expression;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.extension.ILexer;
import osc.innovator.arbitrarygen.statement.ElseIfStatement;
import osc.innovator.arbitrarygen.statement.ElseStatement;
import osc.innovator.arbitrarygen.statement.IfElseStatement;
import osc.innovator.arbitrarygen.utils.Util;

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
	public IfElseStatement parse(IReader reader, ILexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "if".equals(curWord.value)) {
				Word word = nextWord(reader, lexer);
				IfElseStatement ifElseStatement = new IfElseStatement();
				ifElseStatement.setPrefixWord(curWord);
				ifElseStatement.setWordLeftBracket(word);
//				ifElseStatement.setCommendBlock(getCommendStr());
				Expression condition = Util.extractExpressionFromBlacket(reader, lexer, word, this);
				if (condition == null) {
					throw new RuntimeException("extract expression from blacket failed.");
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
						Expression e = Util.extractExpressionFromBlacket(reader, lexer, word, this);
						if (e == null) {
							throw new RuntimeException("extract expression from blacket failed.");
						}
//						elseIfStatement.setWordRightBracket(word);
						elseIfStatement.setConditionExpression(e);
						ifElseStatement.addElseIfStatment(elseIfStatement);
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
