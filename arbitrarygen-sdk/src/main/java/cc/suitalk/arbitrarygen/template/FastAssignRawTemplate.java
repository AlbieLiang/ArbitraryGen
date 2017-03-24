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

import cc.suitalk.arbitrarygen.core.KeyWords;
import cc.suitalk.arbitrarygen.core.TemplateConstants;
import cc.suitalk.arbitrarygen.utils.TemplateAttributeHelper;

/**
 * 
 * @author AlbieLiang
 *
 */
public class FastAssignRawTemplate extends RawTemplate {

	public FastAssignRawTemplate() {
	}

	public FastAssignRawTemplate(String type, String name) {
		setAttrModifier(KeyWords.V_JAVA_KEYWORDS_PUBLIC);
		setAttrType(type);
		setAttrName(name);
	}

	public FastAssignRawTemplate(String modifier, boolean isStatic,
			boolean isFinal, String type, String name) {
		setAttrModifier(modifier);
		setAttrIsStatic(isStatic);
		setAttrIsFinal(isFinal);
		setAttrType(type);
		setAttrName(name);
	}

	public FastAssignRawTemplate setAttrDefault(String value) {
		TemplateAttributeHelper.setAttribute(this,
				TemplateConstants.TEMPLATE_KEYWORDS_DEFAULT, value);
		return this;
	}

	public FastAssignRawTemplate setAttrName(String name) {
		TemplateAttributeHelper.setAttribute(this,
				TemplateConstants.TEMPLATE_KEYWORDS_NAME, name);
		return this;
	}

	public FastAssignRawTemplate setAttrType(String type) {
		TemplateAttributeHelper.setAttribute(this,
				TemplateConstants.TEMPLATE_KEYWORDS_TYPE, type);
		return this;
	}

	public FastAssignRawTemplate setAttrModifier(String modifier) {
		TemplateAttributeHelper.setAttribute(this,
				TemplateConstants.TEMPLATE_KEYWORDS_MODIFIER, modifier);
		return this;
	}

	public FastAssignRawTemplate setAttrIsFinal(boolean isFinal) {
		TemplateAttributeHelper.setAttribute(this,
				TemplateConstants.TEMPLATE_KEYWORDS_IS_FINAL,
				isFinal ? KeyWords.V_JAVA_KEYWORDS_TRUE
						: KeyWords.V_JAVA_KEYWORDS_FALSE);
		return this;
	}

	public FastAssignRawTemplate setAttrIsStatic(boolean isStatic) {
		TemplateAttributeHelper.setAttribute(this,
				TemplateConstants.TEMPLATE_KEYWORDS_IS_STATIC,
				isStatic ? KeyWords.V_JAVA_KEYWORDS_TRUE
						: KeyWords.V_JAVA_KEYWORDS_FALSE);
		return this;
	}
}
