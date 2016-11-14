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
//				forStm.setWordRightBracket(word);
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
