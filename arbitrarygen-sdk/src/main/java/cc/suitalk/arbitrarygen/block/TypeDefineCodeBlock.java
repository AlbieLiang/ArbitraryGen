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

package cc.suitalk.arbitrarygen.block;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.suitalk.arbitrarygen.base.BaseDefineCodeBlock;
import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.core.KeyWords;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.model.TypeName;
import cc.suitalk.arbitrarygen.statement.AnnotationStatement;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Template for the Code Generator. 
 * 
 * TODO deal enum type
 * 
 * @author AlbieLiang
 * 
 */
public class TypeDefineCodeBlock extends BaseDefineCodeBlock {

	protected TypeName mParent;
	protected List<TypeName> mInterfaces;
	protected List<FieldCodeBlock> mFields;
	protected List<TypeDefineCodeBlock> mSubTypeDefineCodeBlocks;
	protected List<MethodCodeBlock> mMethods;
	/**
	 * 
	 */
	private Map<String, FieldCodeBlock> mFieldIndexs;

	private Word mWordExtends;
	private Word mWordImplements;
	
	public TypeDefineCodeBlock() {
		mInterfaces = new LinkedList<>();
		mFields = new LinkedList<>();
		mSubTypeDefineCodeBlocks = new LinkedList<>();
		mMethods = new LinkedList<>();
		mFieldIndexs = new HashMap<>();
		mWordExtends = Util.createKeyWord("extends");
		mWordImplements = Util.createKeyWord("implements");
		setCodeBlock(new PlainCodeBlock());
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		if (builder.length() == 0) {
			// Insert class or interface or enum definition
//			builder.append(getLinefeed(linefeed));
			String typedef = genTypedef(this, linefeed);
			if (Util.isNullOrNil(typedef)) {
				throw new RuntimeException("Gen Code Typedef Fail.");
			}
			builder.append(typedef);
			builder.append(getWordBlank(BLANK_1));
			if (mCodeBlock != null) {
				builder.append(mCodeBlock.genCode(linefeed));
			}
			// end of code
		}
		return builder.toString();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		if (mParent != null) {
			jsonObject.put("_parent", mParent.toJSONObject());
		}
		JSONArray interfaceList = new JSONArray();
		for (int i = 0; i < mInterfaces.size(); i++) {
			TypeName _interface = mInterfaces.get(i);
			interfaceList.add(_interface.toJSONObject());
		}
		if (!interfaceList.isEmpty()) {
			jsonObject.put("_interface", interfaceList);
		}
		JSONArray fieldList = new JSONArray();
		for (int i = 0; i < mFields.size(); i++) {
			FieldCodeBlock field = mFields.get(i);
			fieldList.add(field.toJSONObject());
		}
		jsonObject.put("_modifier", getModifier());
		jsonObject.put("_static", isIsStatic());
		jsonObject.put("_final", isIsFinal());
		jsonObject.put("_abstract", isIsAbstract());
		jsonObject.put("_synchronized", isIsSynchronized());
		jsonObject.put("_type", getType().getName());
		jsonObject.put("_name", getName().getName());
		JSONObject annJsonObj = new JSONObject();
		for (int i = 0; i < countOfAnnotations(); i++) {
			AnnotationStatement astm = getAnnotation(i);
			annJsonObj.put(astm.getName().getName(), astm.toJSONObject());
		}
		if (!annJsonObj.isEmpty()) {
			jsonObject.put("_annotation", annJsonObj);
		}
		if (!fieldList.isEmpty()) {
			jsonObject.put("field", fieldList);
		}
		JSONArray methodList = new JSONArray();
		for (int i = 0; i < mMethods.size(); i++) {
			MethodCodeBlock method = mMethods.get(i);
			methodList.add(method.toJSONObject());
		}
		if (!methodList.isEmpty()) {
			jsonObject.put("method", methodList);
		}
		JSONArray typeDefineList = new JSONArray();
		for (int i = 0; i < mSubTypeDefineCodeBlocks.size(); i++) {
			TypeDefineCodeBlock typeDefine = mSubTypeDefineCodeBlocks.get(i);
			typeDefineList.add(typeDefine.toJSONObject());
		}
		if (!typeDefineList.isEmpty()) {
			jsonObject.put("subClass", typeDefineList);
		}
		return jsonObject;
	}
	
	protected String genTypedef(TypeDefineCodeBlock typeDefCodeBlock, String linefeed) {
		StringBuilder builder = new StringBuilder();
		// sample : public synchronized static final class a (extends b implements c)
		builder.append(genDefCode(typeDefCodeBlock, linefeed));
		// Insert extends
		String blank = getWordBlank(BLANK_1);
		if (typeDefCodeBlock.mParent != null) {
			builder.append(blank);
			builder.append(mWordExtends != null ? mWordExtends : KeyWords.V_JAVA_KEYWORDS_EXTENDS);
			builder.append(blank);
			builder.append(typeDefCodeBlock.mParent);
		}
		// Insert implements
		if (typeDefCodeBlock.mInterfaces != null && typeDefCodeBlock.mInterfaces.size() > 0) {
			builder.append(blank);
			builder.append(mWordImplements != null ? mWordImplements : KeyWords.V_JAVA_KEYWORDS_IMPLEMENTS);
			int i = 0;
			for (; i < typeDefCodeBlock.mInterfaces.size() - 1; i++) {
				builder.append(blank);
				builder.append(typeDefCodeBlock.mInterfaces.get(i).genCode(""));
				builder.append(KeyWords.V_JAVA_KEYWORDS_SIGN_COMMA);
			}
			builder.append(blank);
			builder.append(typeDefCodeBlock.mInterfaces.get(i));
		}
		return builder.toString();
	}

	public void setWordExtends(Word word) {
		if (word != null && !"extends".equals(word.value)) {
			return;
		}
		mWordExtends = word;
	}
	
	public void setWordImplements(Word word) {
		if (word != null && !"implements".equals(word.value)) {
			return;
		}
		mWordImplements = word;
	}
	
	public void setParent(TypeName parent) {
		this.mParent = parent;
	}
	
	public TypeName getParent() {
		return this.mParent;
	}
	
	public void addInterface(TypeName Interface) {
		this.mInterfaces.remove(Interface);
		this.mInterfaces.add(Interface);
	}

	public void addInterfaces(List<TypeName> Interfaces) {
		this.mInterfaces.addAll(Interfaces);
	}
	
	public void removeInterface(TypeName Interface) {
		this.mInterfaces.remove(Interface);
	}

	public int countOfInterfaces() {
		return mInterfaces.size();
	}

	public List<TypeName> getInterfaceList() {
		return mInterfaces;
	}
	
	public void addField(FieldCodeBlock field) {
		if (field == null) {
			return;
		}
		FieldCodeBlock t = null;
		if (!Util.isNullOrNil(field.getId())) {
			t = mFieldIndexs.get(field.getId());
		} else {
			field.setId(field.getName().getName());
		}
		if (t != null) {
			this.mFields.remove(t);
			mCodeBlock.removeStatement(t);
		} else {
			this.mFields.remove(field);
			mCodeBlock.removeStatement(field);
		}
		// TODO 
//		if (t.genGetter) {
//			Methods.add(new GetterMethodCodeBlock(t));
//		}
//		if (t.genSetter) {
//			Methods.add(new SetterMethodCodeBlock(t));
//		}
		this.mFields.add(field);
		mCodeBlock.addStatement(field);
		mFieldIndexs.put(field.getId(), field);
	}

	public void removeField(FieldCodeBlock field) {
		if (field == null) {
			return;
		}
		FieldCodeBlock t = mFieldIndexs.get(field.getId());
		if (t == null) {
			return;
		}
		this.mFields.remove(t);
		mCodeBlock.removeStatement(t);
		mFieldIndexs.remove(t);
	}

	public int countOfFields() {
		return mFields.size();
	}

	public FieldCodeBlock getField(int index) {
		if (index >= 0 && index < mFields.size()) {
			return mFields.get(index);
		}
		return null;
	}

	public boolean addMethod(MethodCodeBlock method) {
		if (mMethods.contains(method)) {
			return false;
		}
		mCodeBlock.addStatement(method);
		return mMethods.add(method);
	}
	
	public boolean removeMethod(MethodCodeBlock method) {
		boolean result = false;
		if (method != null) {
			result = mMethods.remove(method);
		}
		if (result) {
			mCodeBlock.removeStatement(method);
		}
		return result;
	}
	
	public MethodCodeBlock removeMethod(int index) {
		MethodCodeBlock m = null;
		if (index >= 0 && index < mMethods.size()) {
			m = mMethods.remove(index);
			if (m != null) {
				mCodeBlock.removeStatement(m);
			}
		}
		return m;
	}

	public MethodCodeBlock getMethod(int index) {
		if (index >= 0 && index < mMethods.size()) {
			return mMethods.get(index);
		}
		return null;
	}
	
	public int countOfMethods() {
		return mMethods.size();
	}
	
	public boolean addTypeDefineCodeBlock(TypeDefineCodeBlock typeDefCodeBlock) {
		if (typeDefCodeBlock == null) {
			return false;
		}
		mCodeBlock.addStatement(typeDefCodeBlock);
		return mSubTypeDefineCodeBlocks.add(typeDefCodeBlock);
	}

	public int countOfTypeDefCodeBlocks() {
		return mSubTypeDefineCodeBlocks.size();
	}
	
	public TypeDefineCodeBlock getTypeDefCodeBlock(int index) {
		return mSubTypeDefineCodeBlocks.get(index);
	}

	@Override
	public boolean addStatement(BaseStatement s) {
		boolean r = super.addStatement(s);
		if (s instanceof TypeDefineCodeBlock) {
			addTypeDefineCodeBlock((TypeDefineCodeBlock) s);
		}
		return r;
	}
}
