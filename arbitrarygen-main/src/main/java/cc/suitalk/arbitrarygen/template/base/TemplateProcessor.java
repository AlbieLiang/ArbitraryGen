package cc.suitalk.arbitrarygen.template.base;

import java.util.List;

import cc.suitalk.arbitrarygen.template.TemplateConfig;

/**
 * 
 * @author AlbieLiang
 *
 */
public interface TemplateProcessor {
	
	void prepare(TemplateConfig cfg);
	
	void process(String src, String destPath);
	
	void addTaskWorker(AGPsychicWorker worker);
	
	void addTaskWorker(AGPyroWorker worker);

	List<String> getSupportSuffixList();
}
