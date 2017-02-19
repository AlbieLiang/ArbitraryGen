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

package cc.suitalk.tools.arbitrarygen.demo.greendao;

import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ContextInfo;
import cc.suitalk.arbitrarygen.extension.CustomizeConverter;
import cc.suitalk.arbitrarygen.template.RawTemplate;
import cc.suitalk.arbitrarygen.utils.Log;

/**
 * 
 * @author AlbieLiang
 * 
 */
public class GreenDaoConverter implements CustomizeConverter {

	private static final String TAG = "GreenDao.GreenDaoConverter";

	private static final String TAG_NAME = "table";

	public GreenDaoConverter() {
	}

	@Override
	public TypeDefineCodeBlock convert(ContextInfo contextInfo, RawTemplate rawTemplate) {
		if (rawTemplate == null) {
			return null;
		}
		TypeDefineCodeBlock typeDefineCodeBlock = DatabaseGenerator.doGen(contextInfo, rawTemplate);
		Log.d(TAG, "do generate green dao code.");
		return typeDefineCodeBlock;
	}

	@Override
	public boolean canConvert(RawTemplate template) {
		return template != null && TAG_NAME.equalsIgnoreCase(template.getName());
	}
}
