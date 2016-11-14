package cc.suitalk.arbitrarygen.core;

import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.extension.TypeDefineWrapper;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TypeDefineWrapperMgr {

	private static TypeDefineWrapperMgr sMgr;

	private List<TypeDefineWrapper> mWrapperList;

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
			TypeDefineWrapper p = mWrapperList.get(i);
			// TODO
			if (p.doWrap(configInfo, fileObject)) {
//				return;
			}
		}
	}

	public void doWrap(ConfigInfo configInfo, TypeDefineCodeBlock codeblock) {
		for (int i = 0; i < mWrapperList.size(); i++) {
			TypeDefineWrapper p = mWrapperList.get(i);
			if (p.doWrap(configInfo, codeblock)) {
				return;
			}
		}
	}

	public List<TypeDefineWrapper> getWrapperList() {
		return mWrapperList;
	}

	public void addWrapper(TypeDefineWrapper wrapper) {
		if (wrapper != null) {
			this.mWrapperList.add(0, wrapper);
		}
	}

	public void removeWrapper(TypeDefineWrapper wrapper) {
		mWrapperList.remove(wrapper);
	}
}
