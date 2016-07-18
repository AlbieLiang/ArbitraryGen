package osc.innovator.arbitrarygen.template.base;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BaseTemplateProcessor implements ITemplateProcessor {

	protected List<IGenCodeWorker> mWorkers;
	protected List<String> mSupportSuffixs;
	
	public BaseTemplateProcessor() {
		mWorkers = new LinkedList<IGenCodeWorker>();
		mSupportSuffixs = new LinkedList<String>();
	}
	
	@Override
	public void addTaskWorkder(IGenCodeWorker worker) {
		mWorkers.add(worker);
		mSupportSuffixs.add(worker.getSupportSuffix());
	}

	@Override
	public List<String> getSupportSuffixs() {
		return mSupportSuffixs;
	}
}
