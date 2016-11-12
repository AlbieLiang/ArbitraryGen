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

	private static TypeDefineWrapperMgr sMgr;

	private List<ITypeDefineWrapper> mWrapperList;

	public static TypeDefineWrapperMgr getMgr() {
		if (sMgr == null) {
			sMgr = new TypeDefineWrapperMgr();
		}
		return sMgr;
	}

	public TypeDefineWrapperMgr() {
		mWrapperList = new LinkedList<>();
	}

	public void doWrap(ConfigInfo configInfo, JavaFileObject fileObject) {
		for (int i = 0; i < mWrapperList.size(); i++) {
			ITypeDefineWrapper p = mWrapperList.get(i);
			// TODO
			if (p.doWrap(configInfo, fileObject)) {
//				return;
			}
		}
	}

	public void doWrap(ConfigInfo configInfo, TypeDefineCodeBlock codeblock) {
		for (int i = 0; i < mWrapperList.size(); i++) {
			ITypeDefineWrapper p = mWrapperList.get(i);
			if (p.doWrap(configInfo, codeblock)) {
				return;
			}
		}
	}

	public List<ITypeDefineWrapper> getWrapperList() {
		return mWrapperList;
	}

	public void addWrapper(ITypeDefineWrapper wrapper) {
		if (wrapper != null) {
			this.mWrapperList.add(0, wrapper);
		}
	}

	public void removeWrapper(ITypeDefineWrapper wrapper) {
		mWrapperList.remove(wrapper);
	}
}
