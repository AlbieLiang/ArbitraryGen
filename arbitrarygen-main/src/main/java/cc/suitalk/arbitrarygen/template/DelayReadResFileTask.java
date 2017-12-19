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

import java.io.IOException;
import java.io.InputStream;

import cc.suitalk.arbitrarygen.template.TemplateManager.DelayGetTask;
import cc.suitalk.arbitrarygen.utils.FileOperation;

/**
 * Created by AlbieLiang on 2017/3/7.
 */
public class DelayReadResFileTask implements DelayGetTask {

	private static final String TAG = "AG.DelayReadResFileTask";

	String path;

	public DelayReadResFileTask(String path) {
		this.path = path;
	}
	
	@Override
	public String doGet() {
		InputStream is = getClass().getResourceAsStream(path);
		if (is != null) {
			String result = FileOperation.read(is);
			try {
				is.close();
			} catch (IOException e) {
			}
			return result;
		}
//		Log.i(TAG, "doGet(jarPath : %s)", getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		return "";
	}
	
}