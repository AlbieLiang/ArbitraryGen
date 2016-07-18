package osc.innovator.arbitrarygen.extension;

import osc.innovator.arbitrarygen.block.TypeDefineCodeBlock;
import osc.innovator.arbitrarygen.template.RawTemplate;

/**
 * 
 * @author AlbieLiang
 *
 */
public interface ITemplateWrapper {

	/**
	 * To wrap the {@link RawTemplate}
	 * 
	 * @param template
	 * @return true to interrupt the wrap action that means do not need to wrap
	 *         any more, otherwise false
	 */
	boolean doWrap(RawTemplate template);

	/**
	 * To wrap the {@link TypeDefineCodeBlock}
	 * 
	 * @param template
	 * @return true to interrupt the wrap action that means do not need to wrap
	 *         any more, otherwise false
	 */
	boolean doWrap(TypeDefineCodeBlock template);
}
