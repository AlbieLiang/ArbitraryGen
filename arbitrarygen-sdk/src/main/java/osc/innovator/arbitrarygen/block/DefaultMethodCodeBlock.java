package osc.innovator.arbitrarygen.block;

import osc.innovator.arbitrarygen.base.PlainCodeBlock;
import osc.innovator.arbitrarygen.core.KeyWords;
import osc.innovator.arbitrarygen.model.DefaultKeyValuePair;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DefaultMethodCodeBlock extends MethodCodeBlock {

	public DefaultMethodCodeBlock(String name, String returnType,
			DefaultKeyValuePair... args) {
		setModifier(KeyWords.V_JAVA_KEYWORDS_PUBLIC);
		setIsStatic(false);
		setIsFinal(false);
		this.setName(Util.createSimpleTypeName(name));
		this.setType(Util.createSimpleTypeName(returnType));
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				mArgs.add(args[i]);
			}
		}
		setCodeBlock(new PlainCodeBlock());
	}
}
