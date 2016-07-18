package osc.innovator.arbitrarygen.statement;

import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.base.Expression;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.model.TypeName;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DefinitionStatement extends BaseStatement {

	private TypeName mTypeName;
	private boolean mIsFinal;
	private Expression mExpression;
	private boolean mSemicolon;
	private Word wordFinal;
	
	public DefinitionStatement(TypeName typeName, boolean isFinal, Expression expression) {
		mTypeName = typeName;
		mIsFinal = isFinal;
		mExpression = expression;
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);

		builder.append(genCommendBlock(linefeed));
		if (mIsFinal) {
			builder.append(Util.getFinal(this));
			builder.append(blank);
		}
		builder.append(mTypeName.genCode(""));
		builder.append(blank);
		builder.append(mExpression.genCode(linefeed));
		if (mSemicolon || mSuffixWord != null) {
			builder.append(Util.getSuffix(this, ";"));
		}
		return builder.toString();
	}

	public boolean isSemicolon() {
		return mSemicolon;
	}

	public void setSemicolon(boolean semicolon) {
		this.mSemicolon = semicolon;
	}

	public Word getWordFinal() {
		return wordFinal;
	}

	public void setWordFinal(Word wordFinal) {
		this.wordFinal = wordFinal;
		if (wordFinal != null) {
			mIsFinal = true;
		} else {
			mIsFinal = false;
		}
	}

}
