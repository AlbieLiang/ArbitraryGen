package cc.suitalk.arbitrarygen.base;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
public class PlainCodeBlock implements ICodeGenerator, JSONConverter {

	private List<BaseStatement> mStatements;
	private EnvironmentArgs mEnvironmentArgs;
	private Word mLeftBrace;
	private Word mRightBrace;
	private boolean mDisplayBrace;
	private BaseStatement mBelongStatement;
	
	public PlainCodeBlock() {
		mStatements = new LinkedList<>();
		mLeftBrace = Util.createSignWord("{", Type.BEGIN);
		mRightBrace = Util.createSignWord("}", Type.END);
		mDisplayBrace = true;
	}

	public void attachEnvironmentArgs(EnvironmentArgs args) {
		mEnvironmentArgs = args;
		BaseStatement stm = null;
		for (int i = 0; i < mStatements.size(); i++) {
			stm = mStatements.get(i);
			stm.attachEnvironmentArgs(args);
		}
	}

	@Override
	public JSONObject toJSONObject() {
		if (mStatements.isEmpty()) {
			return null;
		}
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < mStatements.size(); i++) {
			BaseStatement stm = mStatements.get(i);
			jsonArray.add(stm.toJSONObject());
		}
		if (!jsonArray.isEmpty()) {
			jsonObject.put("statement", jsonArray);
		}
		return jsonObject;
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
		if (mDisplayBrace) {
			builder.append(mLeftBrace != null ? mLeftBrace : "{");
		}
		for (BaseStatement statement : mStatements) {
			builder.append(getLinefeed(linefeed + TAB));
			builder.append(statement.genCode(linefeed + TAB));
		}
		builder.append(getLinefeed(linefeed));
		if (mDisplayBrace) {
			builder.append(mRightBrace != null ? mRightBrace : "}");
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
	
	public Word getLeftBrace() {
		return mLeftBrace;
	}

	public void setLeftBrace(Word leftBrace) {
		this.mLeftBrace = leftBrace;
	}

	public Word getRightBrace() {
		return mRightBrace;
	}

	public void setRightBrace(Word rightBrace) {
		this.mRightBrace = rightBrace;
	}

	public boolean isDisplayBrace() {
		return mDisplayBrace;
	}

	public void setDisplayBrace(boolean displayBrace) {
		this.mDisplayBrace = displayBrace;
	}
}