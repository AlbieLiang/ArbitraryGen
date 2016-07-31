package cc.suitalk.arbitrarygen.analyzer.reader;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Word.WordType;

/**
 * 
 * @author AlbieLiang
 *
 */
public class StringValueReader extends BaseWordReader {

	@Override
	public Word read(IReader reader, char prefix) {
		StringBuilder builder = new StringBuilder();
		Word word = new Word();
		WordType type = Word.WordType.STRING;
		char c = '\0';
		char preChar = prefix;
		builder.append(prefix);
		try {
			if (prefix == '\'') {
				while ((c = nextChar(reader)) != DOC_END_MARK) {
					builder.append(c);
					if (c == '\'') {
						if (preChar != '\\') {
							c = nextChar(reader);
							break;
						}
					} else if (c == '\n' || c == '\r') {
						new IOException("\"'\" do not closed.");
					}
					preChar = c;
				}
				type = WordType.CHAR_VALUE;
			} else if (prefix == '\"') {
				while ((c = nextChar(reader)) != DOC_END_MARK) {
					builder.append(c);
					if (c == '\"') {
						if (preChar != '\\') {
							c = nextChar(reader);
							break;
						}
					} else if (c == '\n' || c == '\r') {
						new IOException("\"\"\" do not closed.");
					}
					preChar = c;
				}
				type = WordType.STRING_VALUE;
			}
			word.value = builder.toString();
		} catch (IOException e) {
			type = WordType.ERROR;
			// TODO Delete
			e.printStackTrace();
		}
		word.type = type;
		return word;
	}

}
