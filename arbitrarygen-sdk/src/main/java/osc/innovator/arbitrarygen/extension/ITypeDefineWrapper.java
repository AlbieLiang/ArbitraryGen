package osc.innovator.arbitrarygen.extension;

import osc.innovator.arbitrarygen.base.JavaFileObject;
import osc.innovator.arbitrarygen.block.TypeDefineCodeBlock;

/**
 * 
 * @author AlbieLiang
 *
 */
public interface ITypeDefineWrapper {

	/**
	 * To wrap the {@link JavaFileObject}
	 * 
	 * @param fileObject
	 * @return true to interrupt the wrap action that means do not need to wrap
	 *         any more, otherwise false
	 */
	boolean doWrap(JavaFileObject fileObject);

	/**
	 * To wrap the {@link TypeDefineCodeBlock}
	 * 
	 * @param codeblock
	 * @return true to interrupt the wrap action that means do not need to wrap
	 *         any more, otherwise false
	 */
	boolean doWrap(TypeDefineCodeBlock codeblock);
	
	/**
	 * Add a {@link IAGTaskWorker} to the worker set.
	 * 
	 * @param worker
	 * @return
	 */
	boolean addIAGTaskWorker(IAGTaskWorker worker);
	
	/**
	 * Remove a {@link IAGTaskWorker} from the worker set.
	 * 
	 * @param worker
	 * @return
	 */
	boolean removeIAGTaskWorker(IAGTaskWorker worker);
}
