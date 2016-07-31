package cc.suitalk.arbitrarygen.statement.parser;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.expression.parser.PlainExpressionParser;
import cc.suitalk.arbitrarygen.extension.ILexer;
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
	public ReturnStatement parse(IReader reader, ILexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "return".equals(curWord.value)) {
				Word word = nextWord(reader, lexer);
				PlainExpressionParser parser = ParserFactory.getPlainExpressionParser();
				ReturnStatement stm = new ReturnStatement();
				stm.setPrefixWord(curWord);
//				s.setCommendBlock(getCommendStr());
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
