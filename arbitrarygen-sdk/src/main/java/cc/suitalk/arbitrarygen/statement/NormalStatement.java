package cc.suitalk.arbitrarygen.statement;

import net.sf.json.JSONObject;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class NormalStatement extends BaseStatement {

	private String mStatementStr;

	public NormalStatement() {
	}

	public NormalStatement(String statementStr) {
		mStatementStr = statementStr;
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.nullAsNil(mStatementStr));
		String plainCodeBlock = genPlainCodeBlock(linefeed);
		if (Util.isNullOrNil(plainCodeBlock)) {
			if (!Util.isNullOrNil(mStatementStr)) {
				builder.append(Util.getSuffix(this, ";"));
			}
		} else {
			builder.append(plainCodeBlock);
		}
		return builder.toString();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject o = super.toJSONObject();
		o.put("_type", "normal");
		o.put("_content", mStatementStr);
		return o;
	}

	public void setStatementStr(String statement) {
		mStatementStr = statement;
	}
}
