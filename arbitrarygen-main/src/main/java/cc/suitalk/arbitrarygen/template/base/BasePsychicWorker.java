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

package cc.suitalk.arbitrarygen.template.base;

import cc.suitalk.arbitrarygen.template.TemplateConfig;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BasePsychicWorker implements AGPsychicWorker {

	protected TemplateConfig mConfigArgs;
	
	public BasePsychicWorker(TemplateConfig cfg) {
		if (cfg == null) {
			throw new RuntimeException("Config args is null.");
		}
		setConfigArgs(cfg);
	}
	
	public BasePsychicWorker setConfigArgs(TemplateConfig cfg) {
		mConfigArgs = cfg;
		return this;
	}
}
