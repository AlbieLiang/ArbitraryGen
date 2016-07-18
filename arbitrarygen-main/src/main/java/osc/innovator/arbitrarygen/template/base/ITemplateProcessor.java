package osc.innovator.arbitrarygen.template.base;

import java.util.List;

import osc.innovator.arbitrarygen.template.TemplateConfig;

/**
 * 
 * @author AlbieLiang
 *
 */
public interface ITemplateProcessor {
	
	void prepare(TemplateConfig cfg);
	
	void process(String src, String destPath);
	
	void addTaskWorkder(IGenCodeWorker worker);
	
	List<String> getSupportSuffixs();
}
