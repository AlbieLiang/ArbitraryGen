package cc.suitalk.arbitrarygen.statement.parser;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.statement.NormalStatement;

/**
 * This parser is differ from other implement of the {@link BaseStatementParser}, 
 * it parse without prefix so it may not do a real parse if the first {@link Word} do not match the commend format.
 * 
 * @author AlbieLiang
 *
 */
@Deprecated
public class CommendStatementParser extends BaseStatementParser {

	public CommendStatementParser() {
		super("");
	}

	@Override
	public NormalStatement parse(IReader reader, Lexer lexer, Word curWord) throws IOException {
//		super.parse(reader, lexer, curWord);
//		curWord = getLastWord();
		StringBuilder builder = new StringBuilder();
		NormalStatement statement = null;
		setLastWord(curWord);
		Word word = curWord;
		try {
			while (word != null && word.type == WordType.COMMEND_STRING) {
				if (word != curWord) {
					builder.append(word.blankStr);
				}
				builder.append(word.value);
				word = nextWord(reader, lexer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (builder.length() > 0) {
			statement = new NormalStatement();
			statement.setCommendBlock(builder.toString());
		}
		return statement;
	}
}