package osc.innovator.arbitrarygen.template;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TemplateManager {

	private static TemplateManager sTemplateManager;
	
	private Map<String, TemplateWrapper> mTemplates;
	
	public synchronized static TemplateManager getImpl() {
		if (sTemplateManager == null) {
			sTemplateManager = new TemplateManager();
		}
		return sTemplateManager;
	}
	
	public TemplateManager() {
		mTemplates = new HashMap<String, TemplateWrapper>();
	}
	
	public void put(String name, String template) {
		TemplateWrapper tw = mTemplates.get(name);
		if (tw == null) {
			tw = new TemplateWrapper();
			mTemplates.put(name, tw);
		}
		tw.template = template;
	}
	
	public void put(String name, DelayGetTask task) {
		TemplateWrapper tw = mTemplates.get(name);
		if (tw == null) {
			tw = new TemplateWrapper();
			mTemplates.put(name, tw);
		}
		tw.template = null;
		tw.delayGetTask = task;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public String get(String name) {
		TemplateWrapper tw = mTemplates.get(name);
		if (tw == null) {
			return null;
		}
		if (Util.isNullOrNil(tw.template) && tw.delayGetTask != null) {
			tw.template = tw.delayGetTask.doGet();
		}
		return tw.template;
	}

	/**
	 * Get template by the given 'name', if the value do not exist
	 * then it with invoke {@link DelayGetTask#doGet()} to get value
	 * and update the template immediately.
	 * 
	 * @param name
	 * @param task
	 * @return
	 */
	public String get(String name, DelayGetTask task) {
		TemplateWrapper tw = mTemplates.get(name);
		if (tw == null) {
			if (task != null) {
				put(name, task);
				return get(name);
			}
			return null;
		}
		if (Util.isNullOrNil(tw.template) && task != null) {
			tw.template = task.doGet();
		}
		return tw.template;
	}
	
	public String remove(String name) {
		TemplateWrapper tw = mTemplates.remove(name);
		if (tw != null) {
			if (Util.isNullOrNil(tw.template) && tw.delayGetTask != null) {
				tw.template = tw.delayGetTask.doGet();
			}
			return tw.template;
		}
		return null;
	}
	
	public void putAll(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return;
		}
		for (Entry<String, String> entry : map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 
	 * @author AlbieLiang
	 *
	 */
	public static interface DelayGetTask {
		String doGet();
	}
	
	/**
	 * 
	 * @author AlbieLiang
	 *
	 */
	private static class TemplateWrapper {
		String template;
		DelayGetTask delayGetTask;
	}
	
}
