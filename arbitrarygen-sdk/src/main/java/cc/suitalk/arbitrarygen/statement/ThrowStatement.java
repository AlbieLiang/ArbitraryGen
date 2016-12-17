package cc.suitalk.arbitrarygen.statement;

import net.sf.json.JSONObject;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ThrowStatement extends BaseStatement {

	private Expression mExpression;
	
	public ThrowStatement(Expression expression) {
		mExpression = expression;
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "throw"));
		builder.append(getWordBlank(BLANK_1));
		builder.append(mExpression.genCode(linefeed));
		builder.append(Util.getSuffix(this, ";"));
		return builder.toString();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject o = super.toJSONObject();
		o.put("_type", "throw");
		o.put("_expression", mExpression.toString());
		return o;
	}

}
