package cc.suitalk.arbitrarygen.statement;

import net.sf.json.JSONObject;

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


	@Override
	public JSONObject toJSONObject() {
		JSONObject o = super.toJSONObject();
		o.put("_type", "plain");
		return o;
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
