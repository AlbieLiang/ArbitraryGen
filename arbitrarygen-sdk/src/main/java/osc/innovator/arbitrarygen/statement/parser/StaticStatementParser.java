package osc.innovator.arbitrarygen.statement.parser;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.base.BaseStatementParser;
import osc.innovator.arbitrarygen.core.ParserFactory;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.extension.ILexer;
import osc.innovator.arbitrarygen.statement.PlainStatement;
import osc.innovator.arbitrarygen.statement.StaticStatement;

/**
 * 
 * @author AlbieLiang
 *
 */
public class StaticStatementParser extends BaseStatementParser {

	public StaticStatementParser() {
		super("static");
	}

	@Override
	public StaticStatement parse(IReader reader, ILexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "static".equals(curWord.value)) {
				Word word = nextWord(reader, lexer);
				// static code block
				if ("{".equals(word.value)) {
					StaticStatement s = new StaticStatement();
					s.setPrefixWord(curWord);
//					s.setCommendBlock(getCommendStr());
					PlainStatementParser parser = ParserFactory.getPlainStatementParser();
					PlainStatement stm = parser.parse(reader, lexer, word);
					if (stm == null) {
						throw new RuntimeException("parse plain statment failed.");
					}
					s.setCodeBlock(stm.getCodeBlock());
					return s;
				} else {
					throw new RuntimeException("Parse static statement failed.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
