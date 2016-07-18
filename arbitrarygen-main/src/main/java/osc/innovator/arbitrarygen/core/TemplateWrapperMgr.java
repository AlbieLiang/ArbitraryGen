package osc.innovator.arbitrarygen.core;

import java.util.LinkedList;
import java.util.List;

import osc.innovator.arbitrarygen.block.TypeDefineCodeBlock;
import osc.innovator.arbitrarygen.extension.ITemplateWrapper;
import osc.innovator.arbitrarygen.template.RawTemplate;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TemplateWrapperMgr {

	private List<ITemplateWrapper> mWrappers;
	private static TemplateWrapperMgr mgr;

	public static TemplateWrapperMgr getMgr() {
		if (mgr == null) {
			mgr = new TemplateWrapperMgr();
		}
		return mgr;
	}

	public TemplateWrapperMgr() {
		mWrappers = new LinkedList<ITemplateWrapper>();
	}

	public void doWrap(RawTemplate template) {
		for (int i = 0; i < mWrappers.size(); i++) {
			ITemplateWrapper p = mWrappers.get(i);
			if (p.doWrap(template)) {
				return;
			}
		}
	}

	public void doWrap(TypeDefineCodeBlock template) {
		for (int i = 0; i < mWrappers.size(); i++) {
			ITemplateWrapper p = mWrappers.get(i);
			if (p.doWrap(template)) {
				return;
			}
		}
	}

	public List<ITemplateWrapper> getWrappers() {
		return mWrappers;
	}

	public void addWrapper(ITemplateWrapper wrapper) {
		if (wrapper != null) {
			this.mWrappers.add(0, wrapper);
		}
	}

	public void removeWrapper(ITemplateWrapper wrapper) {
		mWrappers.remove(wrapper);
	}
}
