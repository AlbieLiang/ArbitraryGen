package osc.innovator.arbitrarygen.core;

import osc.innovator.arbitrarygen.core.KeyWords.Sign;
import osc.innovator.arbitrarygen.core.KeyWords.Sign.Type;

/**
 * 
 * @author AlbieLiang
 *
 */
public class Word {
	public String value = "";
	public String blankStr = "";
	public WordType type = WordType.DOC_END;
	public Type mark;
	public Sign sign;

	public static enum WordType {
		DOC_END, ERROR, STRING, INTEGER, HEX_INTEGER,  DOUBLE, FLOAT, LONG, CHAR_VALUE, STRING_VALUE, SIGN, COMMEND_STRING, ANNOTATION
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(blankStr);
		builder.append(value);
		return builder.toString();
	}
}