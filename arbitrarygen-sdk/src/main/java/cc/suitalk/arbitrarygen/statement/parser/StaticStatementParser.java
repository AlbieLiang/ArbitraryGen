package cc.suitalk.arbitrarygen.statement.parser;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.statement.PlainStatement;
import cc.suitalk.arbitrarygen.statement.StaticStatement;

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
	public StaticStatement parse(IReader reader, Lexer lexer, Word curWord) {
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
