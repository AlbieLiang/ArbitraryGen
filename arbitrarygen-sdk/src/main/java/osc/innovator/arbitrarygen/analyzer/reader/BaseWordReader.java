package osc.innovator.arbitrarygen.analyzer.reader;

import java.io.IOException;
import java.io.Reader;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.core.Word;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BaseWordReader {

	public static final char DOC_END_MARK = (char) -1;
	protected char mLastChar;

	/**
	 * Invoke {@link #nextChar(Reader)} to get next char.
	 * 
	 * @param reader
	 * @return
	 */
	public abstract Word read(IReader reader, char prefix);

	protected char nextChar(IReader reader) throws IOException {
		return mLastChar = (char) reader.read();
	}

	public char getLastChar() {
		return mLastChar;
	}

	public void setLastChar(char c) {
		mLastChar = c;
	}
	
	public static boolean isBlankChar(char c) {
		return (c == ' ' || c == '\n' || c == '\t' || c == '\r');
	}
}
