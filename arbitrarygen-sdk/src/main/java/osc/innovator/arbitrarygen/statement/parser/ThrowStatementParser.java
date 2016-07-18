package osc.innovator.arbitrarygen.statement.parser;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.base.BaseStatementParser;
import osc.innovator.arbitrarygen.base.Expression;
import osc.innovator.arbitrarygen.core.ParserFactory;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.expression.parser.PlainExpressionParser;
import osc.innovator.arbitrarygen.extension.ILexer;
import osc.innovator.arbitrarygen.statement.ThrowStatement;

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
	public ThrowStatement parse(IReader reader, ILexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "throw".equals(curWord.value)) {
				PlainExpressionParser parser = ParserFactory.getPlainExpressionParser();
				Expression expression = parser.parse(reader, lexer, nextWord(reader, lexer));
				if (expression != null) {
					ThrowStatement stm = new ThrowStatement(expression);
					stm.setPrefixWord(curWord);
//					s.setCommendBlock(getCommendStr());
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
