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

package cc.suitalk.arbitrarygen.core;

import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.extension.CustomizeConverter;
import cc.suitalk.arbitrarygen.template.RawTemplate;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TemplateConverterMgr {

	private List<CustomizeConverter> mConverterList;
	private static TemplateConverterMgr sMgr;

	public static TemplateConverterMgr getMgr() {
		if (sMgr == null) {
			sMgr = new TemplateConverterMgr();
		}
		return sMgr;
	}

	public TemplateConverterMgr() {
		mConverterList = new LinkedList<>();
	}

	public CustomizeConverter getFirstMatchConverter(RawTemplate rawTemplate) {
		for (int i = 0; i < mConverterList.size(); i++) {
			CustomizeConverter a = mConverterList.get(i);
			if (a.canConvert(rawTemplate)) {
				return a;
			}
		}
		return null;
	}

	public List<CustomizeConverter> getConverterList() {
		return mConverterList;
	}

	public void addConverter(CustomizeConverter converter) {
		if (converter != null) {
			this.mConverterList.add(converter);
		}
	}

	public void removeConverter(CustomizeConverter converter) {
		mConverterList.remove(converter);
	}
}
