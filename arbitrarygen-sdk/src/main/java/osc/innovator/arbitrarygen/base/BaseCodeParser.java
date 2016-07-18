package osc.innovator.arbitrarygen.base;

import java.io.IOException;
import java.io.Reader;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.extension.ILexer;

public abstract class BaseCodeParser {

	private Word mLastWord;

	/**
	 * Use {@link #nextWord(Reader, ILexer)} to get next word.
	 * 
	 * @param reader
	 * @param lexer
	 * @return
	 */
	public abstract ICodeGenerator parse(IReader reader, ILexer lexer, Word curWord) throws IOException;

	public Word getLastWord() {
		return mLastWord;
	}

	public void setLastWord(Word lastWord) {
		mLastWord = lastWord;
	}

	public Word nextWord(IReader reader, ILexer lexer) throws IOException {
		return mLastWord = lexer.getWord(reader);
	}
}
