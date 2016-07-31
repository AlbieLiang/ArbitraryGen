package cc.suitalk.arbitrarygen.core;

import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class Value {

	private Object value;
	private ValueType type;
	private String valueStr;
	private String blankStr = "";

	public Value() {
	}

	public Value(Object value, ValueType type, String valueStr) {
		this.value = value;
		this.type = type;
		this.valueStr = valueStr;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(Util.nullAsNil(blankStr));
		builder.append(valueStr);
		return builder.toString();
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public ValueType getType() {
		return type;
	}

	public void setType(ValueType type) {
		this.type = type;
	}

	public String getValueStr() {
		return valueStr;
	}

	public void setValueStr(String valueStr) {
		this.valueStr = valueStr;
	}

	public String getBlankStr() {
		return blankStr;
	}

	public void setBlankStr(String blankStr) {
		this.blankStr = blankStr;
	}

	public static enum ValueType {
		REFERENCE, STRING, FLOAT, DOUBLE, BYTE, INTEGER, CHARACTER, SHORT, LONG, ARRAY
	}
}
