/*
 *  Copyright (C) 2016-present Albie Liang. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
