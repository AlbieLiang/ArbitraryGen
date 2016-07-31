package cc.suitalk.arbitrarygen.analyzer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class JavaStringLexer extends JavaLexer {

	public JavaStringLexer() {
	}
	
	public List<Word> parse(String javaString) throws IOException {
		if (Util.isNullOrNil(javaString)) {
			return null;
		}
		List<Word> results = new LinkedList<Word>();
		JavaStringReader reader = new JavaStringReader(javaString);
		Word word = null;
		while ((word = getWord(reader)) != null && word.type != WordType.DOC_END) {
			results.add(word);
		}
		return results;
	}
	
	/**
	 * 
	 * @author AlbieLiang
	 *
	 */
	public static class JavaStringReader implements IReader {

		private StringBuilder builder;
		private int mCurrentPos;
		
		public JavaStringReader(String javaString) {
			builder = new StringBuilder(javaString);
		}
		
		@Override
		public void close() throws IOException {
			builder.setLength(0);
		}

		@Override
		public int read() throws IOException {
			if (mCurrentPos >= builder.length()) {
				return -1;
			}
			return builder.charAt(mCurrentPos++);
		}
		
	}
}
