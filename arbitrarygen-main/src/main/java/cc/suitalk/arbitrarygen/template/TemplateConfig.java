/*
 *  Copyright (C) 2016-present Albie Liang. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
	
	private static final String TAG = "AG.TemplateConfig";
	
	private String templateLibs;
	private Properties properties;
	
	public TemplateConfig(String templateLibs) {
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

	public String getTemplateLibs() {
		return templateLibs;
	}
}