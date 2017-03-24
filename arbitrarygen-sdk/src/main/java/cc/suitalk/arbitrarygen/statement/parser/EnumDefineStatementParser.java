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

package cc.suitalk.arbitrarygen.statement.parser;

import java.util.HashMap;
import java.util.Map;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.extension.Lexer;

/**
 * 
 * @author AlbieLiang
 *
 */
public class EnumDefineStatementParser extends BaseStatementParser {

	public EnumDefineStatementParser() {
		super("import");
	}

	@Override
	public TypeDefineCodeBlock parse(IReader reader, Lexer lexer, Word curWord) {
		setLastWord(curWord);
		if (curWord == null) {
			return null;
		}
		TypeDefineCodeBlock typeDefine = new TypeDefineCodeBlock();
		Map<String, Word> keywords = new HashMap<String, Word>();
		Word word = curWord;
		while (!word.value.equals("{")) {
			if (word.type != WordType.STRING) {
				throw new RuntimeException("Error has occurred when parse type define code block before '{' appear.");
			}

			if (word.value.equals("class")) {
				;
			} else if (word.value.equals("interface")) {
				;
			} else if (word.value.equals("enum")) {
				;
			} else if (word.value.equals("@interface")) {
				// TODO
			}
			keywords.put(word.value, word);
		}
		return null;
	}
}