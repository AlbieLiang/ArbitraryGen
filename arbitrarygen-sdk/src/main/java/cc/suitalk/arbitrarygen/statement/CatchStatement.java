package cc.suitalk.arbitrarygen.statement;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class CatchStatement extends BaseStatement {

	private DefinitionStatement mDefinitionStatement;
	
	public CatchStatement() {
		this(null);
	}

	public CatchStatement(DefinitionStatement s) {
		mDefinitionStatement = s;
		setCodeBlock(new PlainCodeBlock());
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "catch"));
		builder.append(blank);
		builder.append(Util.getLeftBlacket(this));
		builder.append(mDefinitionStatement.genCode(linefeed));
		builder.append(Util.getRightBlacket(this));
		builder.append(blank);
		builder.append(genPlainCodeBlock(linefeed));
		return builder.toString();
	}

	public DefinitionStatement getDefinitionStatement() {
		return mDefinitionStatement;
	}

	public void setDefinitionStatement(DefinitionStatement stm) {
		this.mDefinitionStatement = stm;
	}
}
