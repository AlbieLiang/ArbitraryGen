package osc.innovator.arbitrarygen.block;

import osc.innovator.arbitrarygen.model.DefaultKeyValuePair;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ConstructorMethodCodeBlock extends DefaultMethodCodeBlock {

	public ConstructorMethodCodeBlock(TypeDefineCodeBlock template,
			DefaultKeyValuePair... args) {
		this(template.getName().getName(), args);
	}

	public ConstructorMethodCodeBlock(String name, DefaultKeyValuePair... args) {
		super(name, "", args);
	}
}
