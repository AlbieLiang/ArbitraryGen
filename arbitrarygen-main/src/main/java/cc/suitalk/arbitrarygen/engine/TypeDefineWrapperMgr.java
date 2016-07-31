package cc.suitalk.arbitrarygen.engine;

import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ConfigInfo;
import cc.suitalk.arbitrarygen.extension.ITypeDefineWrapper;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TypeDefineWrapperMgr {

	private List<ITypeDefineWrapper> mWrappers;
	private static TypeDefineWrapperMgr sMgr;

	public static TypeDefineWrapperMgr getMgr() {
		if (sMgr == null) {
			sMgr = new TypeDefineWrapperMgr();
		}
		return sMgr;
	}

	public TypeDefineWrapperMgr() {
		mWrappers = new LinkedList<ITypeDefineWrapper>();
	}

	public void doWrap(ConfigInfo configInfo, JavaFileObject fileObject) {
		for (int i = 0; i < mWrappers.size(); i++) {
			ITypeDefineWrapper p = mWrappers.get(i);
			// TODO
			if (p.doWrap(configInfo, fileObject)) {
//				return;
			}
		}
	}

	public void doWrap(ConfigInfo configInfo, TypeDefineCodeBlock codeblock) {
		for (int i = 0; i < mWrappers.size(); i++) {
			ITypeDefineWrapper p = mWrappers.get(i);
			if (p.doWrap(configInfo, codeblock)) {
				return;
			}
		}
	}

	public List<ITypeDefineWrapper> getWrappers() {
		return mWrappers;
	}

	public void addWrapper(ITypeDefineWrapper wrapper) {
		if (wrapper != null) {
			this.mWrappers.add(0, wrapper);
		}
	}

	public void removeWrapper(ITypeDefineWrapper wrapper) {
		mWrappers.remove(wrapper);
	}
}
