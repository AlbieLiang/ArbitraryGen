package osc.innovator.arbitrarygen.statement;

import java.util.LinkedList;
import java.util.List;

import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.base.Expression;
import osc.innovator.arbitrarygen.base.PlainCodeBlock;
import osc.innovator.arbitrarygen.core.Environment.EnvironmentArgs;
import osc.innovator.arbitrarygen.utils.Util;

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
		builder.append(Util.getLeftBlacket(this));
		builder.append(mExpression.genCode(linefeed));
		builder.append(Util.getRightBlacket(this));
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

	public boolean addElseIfStatment(ElseIfStatement statement) {
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
