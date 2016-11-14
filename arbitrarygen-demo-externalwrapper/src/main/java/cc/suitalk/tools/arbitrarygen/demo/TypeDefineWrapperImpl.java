package cc.suitalk.tools.arbitrarygen.demo;

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ConfigInfo;
import cc.suitalk.arbitrarygen.extension.AGTaskWorker;
import cc.suitalk.arbitrarygen.extension.TypeDefineWrapper;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TypeDefineWrapperImpl implements TypeDefineWrapper {

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
	public boolean addIAGTaskWorker(AGTaskWorker worker) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeIAGTaskWorker(AGTaskWorker worker) {
		// TODO Auto-generated method stub
		return false;
	}

}
