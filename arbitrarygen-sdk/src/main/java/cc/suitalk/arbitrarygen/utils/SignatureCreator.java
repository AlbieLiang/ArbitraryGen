package cc.suitalk.arbitrarygen.utils;

/**
 * 
 * @author AlbieLiang
 *
 */
public final class SignatureCreator {
	
	public static final int TYPE_ANNOTATION = 1;
	public static final String PREFIX = "@SIGNATURE@";
	public static final String PREFIX_ANNOTATION = "@ANNOTATION@";
	public static final String SEPARATOR = "#";

	public static final String create(int type, String...args) {
		StringBuilder builder = new StringBuilder();
		switch (type) {
		case TYPE_ANNOTATION:
			builder.append(PREFIX_ANNOTATION);
			break;

		default:
			builder.append(PREFIX);
			break;
		}
		builder.append(Util.joint(SEPARATOR, args));
		return builder.toString();
	}
}