package osc.innovator.arbitrarygen.template.base;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BaseTemplateProcessor implements ITemplateProcessor {

	protected List<IPsychicGenerator> mWorkers;
	protected List<String> mSupportSuffixList;
	
	public BaseTemplateProcessor() {
		mWorkers = new LinkedList<>();
		mSupportSuffixList = new LinkedList<>();
	}
	
	@Override
	public void addTaskWorker(IGenCodeWorker worker) {
		mWorkers.add(worker);
		mSupportSuffixList.add(worker.getSupportSuffix());
	}

	@Override
	public void addTaskWorker(IAGPsychicWorker worker) {
		mWorkers.add(worker);
		mSupportSuffixList.addAll(worker.getSupportSuffixList());
	}

	@Override
	public List<String> getSupportSuffixList() {
		return mSupportSuffixList;
	}
}
