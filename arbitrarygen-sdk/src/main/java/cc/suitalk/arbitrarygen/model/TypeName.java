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

package cc.suitalk.arbitrarygen.model;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseCodeParser;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.base.ICodeGenerator;
import cc.suitalk.arbitrarygen.base.JSONConverter;
import cc.suitalk.arbitrarygen.core.KeyWords.Sign.Type;
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.expression.ReferenceExpression;
import cc.suitalk.arbitrarygen.expression.VariableExpression;
import cc.suitalk.arbitrarygen.expression.parser.ReferenceExpressionParser;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TypeName implements ICodeGenerator, JSONConverter {

	private Word mWordFinal;
	private ReferenceExpression mName;
	private List<TypeName> mGenericityTypes;
	private List<Expression> mArrayArgs;

	private List<Word> mLeftSquareBrackets;
	private List<Word> mRightSquareBrackets;

	public TypeName() {
		mGenericityTypes = new LinkedList<>();
		mArrayArgs = new LinkedList<>();
		mLeftSquareBrackets = new LinkedList<>();
		mRightSquareBrackets = new LinkedList<>();
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
//		builder.append(genAnnotationBlock(linefeed));
		if (mWordFinal != null) {
			builder.append(mWordFinal);
		}
		builder.append(mName.genCode(linefeed));
		if (mGenericityTypes.size() > 0) {
			builder.append("<");
			builder.append(mGenericityTypes.get(0).genCode(linefeed));
			for (int i = 1; i < mGenericityTypes.size(); i++) {
				builder.append(", ");
				builder.append(mGenericityTypes.get(i).genCode(linefeed));
			}
			builder.append(">");
		}
		for (int i = 0; i < mArrayArgs.size(); i++) {
			builder.append(mLeftSquareBrackets.get(i));
			Expression expression = mArrayArgs.get(i);
			if (expression != null) {
				builder.append(expression.genCode(""));
			}
			builder.append(mRightSquareBrackets.get(i));
		}
		return builder.toString();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("isFinal", mWordFinal != null);
		jsonObject.put("name", mName.getVariable());
		JSONArray genericityTypeArray = new JSONArray();
		for (int i = 0; i < mGenericityTypes.size(); i++) {
			TypeName name = mGenericityTypes.get(i);
			genericityTypeArray.add(name.getName());
		}
		if (!genericityTypeArray.isEmpty()) {
			jsonObject.put("genericityType", genericityTypeArray);
		}
		JSONArray arrayArgsArray = new JSONArray();
		for (int i = 0; i < mGenericityTypes.size(); i++) {
			TypeName name = mGenericityTypes.get(i);
			genericityTypeArray.add(name.getName());
		}
		if (!arrayArgsArray.isEmpty()) {
			jsonObject.put("arrayArgs", arrayArgsArray);
		}
		return jsonObject;
	}

	@Override
	public String toString() {
		return genCode("");
	}

	public ReferenceExpression getNameRefExpression() {
		return mName;
	}

	public Word getFinal() {
		return mWordFinal;
	}

	public void setFinal(Word word) {
		mWordFinal = word;
	}

	public String getName() {
		return mName.getVariable();
	}

	public void setName(ReferenceExpression name) {
		mName = name;
	}

	public List<TypeName> getGenericityType() {
		return mGenericityTypes;
	}

	public void addGenericityType(TypeName genericityType) {
		mGenericityTypes.add(genericityType);
	}

	public List<Expression> getArrayArgs() {
		return mArrayArgs;
	}
	
	public void addArrayArg(Word leftSquareBracket, Expression e, Word rightSquareBracket) {
		mLeftSquareBrackets.add(leftSquareBracket == null ? Util.createSignWord("[", Type.BEGIN) : leftSquareBracket);
		mArrayArgs.add(e);
		mRightSquareBrackets.add(rightSquareBracket == null ? Util.createSignWord("]", Type.END) : rightSquareBracket);
	}

	/**
	 * 
	 * @author AlbieLiang
	 *
	 */
	public static final class Parser extends BaseCodeParser {

		private static final String TAG = "TypeName.Parser";

		@Override
		public TypeName parse(IReader reader, Lexer lexer, Word curWord) throws IOException {
			Word finalWord = null;
			if (curWord != null && "final".equals(curWord.value)) {
				finalWord = curWord;
				curWord = nextWord(reader, lexer);
			}
			ReferenceExpressionParser parser = ParserFactory.getRefExpressionParser(true);
			ReferenceExpression refExpr = parser.parse(reader, lexer, curWord);
			if (refExpr == null) {
				Log.i(TAG, "Parse refExpr is null.");
				return null;
			}
			setLastWord(parser.getLastWord());
			Word word = getLastWord();
			TypeName typeName = new TypeName();
			typeName.setName(refExpr);
			typeName.setFinal(finalWord);
			if (word.value.equals("<")) {
				word = nextWord(reader, lexer);
				if ("?".equals(word.value)) {
					word = nextWord(reader, lexer);
					if (!">".equals(word.value)) {
						throw new RuntimeException("missing '>' sign.");
					}
					TypeName gt = new TypeName();
					gt.setName(new VariableExpression("?"));
					typeName.addGenericityType(gt);
					nextWord(reader, lexer);
				} else {
					TypeName.Parser p = new TypeName.Parser();
					do {
						TypeName tn = p.parse(reader, lexer, word);
						typeName.addGenericityType(tn);
						setLastWord(p.getLastWord());
						word = getLastWord();
						if (">".equals(word.value)) {
							word = nextWord(reader, lexer);
							break;
						} else if (",".equals(word.value)) {
							word = nextWord(reader, lexer);
							continue;
						} else {
							throw new RuntimeException("Parse TypeName error.(last word : " + word.value + ").");
						}
					} while (word.type != WordType.DOC_END);
				}
			}
			while ("[".equals(word.value)) {
				Expression e = Util.extractExpression(reader, lexer, nextWord(reader, lexer), this, "]");
				typeName.addArrayArg(word, e, getLastWord());
				word = nextWord(reader, lexer);
			}
			return typeName;
		}
		
	}
}
