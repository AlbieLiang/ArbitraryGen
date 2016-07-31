package cc.suitalk.arbitrarygen.statement.parser;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.expression.ReferenceExpression;
import cc.suitalk.arbitrarygen.expression.parser.ReferenceExpressionParser;
import cc.suitalk.arbitrarygen.extension.ILexer;
import cc.suitalk.arbitrarygen.statement.ImportStatement;

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
