package cc.suitalk.arbitrarygen.template.base;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BaseTemplateProcessor implements TemplateProcessor {

	protected List<PsychicGenerator> mWorkers;
	protected List<String> mSupportSuffixList;
	
	public BaseTemplateProcessor() {
		mWorkers = new LinkedList<>();
		mSupportSuffixList = new LinkedList<>();
	}
	
	@Override
	public void addTaskWorker(AGPsychicWorker worker) {
		mWorkers.add(worker);
		mSupportSuffixList.add(worker.getSupportSuffix());
	}

	@Override
	public void addTaskWorker(AGPyroWorker worker) {
		mWorkers.add(worker);
		mSupportSuffixList.addAll(worker.getSupportSuffixList());
	}

	@Override
	public List<String> getSupportSuffixList() {
		return mSupportSuffixList;
	}
}
