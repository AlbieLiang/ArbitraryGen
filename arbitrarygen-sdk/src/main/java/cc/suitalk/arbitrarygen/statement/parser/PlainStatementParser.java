package cc.suitalk.arbitrarygen.statement.parser;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.statement.PlainStatement;
import cc.suitalk.arbitrarygen.utils.Log;

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
	public PlainStatement parse(IReader reader, Lexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			if (curWord != null && "{".equals(curWord.value)) {
				PlainStatement statement = new PlainStatement();
				PlainCodeBlock cb = statement.getCodeBlock();
				cb.setLeftBrace(curWord);
				NormalStatementParser parser = ParserFactory.getNormalStatementParser();
				Word word = nextWord(reader, lexer);
				while (word != null && !"}".equals(word.value) && word.type != WordType.DOC_END) {
//					Log.i(TAG, "plain current word (" + word.value + ").");
					BaseStatement s = parser.parse(reader, lexer, word);
					word = parser.getLastWord();
					if (s != null) {
						statement.addStatement(s);
					}
				}
				if (!"}".equals(word.value)) {
					throw new RuntimeException("missing '}' sign when parse PlainStatement.");
				}
				cb.setRightBrace(word);
				nextWord(reader, lexer);
				return statement;
			}
		} catch (IOException e) {
			Log.w(TAG, "parse PlainStatement error. Exception : %s", Log.getStackTraceString(e));
		}
		return null;
	}
}
