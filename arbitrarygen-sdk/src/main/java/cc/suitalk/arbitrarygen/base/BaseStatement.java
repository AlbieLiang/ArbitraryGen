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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Environment.EnvironmentArgs;
import cc.suitalk.arbitrarygen.statement.AnnotationStatement;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BaseStatement extends Session implements ICodeGenerator, JSONConverter {

	private String mCommendBlock;
	private List<AnnotationStatement> mAnnotationStatements;
	protected PlainCodeBlock mCodeBlock;
	protected Word mPrefixWord;
	protected Word mSuffixWord;
	protected Word mWordLeftBracket;
	protected Word mWordRightBracket;
	private EnvironmentArgs mEnvironmentArgs;
	private BaseStatement mOuterStatement;
	private BaseStatement mBelongStatement;
	
	public BaseStatement() {
		mAnnotationStatements = new LinkedList<>();
	}

	public void attachEnvironmentArgs(EnvironmentArgs args) {
		mEnvironmentArgs = args;
		if (mCodeBlock != null) {
			mCodeBlock.attachEnvironmentArgs(args);
		}
		AnnotationStatement stm = null;
		for (int i = 0; i < mAnnotationStatements.size(); i++) {
			stm = mAnnotationStatements.get(i);
			stm.attachEnvironmentArgs(args);
		}
		onAttachEnvironmentArgs(args);
	}
	
	public EnvironmentArgs getEnvironmentArgs() {
		return mEnvironmentArgs;
	}
	
	/**
	 * Override the method when the statement has some sub statement.
	 * 
	 * @param args the {@link EnvironmentArgs} of this statement.
	 */
	public void onAttachEnvironmentArgs(EnvironmentArgs args) {
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		JSONArray annArray = new JSONArray();
		for (int i = 0; i < mAnnotationStatements.size(); i++) {
			AnnotationStatement astm = mAnnotationStatements.get(i);
			annArray.add(astm.toJSONObject());
		}
		if (!annArray.isEmpty()) {
			jsonObject.put("_annotation", annArray);
		}
		if (mCodeBlock != null) {
			jsonObject.put("codeBlock", mCodeBlock.toJSONObject());
		}
		return jsonObject;
	}

	public BaseStatement getOuterStatement() {
		return mOuterStatement;
	}

	public void setOuterStatement(BaseStatement stm) {
		this.mOuterStatement = stm;
	}

	public BaseStatement getBelongStatement() {
		return mBelongStatement;
	}

	public void setBelongStatement(BaseStatement stm) {
		this.mBelongStatement = stm;
	}

	public boolean isJavaFile() {
		return mEnvironmentArgs == null ? false : mEnvironmentArgs.isJavaFile();
	}
	
	public boolean isForceLinefeed() {
		return mEnvironmentArgs != null && mEnvironmentArgs.checkFlag(EnvironmentArgs.MODE_FORCE_LINE_FEED);
	}
	
	public String getLinefeed(String linefeed) {
		return isJavaFile() ? "" : linefeed;
	}
	
	public String getWordBlank(String blank) {
		return isJavaFile() ? "" : blank;
	}
	
	protected String genCommendBlock(String linefeed) {
		// in advance
		StringBuilder builder = new StringBuilder();
		if (isForceLinefeed()) {
			builder.append(linefeed);
		}
		if (mCommendBlock != null && !mCommendBlock.equals("")) {
			builder.append(mCommendBlock);
			builder.append(getLinefeed(linefeed));
		}
		return builder.toString();
	}

	protected String genAnnotationBlock(String linefeed) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for (; i < mAnnotationStatements.size(); i++) {
			AnnotationStatement s = mAnnotationStatements.get(i);
			builder.append(getLinefeed(linefeed));
			builder.append(s.genCode(linefeed));
		}
		if (i > 0) {
			builder.append(getLinefeed(linefeed));
		}
		return builder.toString();
	}

	protected String genPlainCodeBlock(String linefeed) {
		if (mCodeBlock != null) {
			return mCodeBlock.genCode(linefeed);
		}
		return "";
	}

	public PlainCodeBlock getCodeBlock() {
		return mCodeBlock;
	}

	public void setCodeBlock(PlainCodeBlock codeBlock) {
		this.mCodeBlock = codeBlock;
		if (mCodeBlock != null) {
			mCodeBlock.setBelongStatement(this);
		}
	}

	public String getCommendBlock() {
		return mCommendBlock;
	}

	public void setCommendBlock(String commendBlock) {
		this.mCommendBlock = commendBlock;
	}

	public boolean addStatement(BaseStatement s) {
		return mCodeBlock.addStatement(s);
	}

	public boolean addAnnotation(AnnotationStatement s) {
		if (s == null || mAnnotationStatements.contains(s)) {
			return false;
		}
		s.attachEnvironmentArgs(mEnvironmentArgs);
		s.setBelongStatement(this);
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

	public boolean containsAnnotation(String name) {
		return getAnnotation(name) != null;
	}

	public int countOfAnnotations() {
		return mAnnotationStatements.size();
	}
	
	public Word getPrefixWord() {
		return mPrefixWord;
	}

	public void setPrefixWord(Word prefixWord) {
		this.mPrefixWord = prefixWord;
	}
	
	public Word getSuffixWord() {
		return mSuffixWord;
	}

	public void setSuffixWord(Word suffixWord) {
		this.mSuffixWord = suffixWord;
	}
	
	public Word getWordLeftBracket() {
		return mWordLeftBracket;
	}
	
	public void setWordLeftBracket(Word word) {
		mWordLeftBracket = word;
	}

	public Word getWordRightBracket() {
		return mWordRightBracket;
	}
	
	public void setWordRightBracket(Word word) {
		mWordRightBracket = word;
	}
}
