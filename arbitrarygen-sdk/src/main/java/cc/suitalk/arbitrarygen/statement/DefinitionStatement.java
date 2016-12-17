package cc.suitalk.arbitrarygen.statement;

import net.sf.json.JSONObject;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.model.TypeName;
import cc.suitalk.arbitrarygen.utils.Util;

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

	@Override
	public JSONObject toJSONObject() {
		JSONObject o = super.toJSONObject();
		o.put("_type", "definition");
		o.put("_name", mTypeName.toJSONObject());
		o.put("_expression", mExpression.toString());
		return o;
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
