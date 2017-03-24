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

package cc.suitalk.tools.arbitrarygen.demo;

import java.io.File;
import java.util.List;

import cc.suitalk.arbitrarygen.extension.CustomizeParser;
import cc.suitalk.arbitrarygen.template.RawTemplate;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.XmlUtils;

public class ExternalTemplateParser  implements CustomizeParser {

	private static final String TAG = "AG.demo.ExternalTemplateParser";

	@Override
	public boolean canParse(String suffix) {
		return "ext".equalsIgnoreCase(suffix);
	}

	@Override
	public List<RawTemplate> parse(File file) {
		if (file == null || !file.exists() || !file.isFile()) {
			Log.e(TAG, "The file is null or do not exists or isn't a file.");
			return null;
		}
		return XmlUtils.getXmlNotes(file);
	}

}
