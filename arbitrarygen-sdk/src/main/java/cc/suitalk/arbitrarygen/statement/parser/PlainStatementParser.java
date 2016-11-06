package cc.suitalk.arbitrarygen.statement.parser;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.extension.ILexer;
import cc.suitalk.arbitrarygen.statement.PlainStatement;

/**
 * 
 * @author AlbieLiang
 *
 */
public class PlainStatementParser extends BaseStatementParser {

	private static final String TAG = "AG.PlainStatementParser";

	public PlainStatementParser() {
		super("{");
	}

	@Override
	public PlainStatement parse(IReader reader, ILexer lexer, Word curWord) {
		try {
//			super.parse(reader, lexer, curWord);
//			curWord = getLastWord();
			setLastWord(curWord);
			if (curWord != null && "{".equals(curWord.value)) {
				PlainStatement statement = new PlainStatement();
				PlainCodeBlock cb = statement.getCodeBlock();
				cb.setLeftBrack(curWord);
//				statement.setCommendBlock(getCommendStr());
				NormalStatementParser parser = ParserFactory.getNormalStatementParser();
				Word word = nextWord(reader, lexer);
				while (word != null && word.type != WordType.DOC_END) {
//					Log.i(TAG, "plain current word (" + word.value + ").");
					BaseStatement s = parser.parse(reader, lexer, word);
					word = parser.getLastWord();
					if (s != null) {
						statement.addStatement(s);
					}
					if ("}".equals(word.value)) {
						cb.setRightBrack(word);
						nextWord(reader, lexer);
						return statement;
					}
				}
				throw new RuntimeException("missing '}' sign when parse PlainStatement.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
