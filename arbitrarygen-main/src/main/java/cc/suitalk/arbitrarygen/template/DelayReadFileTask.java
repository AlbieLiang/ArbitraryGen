package cc.suitalk.arbitrarygen.template;

import cc.suitalk.arbitrarygen.template.TemplateManager.DelayGetTask;
import cc.suitalk.arbitrarygen.utils.FileOperation;

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