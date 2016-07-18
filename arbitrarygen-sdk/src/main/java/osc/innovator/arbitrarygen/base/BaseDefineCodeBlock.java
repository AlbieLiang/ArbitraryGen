package osc.innovator.arbitrarygen.base;

import java.util.LinkedList;
import java.util.List;

import osc.innovator.arbitrarygen.core.KeyWords;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.model.TypeName;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BaseDefineCodeBlock extends BaseStatement implements ICodeGenerator {

	private String mId;
	/**
	 * It can be {@link KeyWords#V_JAVA_KEYWORDS_PUBLIC},
	 * {@link KeyWords#V_JAVA_KEYWORDS_PRIVATE} or
	 * {@link KeyWords#V_JAVA_KEYWORDS_PROTECTED}
	 */
	private String mModifier;
	private boolean mIsStatic;
	private boolean mIsFinal;
	private boolean mIsAbstract;
	private boolean mIsSynchronized;
	/**
	 * class , interface or enum
	 */
	private TypeName mType;
	private TypeName mName;
	private List<Word> mModifierWords;

	public BaseDefineCodeBlock() {
		mModifierWords = new LinkedList<Word>();
	}
	
	protected String genDefCode(BaseDefineCodeBlock defCodeBlock, String linefeed) {
		// sample : public synchronized static final String name suffix(such as
		// '=
		// ""' or '(){}');
		StringBuilder builder = new StringBuilder();
		boolean needToInsertBlank = false;
		String insertSymbol = "";
		builder.append(genCommendBlock(linefeed));
		builder.append(genAnnotationBlock(linefeed));
		if (isJavaFile()) {
			int i = 0;
			for (; i < mModifierWords.size(); i++) {
				builder.append(mModifierWords.get(i));
			}
			needToInsertBlank = i > 0;
		} else {
			insertSymbol = Util.getStrWithDef(defCodeBlock.mModifier, KeyWords.V_JAVA_KEYWORDS_PUBLIC);
			if (!Util.isNullOrNil(insertSymbol)) {
				needToInsertBlank = true;
				builder.append(insertSymbol);
			}
			insertSymbol = LexicalHelper.getSymbolSync(defCodeBlock);
			if (!Util.isNullOrNil(insertSymbol)) {
				if (needToInsertBlank) {
					builder.append(BLANK_1);
				}
				needToInsertBlank = true;
				builder.append(insertSymbol);
			}
			insertSymbol = LexicalHelper.getSymbolStatic(defCodeBlock);
			if (!Util.isNullOrNil(insertSymbol)) {
				if (needToInsertBlank) {
					builder.append(BLANK_1);
				}
				needToInsertBlank = true;
				builder.append(insertSymbol);
			}
			insertSymbol = LexicalHelper.getSymbolFinal(defCodeBlock);
			if (!Util.isNullOrNil(insertSymbol)) {
				if (needToInsertBlank) {
					builder.append(BLANK_1);
				}
				needToInsertBlank = true;
				builder.append(insertSymbol);
			}
		}
		insertSymbol = defCodeBlock.mType == null ? null : defCodeBlock.mType.genCode(linefeed);
		if (!Util.isNullOrNil(insertSymbol)) {
			if (needToInsertBlank && !isJavaFile()) {
				builder.append(BLANK_1);
			}
			needToInsertBlank = true;
			builder.append(insertSymbol);
		}
		insertSymbol = defCodeBlock.mName == null ? null : defCodeBlock.mName.genCode(linefeed);
		if (!Util.isNullOrNil(insertSymbol)) {
			if (needToInsertBlank && !isJavaFile()) {
				builder.append(BLANK_1);
			}
			needToInsertBlank = true;
			builder.append(insertSymbol);
		}
		return builder.toString();
	}
	
	private boolean addModifier(Word word) {
		return mModifierWords.add(word);
	}
	
	public boolean addModifierWord(Word word) {
		if (word == null || Util.isNullOrNil(word.value)) {
			return false;
		}
		String value = word.value;
		if ("public".equals(value) || "private".equals(value) || "protected".equals(value)) {
			if (!Util.isNullOrNil(mModifier)) {
				removeModifierWord(mModifier);
			}
			mModifier = value;
			return addModifier(word);
		} else if ("static".equals(value)) {
			if (mIsStatic) {
				removeModifierWord("static");
			}
			mIsStatic = true;
			return addModifier(word);
		} else if ("final".equals(value)) {
			if (mIsFinal) {
				removeModifierWord("final");
			}
			mIsFinal = true;
			return addModifier(word);
		} else if ("abstract".equals(value)) {
			if (mIsAbstract) {
				removeModifierWord("abstract");
			}
			mIsAbstract = true;
			return addModifier(word);
		} else if ("synchronized".equals(value)) {
			if (mIsSynchronized) {
				removeModifierWord("synchronized");
			}
			mIsSynchronized = true;
			return addModifier(word);
		} else if ("class".equals(value) || "interface".equals(value) || "enum".equals(value) || "@interface".equals(value)) {
			return addModifier(word);
//		} else if ("".equals(value)) {
//			
//			return addModifier(word);
		}
		return false;
	}
	
	public boolean removeModifierWord(String wordStr) {
		if (Util.isNullOrNil(wordStr)) {
			return false;
		}
		for (int i = 0; i < mModifierWords.size(); i++) {
			Word word = mModifierWords.get(i);
			if (wordStr.equals(word.value)) {
				return mModifierWords.remove(i) != null;
			}
		}
		return false;
	}
	
	public boolean updateModifierWord(Word word) {
		return false;
	}
	
	public String getId() {
		return mId;
	}
	
	public void setId(String id) {
		mId = id;
	}

	public String getModifier() {
		return mModifier;
	}

	public void setModifier(String modifier) {
//		boolean needAdd = false;
//		if (!Util.isNullOrNil(mModifier)) {
//			if (!mModifier.equals(modifier)) {
//				removeModifierWord(mModifier);
//				needAdd = true;
//			}
//		} else if (!Util.isNullOrNil(modifier)) {
//			needAdd = true;
//		}
//		if (needAdd) {
			mModifier = modifier;
//			Word word = new Word();
//			word.value = modifier;
//			word.type = WordType.STRING;
//			word.blankStr = "";
//			addModifier(word);
//		}
	}

	public TypeName getType() {
		return mType;
	}

	public void setType(TypeName type) {
		mType = type;
	}

	public TypeName getName() {
		return mName;
	}

	public void setName(TypeName name) {
		mName = name;
	}

	public boolean isIsStatic() {
		return mIsStatic;
	}

	public void setIsStatic(boolean isStatic) {
		mIsStatic = isStatic;
	}

	public boolean isIsFinal() {
		return mIsFinal;
	}

	public void setIsFinal(boolean isFinal) {
		mIsFinal = isFinal;
	}

	public boolean isIsAbstract() {
		return mIsAbstract;
	}

	public void setIsAbstract(boolean isAbstract) {
		mIsAbstract = isAbstract;
	}

	public boolean isIsSynchronized() {
		return mIsSynchronized;
	}

	public void setIsSynchronized(boolean isSynchronized) {
		mIsSynchronized = isSynchronized;
	}

	// TODO :
	public static class LexicalHelper {
		public static String getSymbolSync(BaseDefineCodeBlock t) {
			return t.mIsSynchronized ? "synchronized" : "";
		}

		public static String getSymbolStatic(BaseDefineCodeBlock t) {
			return t.mIsStatic ? KeyWords.V_JAVA_KEYWORDS_STATIC : "";
		}

		public static String getSymbolFinal(BaseDefineCodeBlock t) {
			return t.mIsFinal ? KeyWords.V_JAVA_KEYWORDS_FINAL : "";
		}
	}
}