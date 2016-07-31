package cc.suitalk.arbitrarygen.statement;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DoWhileStatement extends BaseStatement {

	private Expression mExpression;
	private Word mWordDo;
	private Word mWordWhile;
	
	public DoWhileStatement() {
		this(null);
	}

	public DoWhileStatement(Expression condition) {
		mExpression = condition;
		setCodeBlock(new PlainCodeBlock());
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);

		builder.append(genCommendBlock(linefeed));
		builder.append(mWordDo != null ? mWordDo : "do");
		builder.append(blank);
		builder.append(genPlainCodeBlock(linefeed));
		builder.append(blank);
		builder.append(mWordWhile != null ? mWordWhile : "while");
		builder.append(blank);
		builder.append(Util.getLeftBlacket(this));
		builder.append(mExpression.genCode(linefeed));
		builder.append(Util.getRightBlacket(this));
		builder.append(Util.getSuffix(this, ";"));
		return builder.toString();
	}

	public void setConditionExpression(Expression condition) {
		this.mExpression = condition;
	}

	public Word getWordDo() {
		return mWordDo;
	}

	public void setWordDo(Word wordDo) {
		this.mWordDo = wordDo;
	}

	public Word getWordWhile() {
		return mWordWhile;
	}

	public void setWordWhile(Word wordWhile) {
		this.mWordWhile = wordWhile;
	}
}
