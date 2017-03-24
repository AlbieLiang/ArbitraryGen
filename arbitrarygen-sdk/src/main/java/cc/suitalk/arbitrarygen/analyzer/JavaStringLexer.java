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
