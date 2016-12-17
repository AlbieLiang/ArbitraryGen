package cc.suitalk.arbitrarygen.statement;

import net.sf.json.JSONObject;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ForStatement extends BaseStatement {

	private Expression mExpression1;
	private Expression mExpression2;
	private Expression mExpression3;

	public ForStatement() {
		setCodeBlock(new PlainCodeBlock());
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "for"));
		builder.append(blank);
		builder.append(Util.getLeftBracket(this));
		boolean needAppend = false;
		if (mExpression1 != null) {
			builder.append(mExpression1.genCode(""));
			needAppend = true;
		}
		if (needAppend) {
			builder.append(blank);
			builder.append(";");
		}
		if (mExpression2 != null) {
			builder.append(mExpression2.genCode(""));
		}
		if (needAppend) {
			builder.append(blank);
			builder.append(";");
		}
		if (mExpression3 != null) {
			builder.append(mExpression3.genCode(""));
		}
		builder.append(Util.getRightBracket(this));
		builder.append(blank);
		builder.append(genPlainCodeBlock(linefeed));
		return builder.toString();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject o = super.toJSONObject();
		o.put("_type", "for");
		if (mExpression1 != null) {
			o.put("_expression1", mExpression1.toString());
		}
		if (mExpression2 != null) {
			o.put("_expression2", mExpression2.toString());
		}
		if (mExpression3 != null) {
			o.put("_expression3", mExpression3.toString());
		}
		return o;
	}

	public Expression getExpression1() {
		return mExpression1;
	}

	public void setExpression1(Expression expression1) {
		this.mExpression1 = expression1;
	}

	public Expression getExpression2() {
		return mExpression2;
	}

	public void setExpression2(Expression expression2) {
		this.mExpression2 = expression2;
	}

	public Expression getExpression3() {
		return mExpression3;
	}

	public void setExpression3(Expression expression3) {
		this.mExpression3 = expression3;
	}
}
