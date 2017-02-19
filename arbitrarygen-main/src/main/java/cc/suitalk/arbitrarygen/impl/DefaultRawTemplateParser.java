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

package cc.suitalk.arbitrarygen.impl;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.extension.CustomizeParser;
import cc.suitalk.arbitrarygen.template.RawTemplate;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.XmlUtils;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DefaultRawTemplateParser implements CustomizeParser {

	private static final String TAG = "AG.DefaultRawTemplateParser";

	private List<String> mSuffixList;

	public DefaultRawTemplateParser() {
		mSuffixList = new LinkedList<>();
	}

	@Override
	public List<RawTemplate> parse(File file) {
		if (file == null || !file.exists() || !file.isFile()) {
			Log.e(TAG, "The file is null or do not exists or isn't a file.");
			return null;
		}
		return XmlUtils.getXmlNotes(file);
	}

	@Override
	public boolean canParse(String suffix) {
		return suffix!= null && mSuffixList.contains(suffix.toLowerCase());
	}

	public void addSuffixList(List<String> suffixList) {
		if (suffixList == null || suffixList.isEmpty()) {
			return;
		}
		for (String suffix : suffixList) {
			addSuffix(suffix);
		}
	}

	public void addSuffix(String suffix) {
		if (suffix == null || suffix.length() == 0) {
			return;
		}
		mSuffixList.add(suffix.toLowerCase());
	}
}
