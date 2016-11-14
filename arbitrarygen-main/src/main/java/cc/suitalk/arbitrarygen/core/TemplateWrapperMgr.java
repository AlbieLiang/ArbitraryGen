package cc.suitalk.arbitrarygen.core;

import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.extension.TemplateWrapper;
import cc.suitalk.arbitrarygen.template.RawTemplate;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TemplateWrapperMgr {

	private List<TemplateWrapper> mWrappers;
	private static TemplateWrapperMgr mgr;

	public static TemplateWrapperMgr getMgr() {
		if (mgr == null) {
			mgr = new TemplateWrapperMgr();
		}
		return mgr;
	}

	public TemplateWrapperMgr() {
		mWrappers = new LinkedList<>();
	}

	public void doWrap(ContextInfo contextInfo, RawTemplate template) {
		for (int i = 0; i < mWrappers.size(); i++) {
			TemplateWrapper p = mWrappers.get(i);
			if (p.doWrap(contextInfo, template)) {
				return;
			}
		}
	}

	public void doWrap(ContextInfo contextInfo, TypeDefineCodeBlock template) {
		for (int i = 0; i < mWrappers.size(); i++) {
			TemplateWrapper p = mWrappers.get(i);
			if (p.doWrap(contextInfo, template)) {
				return;
			}
		}
	}

	public List<TemplateWrapper> getWrappers() {
		return mWrappers;
	}

	public void addWrapper(TemplateWrapper wrapper) {
		if (wrapper != null) {
			this.mWrappers.add(0, wrapper);
		}
	}

	public void removeWrapper(TemplateWrapper wrapper) {
		mWrappers.remove(wrapper);
	}
}
