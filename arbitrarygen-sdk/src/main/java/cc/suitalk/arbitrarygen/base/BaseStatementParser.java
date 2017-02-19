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

package cc.suitalk.arbitrarygen.base;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.extension.Lexer;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BaseStatementParser extends BaseCodeParser {

	private String mPrefix;
//	private String mCommendStr;
	
	public BaseStatementParser(String prefix) {
		mPrefix = prefix;
	}

	public BaseStatement parse(IReader reader, Lexer lexer, Word curWord) throws IOException {
		doPreParse(reader, lexer, curWord);
		return null;
	}
	
	protected void doPreParse(IReader reader, Lexer lexer, Word curWord) throws IOException {
//		CommendStatementParser commStmParser = ParserFactory.getCommendStatementParser();
//		NormalStatement nStm = commStmParser.parse(reader, lexer, curWord);
//		setCommendStr(Util.nullAsNil(nStm != null ? nStm.getCommendBlock() : ""));
//		setLastWord(commStmParser.getLastWord());
		setLastWord(curWord);
	}
	
	public String getPrefix() {
		return mPrefix;
	}

	public void setPrefix(String prefix) {
		this.mPrefix = prefix;
	}

//	public String getCommendStr() {
//		return mCommendStr;
//	}
//
//	public void setCommendStr(String mCommendStr) {
//		this.mCommendStr = mCommendStr;
//	}
}
