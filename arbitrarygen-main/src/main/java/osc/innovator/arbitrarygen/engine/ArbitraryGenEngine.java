package osc.innovator.arbitrarygen.engine;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import osc.innovator.arbitrarygen.analyzer.JavaFileLexer;
import osc.innovator.arbitrarygen.base.JavaFileObject;
import osc.innovator.arbitrarygen.core.BaseGenCodeEngine;
import osc.innovator.arbitrarygen.core.GenCodeTaskInfo;
import osc.innovator.arbitrarygen.core.IGenCodeEngine;
import osc.innovator.arbitrarygen.core.SourceFileInfo;
import osc.innovator.arbitrarygen.extension.ITypeDefineWrapper;
import osc.innovator.arbitrarygen.rule.Project;
import osc.innovator.arbitrarygen.rule.Rule;
import osc.innovator.arbitrarygen.utils.FileOperation;
import osc.innovator.arbitrarygen.utils.Log;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ArbitraryGenEngine extends BaseGenCodeEngine {

	private static final String TAG = "CodeGen.ArbitraryGenEngine";
	
	private List<Rule> mRules;
	private TypeDefineWrapperMgr mTypeDefWrapper;
	
	public ArbitraryGenEngine() {
		super();
		mRules = new LinkedList<Rule>();
		mTypeDefWrapper = new TypeDefineWrapperMgr();
	}
	
	@Override
	public void doScanFiles(IGenCodeEngine engine) {
		// TODO
		for (Rule r : mRules) {
			Project p = r.getDefaultProject();
			if (p != null) {
				mSourceFileInfos.addAll(scan(p));
			}
			List<Project> projects = r.getProjects();
			for (int i = 0; i < projects.size(); i++) {
				mSourceFileInfos.addAll(scan(projects.get(i)));
			}
		}
	}

	@Override
	public void doParseFiles(IGenCodeEngine engine) {
		for (int i = 0; i < mSourceFileInfos.size(); i++) {
			SourceFileInfo info = mSourceFileInfos.get(i);
			JavaFileLexer lexer = new JavaFileLexer(info.file);
			JavaFileObject javaFileObject = lexer.start();
			mTypeDefWrapper.doWrap(javaFileObject);
			
			GenCodeTaskInfo taskInfo = new GenCodeTaskInfo();
			taskInfo.FileName = javaFileObject.getFileName();
			taskInfo.RootDir = mConfigInfo.getDestPath() + Util.getPackageDir(javaFileObject);
			taskInfo.javaFileObject = javaFileObject;
			mTasks.add(taskInfo);
		}
	}

	@Override
	public void doConvertTemplates(IGenCodeEngine engine) {
		// do nothing
	}

	private List<SourceFileInfo> scan(Project p) {
		List<SourceFileInfo> results = new LinkedList<SourceFileInfo>();
		for (String path : p.getSrcfiles()) {
			String absolutePath = p.getRoot() + File.separator + p.getName() + File.separator + p.getSrcs().get(0) + File.separator + Util.exchangeToPath(path) + "." + p.getFormats().get(0);
			Log.v(TAG, "path : " + absolutePath);
			List<SourceFileInfo> r = FileOperation.scan(absolutePath, p.getFormats());
			if (r != null && r.size() > 0) {
				results.addAll(r);
			}
		}
		return results;
	}
	
	public List<Rule> getRules() {
		return mRules;
	}

	public boolean addRules(List<Rule> rules) {
		if (rules == null) {
			return false;
		}
		return this.mRules.addAll(rules);
	}
	
	public boolean addRule(Rule rule) {
		if (rule == null || mRules.contains(rule)) {
			return false;
		}
		return this.mRules.add(rule);
	}
	
	public boolean removeRule(Rule rule) {
		return mRules.remove(rule);
	}

	public void addTypeDefWrapper(ITypeDefineWrapper wrapper) {
		this.mTypeDefWrapper.addWrapper(wrapper);
	}
	
	public void removeTypeDefWrapper(ITypeDefineWrapper wrapper) {
		this.mTypeDefWrapper.removeWrapper(wrapper);
	}
}
