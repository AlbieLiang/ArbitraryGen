package osc.innovator.arbitrarygen.statement.parser;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.base.BaseStatementParser;
import osc.innovator.arbitrarygen.base.Expression;
import osc.innovator.arbitrarygen.core.ParserFactory;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.core.Word.WordType;
import osc.innovator.arbitrarygen.extension.ILexer;
import osc.innovator.arbitrarygen.model.TypeName;
import osc.innovator.arbitrarygen.statement.DefinitionStatement;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DefinitionStatementParser extends BaseStatementParser {

	public DefinitionStatementParser() {
		super("");
	}

	@Override
	public DefinitionStatement parse(IReader reader, ILexer lexer, Word curWord) {
		if (curWord != null) {
			try {
				super.parse(reader, lexer, curWord);
				curWord = getLastWord();
				boolean isFinal = false;
				Word word = curWord;
				if ("final".equals(word.value)) {
					isFinal = true;
					word = nextWord(reader, lexer);
				}
				TypeName.Parser tnParser = ParserFactory.getTypeNameParser();
				TypeName tn = tnParser.parse(reader, lexer, word);
				if (tn == null) {
					throw new RuntimeException("parse Definition statement error.");
				}
				word = tnParser.getLastWord();
//				PlainExpressionParser parser = ParserFactory.getPlainExpressionParser();
//				Expression e = parser.parse(reader, lexer, word);
//				String expression = Util.extractExpressionWithEndSign(reader, lexer, this, ")");
//				if (Util.isNullOrNil(expression)) {
//					throw new RuntimeException("parse return expression error.");
//				}
				if (word.type != WordType.STRING) {
					throw new RuntimeException("parse return expression error.(current word : " + word.value + ")");
				}
				Expression e = new Expression();
				e.setVariable(word.value);
				DefinitionStatement s = new DefinitionStatement(tn, isFinal, e);
				if (isFinal) {
					s.setWordFinal(curWord);
				}
//				s.setCommendBlock(getCommendStr());
				word = nextWord(reader, lexer);
				return s;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
