package cc.suitalk.arbitrarygen.core;

import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.extension.ICustomizeConvertor;
import cc.suitalk.arbitrarygen.extension.ICustomizeGenerator;
import cc.suitalk.arbitrarygen.extension.ICustomizeParser;
import cc.suitalk.arbitrarygen.extension.ITemplateWrapper;
import cc.suitalk.arbitrarygen.template.RawTemplate;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.Log;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BaseGenCodeEngine implements IGenCodeEngine {

	private static final String TAG = "CodeGen.BaseGenCodeEngine";
	
	protected ConfigInfo mConfigInfo;
	protected List<SourceFileInfo> mSourceFileInfos;
	protected List<GenCodeTaskInfo> mTasks;
	protected List<RawTemplate> mRawTemplates;
	protected TemplateWrapperMgr mWrapperMgr;
	private TemplateDirectorMgr mDirectorMgr;
	
	public BaseGenCodeEngine() {
		mSourceFileInfos = new LinkedList<SourceFileInfo>();
		mTasks = new LinkedList<GenCodeTaskInfo>();
		mRawTemplates = new LinkedList<RawTemplate>();
		mDirectorMgr = new TemplateDirectorMgr();
		mDirectorMgr.setConvertorMgr(new TemplateConvertorMgr());
		mDirectorMgr.setParserMgr(new TemplateParserMgr());
		mWrapperMgr = new TemplateWrapperMgr();
		setConfigInfo(new ConfigInfo());
	}

	@Override
	public final void start() {
		long startMsec = System.currentTimeMillis();
		doScanFiles(this);
		Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>Parse Files Started<<<<<<<<<<<<<<<<<<<<<<");
		long startParseMsec = System.currentTimeMillis();
		doParseFiles(this);
		long startConvMsec = System.currentTimeMillis();
		Log.i(TAG, ">>>>>>Parse Files finished(use " + (startConvMsec - startParseMsec) + " msec).");

		Log.i(TAG, ">>>>>>>>>>>>>>>>>Convert templates Started<<<<<<<<<<<<<<<<<<<");
		doConvertTemplates(this);
		long startGenMsec = System.currentTimeMillis();
		Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>GenCode Started<<<<<<<<<<<<<<<<<<<<<<<");
		doGenCode(this);
		long endMsec = System.currentTimeMillis();
		Log.i(TAG, ">>>>>>Convert templates finished(use " + (endMsec - startGenMsec) + " msec). Total cost " + (endMsec - startMsec) + " msec.");
	}

	@Override
	public synchronized void doGenCode(IGenCodeEngine engine) {
		for (GenCodeTaskInfo info : mTasks) {
			JavaFileObject javaFileObject = info.javaFileObject;
			ICustomizeGenerator generator = new CodeGenerator(javaFileObject);
			FileOperation.saveToFile(info, generator.genCode());
		}
	}
	
	@Override
	public final ICustomizeParser getFirstMatchParser(String suffix) {
		TemplateParserMgr mgr = mDirectorMgr.getParserMgr();
		if (mgr != null) {
			return mgr.getFirstMatchParser(suffix);
		}
		return null;
	}
	
	@Override
	public final ICustomizeConvertor getFirstMatchConvertor(RawTemplate rawTemplate) {
		TemplateConvertorMgr mgr = mDirectorMgr.getConvertorMgr();
		if (mgr != null) {
			return mgr.getFirstMatchConvertor(rawTemplate);
		}
		return null;
	}
	
	@Override
	public void setConfigInfo(ConfigInfo configInfo) {
		if (configInfo != null) {
			mConfigInfo = configInfo;
		}
	}

	@Override
	public void addParser(ICustomizeParser parser) {
		TemplateParserMgr mgr = mDirectorMgr.getParserMgr();
		if (mgr != null) {
			mgr.addParser(parser);
		}
	}

	@Override
	public void addConvertor(ICustomizeConvertor convertor) {
		TemplateConvertorMgr mgr = mDirectorMgr.getConvertorMgr();
		if (mgr != null) {
			mgr.addConvertor(convertor);
		}
	}

	@Override
	public void addWrapper(ITemplateWrapper wrapper) {
		mWrapperMgr.addWrapper(wrapper);
	}

	@Override
	public void addAllSuffixList(List<String> suffixList) {
		mDirectorMgr.addAllSuffixList(suffixList);
	}
	
	@Override
	public final List<String> getSupportSuffixList() {
		return mDirectorMgr.getSupportSuffixList();
	}
}
