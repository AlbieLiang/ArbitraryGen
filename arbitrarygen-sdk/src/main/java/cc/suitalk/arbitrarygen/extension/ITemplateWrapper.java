package cc.suitalk.arbitrarygen.extension;

import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ContextInfo;
import cc.suitalk.arbitrarygen.template.RawTemplate;

/**
 * 
 * @author AlbieLiang
 *
 */
public interface ITemplateWrapper {

	/**
	 * To wrap the {@link RawTemplate}
	 *
	 * @param contextInfo the source file context info
	 * @param template the {@link RawTemplate} to do wrap
	 * @return true to interrupt the wrap action that means do not need to wrap
	 *         any more, otherwise false
	 */
	boolean doWrap(ContextInfo contextInfo, RawTemplate template);

	/**
	 * To wrap the {@link TypeDefineCodeBlock}
	 *
	 * @param contextInfo the source file context info
	 * @param template the {@link RawTemplate} to do wrap
	 * @return true to interrupt the wrap action that means do not need to wrap
	 *         any more, otherwise false
	 */
	boolean doWrap(ContextInfo contextInfo, TypeDefineCodeBlock template);
}
