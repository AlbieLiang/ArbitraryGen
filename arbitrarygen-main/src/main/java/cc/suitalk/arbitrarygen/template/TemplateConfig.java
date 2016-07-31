package cc.suitalk.arbitrarygen.template;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import cc.suitalk.arbitrarygen.utils.Log;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TemplateConfig {
	
	private static final String TAG = "CodeGen.TemplateConfig";
	
	private String coreLibs;
	private String templateLibs;
	private Properties properties;
	
	public TemplateConfig(String coreLibs, String templateLibs) {
		this.coreLibs = coreLibs;
		this.templateLibs = templateLibs;

		properties = new Properties();
		FileInputStream fis = null;
		try {
			String path = this.templateLibs + "/template-mapping.properties";
			fis = new FileInputStream(path);
			Log.d(TAG, "load properties file.(%s)", path);
			properties.load(fis);
			TemplateManager mgr = TemplateManager.getImpl();
			for (Object key : properties.keySet()) {
				String templatePath = this.templateLibs + "/" +properties.getProperty((String) key, null);
				mgr.put((String) key, new DelayReadFileTask(templatePath));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getCoreLibs() {
		return coreLibs;
	}
	
	public String getTemplateLibs() {
		return templateLibs;
	}
}