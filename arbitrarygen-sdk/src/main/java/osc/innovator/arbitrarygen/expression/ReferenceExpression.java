package osc.innovator.arbitrarygen.expression;

import java.util.LinkedList;
import java.util.List;

import osc.innovator.arbitrarygen.base.Expression;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.core.KeyWords.Sign.Type;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ReferenceExpression extends Expression {

	private static final String TAG = "CodeGen.ReferenceExpression";
	private List<Word> mWords;

	public ReferenceExpression() {
		mWords = new LinkedList<Word>();
	}

	public ReferenceExpression(String variable) {
		this();
		setVariable(variable);
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		if (!Util.isNullOrNil(mBlankStr)) {
			builder.append(mBlankStr);
		}
		if (needSurround()) {
			builder.append("(");
		}
		builder.append(Util.getCodeStr(mWords));
		if (needSurround()) {
			builder.append(")");
		}
		return builder.toString();
	}

	public void appendNode(String name) {
		if (mWords.size() == 0) {
			mWords.add(Util.createKeyWord(name));
		} else {
			mWords.add(Util.createSignWord(".", Type.NORMAL));
			mWords.add(Util.createKeyWord(name));
		}
	}

	public void appendNode(Word word) {
		if (word != null) {
			mWords.add(word);
		}
	}
	
	@Override
	public String getVariable() {
		if (Util.isNullOrNil(super.getVariable())) {
			super.setVariable(Util.getCodeValue(mWords));
		}
		return super.getVariable();
	}

	@Override
	public void setVariable(String variable) {
		if (Util.isNullOrNil(variable)) {
			return;
		}
		mWords.clear();
		String[] names = variable.split("\\.");
		if (names.length > 0) {
			mWords.add(Util.createKeyWord(names[0]));
		}
		for (int i = 1; i < names.length; i++) {
			mWords.add(Util.createSignWord(".", Type.NORMAL));
			mWords.add(Util.createKeyWord(names[i]));
		}
		super.setVariable(variable);
	}
}
