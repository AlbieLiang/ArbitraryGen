package osc.innovator.arbitrarygen.statement.parser;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.base.BaseStatementParser;
import osc.innovator.arbitrarygen.core.ParserFactory;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.expression.ReferenceExpression;
import osc.innovator.arbitrarygen.expression.parser.ReferenceExpressionParser;
import osc.innovator.arbitrarygen.extension.ILexer;
import osc.innovator.arbitrarygen.statement.ImportStatement;

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
	public ImportStatement parse(IReader reader, ILexer lexer, Word curWord) {
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
