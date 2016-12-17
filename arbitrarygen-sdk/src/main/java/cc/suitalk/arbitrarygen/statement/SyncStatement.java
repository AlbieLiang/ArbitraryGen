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
public class SyncStatement extends BaseStatement {

	private Expression mExpression;
	
	public SyncStatement() {
		this(null);
	}

	public SyncStatement(Expression condition) {
		mExpression = condition;
		setCodeBlock(new PlainCodeBlock());
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "synchronized"));
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
		o.put("_type", "synchronized");
		o.put("_expression", mExpression.toString());
		return o;
	}

	public void setConditionExpression(Expression condition) {
		this.mExpression = condition;
	}
}
