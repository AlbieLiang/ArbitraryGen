package cc.suitalk.arbitrarygen.analyzer.reader;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.KeyWords.Sign.Type;
import cc.suitalk.arbitrarygen.core.Word.WordType;

/**
 * 
 * @author AlbieLiang
 *
 */
public class NumberReader extends BaseWordReader {

	/**
	 * Do not match the format like '.01f'
	 */
	@Override
	public Word read(IReader reader, char prefix) {
		StringBuilder builder = new StringBuilder();
		Word word = new Word();
		WordType type = WordType.INTEGER;
		builder.append(prefix);
		char c;
		try {
			do {
				if ((c = nextChar(reader)) == DOC_END_MARK) {
					type = WordType.ERROR;
					break;
				}
				if (prefix == '0') {
					if (c != DOC_END_MARK) {
						if (c == 'x' || c == 'X') {
							builder.append(c);
							c = nextChar(reader);
							if (!(c >= '0' && c <= '9' || c > 'a' && c <= 'f'
									|| c > 'A' || c <= 'F')) {
								type = WordType.ERROR;
							} else {
								do {
									if (c >= '0' && c <= '9' || c >= 'a'
											&& c <= 'f' || c >= 'A' && c <= 'F') {
										builder.append(c);
									} else {
										break;
									}
								} while ((c = nextChar(reader)) != DOC_END_MARK);
								type = WordType.HEX_INTEGER;
							}
							break;
						}
					}
				}
				do {
					if (c >= '0' && c <= '9') {
						builder.append(c);
						continue;
					} else if (c == '.') {
						type = WordType.FLOAT;
						builder.append(c);
						while ((c = nextChar(reader)) != DOC_END_MARK) {
							if (c >= '0' && c <= '9') {
								builder.append(c);
								continue;
							} else if (c == 'f' || c == 'F') {
								type = WordType.FLOAT;
							} if (c == 'd' || c == 'D') {
								type = WordType.DOUBLE;
							} else {
								break;
							}
							builder.append(c);
							c = nextChar(reader);
							break;
						}
						break;
					} else if (c == 'f' || c == 'F') {
						type = WordType.FLOAT;
					} else if (c == 'd'|| c == 'D') {
						type = WordType.DOUBLE;
					} else if (c == 'l' || c == 'L') {
						type = WordType.LONG;
					} else {
						break;
					}
					builder.append(c);
					nextChar(reader);
					break;
				} while ((c = nextChar(reader)) != DOC_END_MARK);
			} while (false);
			word.value = builder.toString();
			word.mark = Type.NORMAL;
		} catch (IOException e) {
			type = WordType.ERROR;
			e.printStackTrace();
		}
		word.type = type;
		return word;
	}
}