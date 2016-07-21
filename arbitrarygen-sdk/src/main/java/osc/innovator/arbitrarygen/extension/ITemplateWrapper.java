package osc.innovator.arbitrarygen.extension;

import osc.innovator.arbitrarygen.block.TypeDefineCodeBlock;
import osc.innovator.arbitrarygen.core.ContextInfo;
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
	 * @param contextInfo
	 * @param template
	 * @return true to interrupt the wrap action that means do not need to wrap
	 *         any more, otherwise false
	 */
	boolean doWrap(ContextInfo contextInfo, RawTemplate template);

	/**
	 * To wrap the {@link TypeDefineCodeBlock}
	 *
	 * @param contextInfo
	 * @param template
	 * @return true to interrupt the wrap action that means do not need to wrap
	 *         any more, otherwise false
	 */
	boolean doWrap(ContextInfo contextInfo, TypeDefineCodeBlock template);
}
