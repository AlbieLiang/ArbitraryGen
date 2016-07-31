package cc.suitalk.arbitrarygen.base;

import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Environment.EnvironmentArgs;
import cc.suitalk.arbitrarygen.core.KeyWords.Sign.Type;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * "{...}" code block.
 * 
 * @author AlbieLiang
 *
 */
public class PlainCodeBlock implements ICodeGenerator {

	private List<BaseStatement> mStatements;
	private EnvironmentArgs mEnvironmentArgs;
	private Word mLeftBrack;
	private Word mRightBrack;
	private boolean mDisplayBrack;
	private BaseStatement mBelongStatement;
	
	public PlainCodeBlock() {
		mStatements = new LinkedList<BaseStatement>();
		mLeftBrack = Util.createSignWord("{", Type.BEGIN);
		mRightBrack = Util.createSignWord("}", Type.END);
		mDisplayBrack = true;
	}

	public void attachEnvironmentArgs(EnvironmentArgs args) {
		mEnvironmentArgs = args;
		BaseStatement stm = null;
		for (int i = 0; i < mStatements.size(); i++) {
			stm = mStatements.get(i);
			stm.attachEnvironmentArgs(args);
		}
	}

	public BaseStatement getBelongStatement() {
		return mBelongStatement;
	}

	public void setBelongStatement(BaseStatement stm) {
		this.mBelongStatement = stm;
		for (int i = 0; i < mStatements.size(); i++) {
			stm = mStatements.get(i);
			stm.setOuterStatement(mBelongStatement);
		}
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		if (mDisplayBrack) {
			builder.append(mLeftBrack != null ? mLeftBrack : "{");
		}
		for (BaseStatement statement : mStatements) {
			builder.append(getLinefeed(linefeed + TAB));
			builder.append(statement.genCode(linefeed + TAB));
		}
		builder.append(getLinefeed(linefeed));
		if (mDisplayBrack) {
			builder.append(mRightBrack != null ? mRightBrack : "}");
		}
		return builder.toString();
	}

	public boolean isJavaFile() {
		return mEnvironmentArgs == null ? false : mEnvironmentArgs.isJavaFile();
	}

	public String getLinefeed(String linefeed) {
		return isJavaFile() ? "" : linefeed;
	}
	
	public boolean addStatement(BaseStatement s) {
		if (s == null) {
			return false;
		}
		s.attachEnvironmentArgs(mEnvironmentArgs);
		s.setOuterStatement(mBelongStatement);
		return mStatements.add(s);
	}

	public boolean removeStatement(BaseStatement s) {
		return mStatements.remove(s);
	}

	public BaseStatement getStatement(int i) {
		return mStatements.get(i);
	}

	public BaseStatement removeStatement(int i) {
		return mStatements.remove(i);
	}

	public int countOfStatement() {
		return mStatements.size();
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

	public boolean isDisplayBrack() {
		return mDisplayBrack;
	}

	public void setDisplayBrack(boolean displayBrack) {
		this.mDisplayBrack = displayBrack;
	}
}