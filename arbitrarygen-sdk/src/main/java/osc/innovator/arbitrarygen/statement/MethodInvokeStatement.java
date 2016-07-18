package osc.innovator.arbitrarygen.statement;

import java.util.List;

import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.core.Value;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class MethodInvokeStatement extends BaseStatement {

	private String mName;
	private List<Value> mArgs;

	public MethodInvokeStatement(String name, List<Value> args) {
		mName = name;
		mArgs = args;
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();

		builder.append(genCommendBlock(linefeed));
		builder.append(mName);
		builder.append("(");
		if (mArgs != null && mArgs.size() > 0) {
			builder.append(mArgs.get(0));
			for (int i = 1; i < mArgs.size(); i++) {
				builder.append(", ");
				builder.append(mArgs.get(i).getValueStr());
			}
		}
		builder.append(")");
		builder.append(";");
		return builder.toString();
	}

}
