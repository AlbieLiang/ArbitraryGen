package osc.innovator.arbitrarygen.base;

/**
 * 
 * @author AlbieLiang
 *
 */
public interface ICodeGenerator {

	public static final String TAB = "\t";
	public static final String BLANK_1 = " ";
	public static final String LINEFEED_UNIT = "\r\n";

	String genCode(String linefeed);
}
