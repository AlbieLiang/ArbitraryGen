package cc.suitalk.arbitrarygen.block;

import cc.suitalk.arbitrarygen.base.BaseDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.KeyWords;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class FieldCodeBlock extends BaseDefineCodeBlock {

	private String Default;
	private boolean genGetter;
	private boolean genSetter;
	private Word mWordAssignment;

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
//		builder.append(genCommendBlock(linefeed));
//		builder.append(genAnnotationBlock(linefeed));
		builder.append(genDefCode(this, linefeed));
		String blank = getWordBlank(BLANK_1);
		if (!Util.isNullOrNil(Default)) {
			builder.append(blank);
			builder.append(mWordAssignment != null ? mWordAssignment : KeyWords.V_JAVA_KEYWORDS_SIGN_ASSIGNMENT);
			builder.append(blank);
			builder.append(Default);
		}
		builder.append(Util.getSuffix(this, KeyWords.V_JAVA_KEYWORDS_SIGN_SEMICOLON));
		return builder.toString();
	}

	public String getDefault() {
		return Default;
	}

	public void setDefault(String default1) {
		Default = default1;
	}

	public boolean isGenGetter() {
		return genGetter;
	}

	public void setGenGetter(boolean genGetter) {
		this.genGetter = genGetter;
	}

	public boolean isGenSetter() {
		return genSetter;
	}

	public void setGenSetter(boolean genSetter) {
		this.genSetter = genSetter;
	}

	public Word getWordAssignment() {
		return mWordAssignment;
	}

	public void setWordAssignment(Word wordAssignment) {
		this.mWordAssignment = wordAssignment;
	}
}