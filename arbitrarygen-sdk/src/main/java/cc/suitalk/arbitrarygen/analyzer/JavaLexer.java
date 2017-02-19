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

import cc.suitalk.arbitrarygen.analyzer.reader.BaseWordReader;
import cc.suitalk.arbitrarygen.analyzer.reader.NumberReader;
import cc.suitalk.arbitrarygen.analyzer.reader.SignReader;
import cc.suitalk.arbitrarygen.analyzer.reader.StringReader;
import cc.suitalk.arbitrarygen.analyzer.reader.StringValueReader;
import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Environment.EnvironmentArgs;
import cc.suitalk.arbitrarygen.core.KeyWords.Sign.Type;
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.statement.ImportStatement;
import cc.suitalk.arbitrarygen.statement.PackageStatement;
import cc.suitalk.arbitrarygen.statement.parser.ImportStatementParser;
import cc.suitalk.arbitrarygen.statement.parser.PackageStatementParser;
import cc.suitalk.arbitrarygen.statement.parser.TypeDefineStatementParser;
import cc.suitalk.arbitrarygen.utils.Log;

/**
 * 
 * @author AlbieLiang
 *
 */
public class JavaLexer implements Lexer {

	private static final String TAG = "AG.JavaLexer";

	private int mDocEnd;
	private char mCurChar;
	private BaseWordReader mNumberReader;
	private BaseWordReader mStringReader;
	private BaseWordReader mSignReader;
	private BaseWordReader mStringValueReader;

	public JavaLexer() {
		mCurChar = '\0';
		mNumberReader = new NumberReader();
		mStringReader = new StringReader();
		mSignReader = new SignReader();
		mStringValueReader = new StringValueReader();
	}
	
	@Override
	public Word getWord(IReader reader) throws IOException {
		Word word = null;
		StringBuilder builder = new StringBuilder();
		// use nil to replace null.
		builder.append("");
		if (mCurChar == '\0') {
			mCurChar = (char) reader.read();
			Log.v(TAG, ">>>>>start parse java file.");
		}
		if (mCurChar == BaseWordReader.DOC_END_MARK) {
			Log.v(TAG, ">>>>>doc end.");
			if (mDocEnd++ == 10) {
				throw new DocEndException();
			}
			return new Word();
		}
		if (BaseWordReader.isBlankChar(mCurChar)) {
			// remove the blank char.
			do {
				builder.append(mCurChar);
			} while (BaseWordReader.isBlankChar((mCurChar = (char) reader.read())));
			if (mCurChar == BaseWordReader.DOC_END_MARK) {
				word = new Word();
				word.blankStr = builder.toString();
				return word;
			}
		}
		if (mCurChar >= '0' && mCurChar <= '9') {
			word = mNumberReader.read(reader, mCurChar);
			mCurChar = mNumberReader.getLastChar();
		} else if ((mCurChar >= 'a' && mCurChar <= 'z')
				|| (mCurChar >= 'A' && mCurChar <= 'Z') || (mCurChar == '_')
				|| (mCurChar == '$')) {
			word = mStringReader.read(reader, mCurChar);
			mCurChar = mStringReader.getLastChar();
		} else if (mCurChar == '\'' || mCurChar == '\"') {
			word = mStringValueReader.read(reader, mCurChar);
			mCurChar = mStringValueReader.getLastChar();
		} else if (mCurChar == '@') {
			word = new Word();
			word.mark = Type.NORMAL;
			word.value = "@";
			word.type = WordType.ANNOTATION;
			mCurChar = (char) reader.read();
		} else {
			word = mSignReader.read(reader, mCurChar);
			mCurChar = mSignReader.getLastChar();
		}
		word.blankStr = builder.toString();
		// TODO
		if (word.type == WordType.COMMEND_STRING) {
			builder.setLength(0);
			builder.append(word);
			word = getWord(reader);
			if (word == null) {
				throw new RuntimeException("Word is null.");
			}
			builder.append(word.blankStr);
			word.blankStr = builder.toString();
//			Log.d(TAG, word.toString());
		} else {
//			Log.v(TAG, word.toString());// + "(" + word.type + ")"
		}
		return word;
	}

	public JavaFileObject analyzeJavaFileObject(IReader reader) throws IOException {
		PackageStatementParser pkgParser = new PackageStatementParser();
		ImportStatementParser importParser = new ImportStatementParser();

		JavaFileObject javaFileObject = new JavaFileObject();
		EnvironmentArgs args = new EnvironmentArgs();
		args.setSourceFileType(EnvironmentArgs.SOURCE_TYPE_JAVA);
		// Attach Environment arguments.
		javaFileObject.attachEnvironmentArgs(args);
		Word word = getWord(reader);
		
		PackageStatement packageStatement;
		List<ImportStatement> importStatements = new LinkedList<ImportStatement>();
		// parse package
		packageStatement = pkgParser.parse(reader, this, word);
		if (packageStatement != null) {
			word = pkgParser.getLastWord();
		} else {
			throw new RuntimeException("analyzeTypeDefineCodeBlock error when parser 'package'.(Current word is : " + word + ")");
		}
		javaFileObject.setPackageStatement(packageStatement);
		// parse import
		do {
			ImportStatement importStm = importParser.parse(reader, this, word);
			word = importParser.getLastWord();
			if (importStm != null) {
				importStatements.add(importStm);
				javaFileObject.addImport(importStm);
				continue;
			}
			break;
		} while (true);
		// parse type define code block
		TypeDefineStatementParser parser = ParserFactory.getTypeDefineStatementParser();
		try {
			while (word != null && word.type != WordType.DOC_END) {
				TypeDefineCodeBlock typeDefineStm = parser.parse(reader, this, word);
				javaFileObject.addTypeDefineCodeBlock(typeDefineStm);
				word = parser.getLastWord();
//				Log.i(TAG, "current word (" + word.value + ", " + (word.type == WordType.DOC_END) +", " + word.type + ").");
			}
		} catch (DocEndException e) {
			Log.i(TAG, "on doc end. %s", Log.getStackTraceString(e));
		}
		return javaFileObject;
	}

}
