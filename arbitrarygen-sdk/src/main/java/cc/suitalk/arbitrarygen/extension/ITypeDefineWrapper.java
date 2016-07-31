package cc.suitalk.arbitrarygen.extension;

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ConfigInfo;

/**
 * 
 * @author AlbieLiang
 *
 */
public interface ITypeDefineWrapper {

	/**
	 * To wrap the {@link JavaFileObject}
	 *
	 * @param configInfo current context {@link ConfigInfo}
	 * @param fileObject current {@link JavaFileObject} object
	 * @return true to interrupt the wrap action that means do not need to wrap
	 *         any more, otherwise false
	 */
	boolean doWrap(ConfigInfo configInfo, JavaFileObject fileObject);

	/**
	 * To wrap the {@link TypeDefineCodeBlock}
	 *
	 * @param configInfo current context {@link ConfigInfo}
	 * @param codeblock current {@link TypeDefineCodeBlock} object
	 * @return true to interrupt the wrap action that means do not need to wrap
	 *         any more, otherwise false
	 */
	boolean doWrap(ConfigInfo configInfo, TypeDefineCodeBlock codeblock);
	
	/**
	 * Add a {@link IAGTaskWorker} to the worker set.
	 * 
	 * @param worker the {@link IAGTaskWorker} for {@link ITypeDefineWrapper}
	 * @return added or not
	 */
	boolean addIAGTaskWorker(IAGTaskWorker worker);
	
	/**
	 * Remove a {@link IAGTaskWorker} from the worker set.
	 * 
	 * @param worker the {@link IAGTaskWorker} added before
	 * @return removed success or not
	 */
	boolean removeIAGTaskWorker(IAGTaskWorker worker);
}
