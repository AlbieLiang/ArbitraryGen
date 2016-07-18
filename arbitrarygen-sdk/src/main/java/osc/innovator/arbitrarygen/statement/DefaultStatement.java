package osc.innovator.arbitrarygen.statement;

import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.base.PlainCodeBlock;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DefaultStatement extends BaseStatement {

	private Word mWordColon;
	
	public DefaultStatement() {
		setCodeBlock(new PlainCodeBlock());
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);
		
		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "default"));
		builder.append(blank);
		builder.append(mWordColon != null ? mWordColon : ":");
		builder.append(blank);
//		builder.append(genPlainCodeBlock(linefeed));
		PlainCodeBlock codeBlock = getCodeBlock();
		if (codeBlock != null) {
			for (int i = 0; i < codeBlock.countOfStatement(); i++) {
				BaseStatement statement  = codeBlock.getStatement(i);
				builder.append(getLinefeed(linefeed + TAB));
				builder.append(statement.genCode(linefeed + TAB));
			}
		}
		return builder.toString();
	}

	public Word getWordColon() {
		return mWordColon;
	}

	public void setWordColon(Word wordColon) {
		this.mWordColon = wordColon;
	}
}
