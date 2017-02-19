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

package cc.suitalk.arbitrarygen.utils;

import java.util.Map;

import cc.suitalk.arbitrarygen.template.RawTemplate;

/**
 * 
 * @author AlbieLiang
 * 
 */
public class TemplateAttributeHelper {

	public static void setAttribute(RawTemplate template, String key,
			String value) {
		if (template == null || Util.isNullOrNil(key)) {
			return;
		}
		Map<String, String> attrs = template.getAttributes();
		if (attrs != null) {
			attrs.put(key, value);
		}
	}

	public static boolean appendAttribute(RawTemplate template, String key,
			String value) {
		return appendAttribute(template, key, value, "");
	}

	public static boolean appendAttribute(RawTemplate template, String key,
			String value, String separator) {
		if (template == null || Util.isNullOrNil(key)
				|| Util.isNullOrNil(value)) {
			return false;
		}
		Map<String, String> attrs = template.getAttributes();
		if (attrs != null) {
			String originValue = attrs.get(key);
			if (Util.isNullOrNil(originValue)) {
				originValue = value;
			} else {
				originValue += separator + value;
			}
			attrs.put(key, originValue);
		}
		return true;
	}
}
