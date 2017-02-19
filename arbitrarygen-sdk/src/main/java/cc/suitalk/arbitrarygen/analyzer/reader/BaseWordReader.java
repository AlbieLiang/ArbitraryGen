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

package cc.suitalk.arbitrarygen.analyzer.reader;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.core.Word;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BaseWordReader {

	public static final char DOC_END_MARK = (char) -1;
	protected char mLastChar;

	/**
	 * Invoke {@link #nextChar(IReader)} to get next char.
	 * 
	 * @param reader a char reader for the word reader
	 * @param prefix next word prefix char
	 * @return next word by the given prefix
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
