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

import java.util.Map;

import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ContextInfo;
import cc.suitalk.arbitrarygen.core.TemplateConstants;
import cc.suitalk.arbitrarygen.extension.CustomizeConverter;
import cc.suitalk.arbitrarygen.template.RawTemplate;
import cc.suitalk.arbitrarygen.utils.AnalyzerHelper;

/**
 * 
 * @author albieliang
 * 
 */
public class ExternalTemplateConverter implements CustomizeConverter {

	private static final String TAG_NAME = "Ext";

	public ExternalTemplateConverter() {
	}

	@Override
	public TypeDefineCodeBlock convert(ContextInfo contextInfo, RawTemplate rawTemplate) {
		if (rawTemplate == null) {
			return null;
		}
		doAttachDefaultValues(rawTemplate);
		return AnalyzerHelper.createTypeDefineCodeBlock(rawTemplate);
	}

	private void doAttachDefaultValues(RawTemplate rawTemplate) {
		Map<String, String> attrs = rawTemplate.getAttributes();
		String name = attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_NAME);
		attrs.put(TemplateConstants.TEMPLATE_KEYWORDS_NAME, name + TAG_NAME);
		attrs.put(TemplateConstants.TEMPLATE_KEYWORDS_PARENT, "Object");
	}

	@Override
	public boolean canConvert(RawTemplate template) {
		return template != null && TAG_NAME.equalsIgnoreCase(template.getName());
	}
}
