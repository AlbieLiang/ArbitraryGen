package osc.innovator.arbitrarygen.statement;

import java.util.LinkedList;
import java.util.List;

import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.base.PlainCodeBlock;
import osc.innovator.arbitrarygen.core.Environment.EnvironmentArgs;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TryStatement extends BaseStatement {

	private List<CatchStatement> mCatchStatements;
	private FinallyStatement mFinallyStatement;

	public TryStatement() {
		mCatchStatements = new LinkedList<CatchStatement>();
		setCodeBlock(new PlainCodeBlock());
	}

	@Override
	public void onAttachEnvironmentArgs(EnvironmentArgs args) {
		for (int i = 0; i < mCatchStatements.size(); i++) {
			BaseStatement stm = mCatchStatements.get(i);
			stm.attachEnvironmentArgs(args);
		}
		if (mFinallyStatement != null) {
			mFinallyStatement.attachEnvironmentArgs(args);
		}
	}
	
	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "try"));
		builder.append(blank);
		builder.append(genPlainCodeBlock(linefeed));
		for (int i = 0; i < mCatchStatements.size(); i++) {
			CatchStatement s = mCatchStatements.get(i);
			if (s != null) {
				builder.append(blank);
				builder.append(s.genCode(linefeed));
			}
		}
		if (mFinallyStatement != null) {
			builder.append(blank);
			builder.append(mFinallyStatement.genCode(linefeed));
		}
		return builder.toString();
	}

	public boolean addCatchStatment(CatchStatement statement) {
		if (statement == null) {
			return false;
		}
		statement.attachEnvironmentArgs(getEnvironmentArgs());
		statement.setBelongStatement(this);
		mCatchStatements.add(statement);
		return true;
	}

	public void setFinallyStatement(FinallyStatement statement) {
		mFinallyStatement = statement;
		if (mFinallyStatement != null) {
			mFinallyStatement.attachEnvironmentArgs(getEnvironmentArgs());
			mFinallyStatement.setBelongStatement(this);
		}
	}
}
