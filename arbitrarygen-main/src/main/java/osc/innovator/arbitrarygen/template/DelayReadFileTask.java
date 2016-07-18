package osc.innovator.arbitrarygen.template;

import osc.innovator.arbitrarygen.template.TemplateManager.DelayGetTask;
import osc.innovator.arbitrarygen.utils.FileOperation;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DelayReadFileTask implements DelayGetTask {

	String path;
	
	public DelayReadFileTask(String path) {
		this.path = path;
	}
	
	@Override
	public String doGet() {
		return FileOperation.read(path);
	}
	
}