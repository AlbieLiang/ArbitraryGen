package cc.suitalk.arbitrarygen.statement;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;

/**
 * The type statement just has a {@link PlainCodeBlock}
 * 
 * @author AlbieLiang
 *
 */
public class PlainStatement extends BaseStatement {

	public PlainStatement() {
		super.setCodeBlock(new PlainCodeBlock());
	}

	@Override
	public String genCode(String linefeed) {
		PlainCodeBlock codeBlock = getCodeBlock();
		if (codeBlock != null) {
			return codeBlock.genCode(linefeed);
		}
		return null;
	}

	/**
	 * The method will do nothing.
	 * 
	 * @param codeBlock
	 */
//	@Deprecated
//	@Override
//	public void setCodeBlock(PlainCodeBlock codeBlock) {
//		// super.setCodeBlock(codeBlock);
//	}

}
