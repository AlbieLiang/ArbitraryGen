package cc.suitalk.arbitrarygen.block;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.suitalk.arbitrarygen.base.BaseDefineCodeBlock;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.core.KeyWords;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.model.TypeName;
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
		mInterfaces = new LinkedList<TypeName>();
		mFields = new LinkedList<FieldCodeBlock>();
		mSubTypeDefineCodeBlocks = new LinkedList<TypeDefineCodeBlock>();
		mMethods = new LinkedList<MethodCodeBlock>();
		mFieldIndexs = new HashMap<String, FieldCodeBlock>();
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
}
