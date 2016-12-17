package cc.suitalk.arbitrarygen.statement;

import net.sf.json.JSONObject;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ElseIfStatement extends BaseStatement {

	private Expression mExpression;
	private Word mWordElse;
	private Word mWordIf;
	
	public ElseIfStatement() {
		this(null);
	}

	public ElseIfStatement(Expression expression) {
		mExpression = expression;
		setCodeBlock(new PlainCodeBlock());
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);

		builder.append(genCommendBlock(linefeed));
		builder.append(mWordElse != null ? mWordElse : "else");
		builder.append(blank);
		builder.append(mWordIf != null ? mWordIf : "if");
		builder.append(blank);
		builder.append(Util.getLeftBracket(this));
		builder.append(mExpression.genCode(linefeed));
		builder.append(Util.getRightBracket(this));
		builder.append(blank);
		builder.append(genPlainCodeBlock(linefeed));
		return builder.toString();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject o = super.toJSONObject();
		o.put("_type", "elseIf");
		o.put("_condition", mExpression.toString());
		return o;
	}

	public void setConditionExpression(Expression condition) {
		this.mExpression = condition;
	}

	public Word getWordElse() {
		return mWordElse;
	}

	public void setWordElse(Word wordElse) {
		this.mWordElse = wordElse;
	}

	public Word getWordIf() {
		return mWordIf;
	}

	public void setWordIf(Word wordIf) {
		this.mWordIf = wordIf;
	}
}
