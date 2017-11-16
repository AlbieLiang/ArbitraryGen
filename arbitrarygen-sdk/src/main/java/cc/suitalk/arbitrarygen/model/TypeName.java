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
import cc.suitalk.arbitrarygen.statement.AnnotationStatement;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TypeName implements ICodeGenerator, JSONConverter {

	private static final String TAG = "AG.TypeName";

	private Word mWordFinal;
	private ReferenceExpression mName;
	private List<TypeName> mGenericityTypes;
	private List<Expression> mArrayArgs;

	private List<Word> mLeftSquareBrackets;
	private List<Word> mRightSquareBrackets;

	private List<AnnotationStatement> mAnnotationStatements;

	public TypeName() {
		mGenericityTypes = new LinkedList<>();
		mArrayArgs = new LinkedList<>();
		mLeftSquareBrackets = new LinkedList<>();
		mRightSquareBrackets = new LinkedList<>();
		mAnnotationStatements = new LinkedList<>();
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		builder.append(genAnnotationBlock(linefeed));
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
		JSONObject annJSONObj = new JSONObject();
		for (int i = 0; i < mAnnotationStatements.size(); i++) {
			AnnotationStatement astm = mAnnotationStatements.get(i);
			annJSONObj.put(astm.getName().getName(), astm.toJSONObject());
		}
		if (!annJSONObj.isEmpty()) {
			jsonObject.put("_annotation", annJSONObj);
		}
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

	public boolean addAnnotation(AnnotationStatement s) {
		if (s == null || mAnnotationStatements.contains(s)) {
			return false;
		}
//		s.attachEnvironmentArgs(mEnvironmentArgs);
//		s.setBelongStatement(this);
		Log.i(TAG, "addAnnotation(%s)", s);
		return mAnnotationStatements.add(s);
	}

	public void addAnnotations(List<AnnotationStatement> annoStms) {
		if (annoStms == null || annoStms.size() == 0) {
			return;
		}
		for (AnnotationStatement as : annoStms) {
			addAnnotation(as);
		}
	}

	public AnnotationStatement removeAnnotation(int index) {
		if (index >= 0 && index < mAnnotationStatements.size()) {
			return mAnnotationStatements.remove(index);
		}
		return null;
	}

	public boolean removeAnnotation(AnnotationStatement stm) {
		if (stm != null) {
			return mAnnotationStatements.remove(stm);
		}
		return false;
	}

	public AnnotationStatement getAnnotation(int index) {
		if (index >= 0 && index < mAnnotationStatements.size()) {
			return mAnnotationStatements.get(index);
		}
		return null;
	}

	public AnnotationStatement getAnnotation(String name) {
		if (!Util.isNullOrNil(name)) {
			for (int i = 0; i < mAnnotationStatements.size(); i++) {
				AnnotationStatement as = mAnnotationStatements.get(i);
				String aname = as.getName().getName();
				if (aname.equals(name) || (aname.length() > name.length() && aname.endsWith(name)
						&& aname.charAt(aname.length() - name.length() - 1) == '.')) {
					return as;
				}
			}
		}
		return null;
	}

	protected String genAnnotationBlock(String linefeed) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for (; i < mAnnotationStatements.size(); i++) {
			AnnotationStatement s = mAnnotationStatements.get(i);
			builder.append(" ");
			builder.append(s.genCode(linefeed));
		}
		if (i > 0) {
			builder.append(" ");
		}
		return builder.toString();
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
			TypeName typeName = new TypeName();
			Word finalWord = null;
			List<AnnotationStatement> annoStms = Util.parseAndAddAnnotation(reader, lexer, curWord, this);
			curWord = getLastWord();
			if (annoStms != null && annoStms.size() > 0) {
				typeName.addAnnotations(annoStms);
			}
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
