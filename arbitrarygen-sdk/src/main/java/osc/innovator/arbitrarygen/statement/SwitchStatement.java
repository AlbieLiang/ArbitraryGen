package osc.innovator.arbitrarygen.statement;

import java.util.LinkedList;
import java.util.List;

import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.base.Expression;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.core.Environment.EnvironmentArgs;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class SwitchStatement extends BaseStatement {

	private Expression mExpression;
	private List<CaseStatement> mCaseStatements;
	private DefaultStatement mDefaultStatement;
	private Word mLeftBrack;
	private Word mRightBrack;

	public SwitchStatement() {
		this(null);
	}
	
	public SwitchStatement(Expression conditionExpression) {
		mExpression = conditionExpression;
		mCaseStatements = new LinkedList<CaseStatement>();
	}

	@Override
	public void onAttachEnvironmentArgs(EnvironmentArgs args) {
		for (int i = 0; i < mCaseStatements.size(); i++) {
			BaseStatement stm = mCaseStatements.get(i);
			stm.attachEnvironmentArgs(args);
		}
		if (mDefaultStatement != null) {
			mDefaultStatement.attachEnvironmentArgs(args);
		}
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "switch"));
		builder.append(blank);
		builder.append(Util.getLeftBlacket(this));
		builder.append(mExpression.genCode(linefeed));
		builder.append(Util.getRightBlacket(this));
		builder.append(blank);
		builder.append(mLeftBrack != null ? mLeftBrack : "{");
		for (int i = 0; i < mCaseStatements.size(); i++) {
			CaseStatement s = mCaseStatements.get(i);
			if (s != null) {
				builder.append(getLinefeed(linefeed));
				builder.append(s.genCode(linefeed));
			}
		}
		if (mDefaultStatement != null) {
			builder.append(getLinefeed(linefeed));
			builder.append(mDefaultStatement.genCode(linefeed));
		}
		builder.append(getLinefeed(linefeed));
		builder.append(mRightBrack != null ? mRightBrack : "}");
		return builder.toString();
	}

	public boolean addCaseStatment(CaseStatement statement) {
		if (statement == null) {
			return false;
		}
		statement.attachEnvironmentArgs(getEnvironmentArgs());
		statement.setBelongStatement(this);
		mCaseStatements.add(statement);
		return true;
	}

	public void setDefaultStatement(DefaultStatement statement) {
		mDefaultStatement = statement;
		if (mDefaultStatement != null) {
			mDefaultStatement.setBelongStatement(this);
			mDefaultStatement.attachEnvironmentArgs(getEnvironmentArgs());
		}
	}

	public Word getLeftBrack() {
		return mLeftBrack;
	}

	public void setLeftBrack(Word leftBrack) {
		this.mLeftBrack = leftBrack;
	}

	public Word getRightBrack() {
		return mRightBrack;
	}

	public void setRightBrack(Word rightBrack) {
		this.mRightBrack = rightBrack;
	}
	
	public void setConditionExpression(Expression condition) {
		this.mExpression = condition;
	}
}
