package osc.innovator.arbitrarygen.statement;

import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.expression.ReferenceExpression;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class PackageStatement extends BaseStatement {

	private ReferenceExpression mPackage;

	public PackageStatement(ReferenceExpression packageName) {
		mPackage = packageName;
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "package"));
		builder.append(getWordBlank(BLANK_1));
		builder.append(mPackage.genCode(linefeed));
		builder.append(Util.getSuffix(this, ";"));
		return builder.toString();
	}

	public ReferenceExpression getPackageRefExpression() {
		return mPackage;
	}

	public String getPackage() {
		return mPackage != null ? mPackage.genCode("").trim() : "";
	}
}
