package cc.suitalk.arbitrarygen.statement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.core.Environment.EnvironmentArgs;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class IfElseStatement extends BaseStatement {

	private Expression mExpression;
	private List<ElseIfStatement> mElseIfStatements;
	private ElseStatement mElseStatement;

	public IfElseStatement() {
		this(null);
	}
	
	public IfElseStatement(Expression conditionExpression) {
		mExpression = conditionExpression;
		mElseIfStatements = new LinkedList<ElseIfStatement>();
		setCodeBlock(new PlainCodeBlock());
	}

	@Override
	public void onAttachEnvironmentArgs(EnvironmentArgs args) {
		for (int i = 0; i < mElseIfStatements.size(); i++) {
			BaseStatement stm = mElseIfStatements.get(i);
			stm.attachEnvironmentArgs(args);
		}
		if (mElseStatement != null) {
			mElseStatement.attachEnvironmentArgs(args);
		}
	}
	
	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "if"));
		builder.append(blank);
		builder.append(Util.getLeftBracket(this));
		builder.append(mExpression.genCode(linefeed));
		builder.append(Util.getRightBracket(this));
		builder.append(blank);
		builder.append(genPlainCodeBlock(linefeed));
		for (int i = 0; i < mElseIfStatements.size(); i++) {
			ElseIfStatement s = mElseIfStatements.get(i);
			if (s != null) {
				builder.append(blank);
				builder.append(s.genCode(linefeed));
			}
		}
		if (mElseStatement != null) {
			builder.append(blank);
			builder.append(mElseStatement.genCode(linefeed));
		}
		return builder.toString();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject o = super.toJSONObject();
		o.put("_type", "if");
		o.put("_condition", mExpression.toString());
		JSONArray elseIf = new JSONArray();
		for (int i = 0; i < mElseIfStatements.size(); i++) {
			ElseIfStatement s = mElseIfStatements.get(i);
			if (s == null) {
				continue;
			}
			elseIf.add(s.toJSONObject());
		}
		if (!elseIf.isEmpty()) {
			o.put("_elseIf", elseIf);
		}
		if (mElseStatement != null) {
			o.put("_else", mElseStatement.toJSONObject());
		}
		return o;
	}

	public boolean addElseIfStatement(ElseIfStatement statement) {
		if (statement == null) {
			return false;
		}
		statement.attachEnvironmentArgs(getEnvironmentArgs());
		statement.setBelongStatement(this);
		mElseIfStatements.add(statement);
		return true;
	}

	public void setElseStatement(ElseStatement statement) {
		mElseStatement = statement;
		if (mElseStatement != null) {
			mElseStatement.attachEnvironmentArgs(getEnvironmentArgs());
			mElseStatement.setBelongStatement(this);
		}
	}

	public void setConditionExpression(Expression condition) {
		this.mExpression = condition;
	}
}
