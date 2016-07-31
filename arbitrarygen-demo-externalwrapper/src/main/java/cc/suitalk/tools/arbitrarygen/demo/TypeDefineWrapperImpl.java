package cc.suitalk.tools.arbitrarygen.demo;

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ConfigInfo;
import cc.suitalk.arbitrarygen.extension.IAGTaskWorker;
import cc.suitalk.arbitrarygen.extension.ITypeDefineWrapper;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TypeDefineWrapperImpl implements ITypeDefineWrapper {

	@Override
	public boolean doWrap(ConfigInfo configInfo, JavaFileObject fileObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doWrap(ConfigInfo configInfo, TypeDefineCodeBlock codeblock) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addIAGTaskWorker(IAGTaskWorker worker) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeIAGTaskWorker(IAGTaskWorker worker) {
		// TODO Auto-generated method stub
		return false;
	}

}
