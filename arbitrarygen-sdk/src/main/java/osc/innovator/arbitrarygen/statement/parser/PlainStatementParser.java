package osc.innovator.arbitrarygen.statement.parser;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.base.BaseStatementParser;
import osc.innovator.arbitrarygen.base.PlainCodeBlock;
import osc.innovator.arbitrarygen.core.ParserFactory;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.core.Word.WordType;
import osc.innovator.arbitrarygen.extension.ILexer;
import osc.innovator.arbitrarygen.statement.PlainStatement;

/**
 * 
 * @author AlbieLiang
 *
 */
public class PlainStatementParser extends BaseStatementParser {

	private static final String TAG = "CodeGen.PlainStatementParser";

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
