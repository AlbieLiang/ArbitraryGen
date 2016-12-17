package cc.suitalk.arbitrarygen.statement.parser;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.expression.parser.PlainExpressionParser;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.statement.ThrowStatement;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ThrowStatementParser extends BaseStatementParser {

	public ThrowStatementParser() {
		super("throw");
	}

	@Override
	public ThrowStatement parse(IReader reader, Lexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "throw".equals(curWord.value)) {
				PlainExpressionParser parser = ParserFactory.getPlainExpressionParser();
				Expression expression = parser.parse(reader, lexer, nextWord(reader, lexer));
				if (expression != null) {
					ThrowStatement stm = new ThrowStatement(expression);
					stm.setPrefixWord(curWord);
					stm.setSuffixWord(parser.getLastWord());
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
