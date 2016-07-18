package osc.innovator.arbitrarygen.statement.parser;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.base.BaseStatementParser;
import osc.innovator.arbitrarygen.core.ParserFactory;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.expression.ReferenceExpression;
import osc.innovator.arbitrarygen.expression.parser.ReferenceExpressionParser;
import osc.innovator.arbitrarygen.extension.ILexer;
import osc.innovator.arbitrarygen.statement.PackageStatement;

/**
 * 
 * @author AlbieLiang
 *
 */
public class PackageStatementParser extends BaseStatementParser {

	public PackageStatementParser() {
		super("package");
	}

	@Override
	public PackageStatement parse(IReader reader, ILexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "package".equals(curWord.value)) {
				ReferenceExpressionParser parser = ParserFactory.getRefExpressionParser(false);
				ReferenceExpression expression = parser.parse(reader, lexer, null);
				if (expression != null) {
					PackageStatement stm = new PackageStatement(expression);
					stm.setPrefixWord(curWord);
					stm.setSuffixWord(parser.getLastWord());;
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
