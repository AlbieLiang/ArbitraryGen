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

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BaseTemplateProcessor implements TemplateProcessor {

	protected List<PsychicGenerator> mWorkers;
	protected List<String> mSupportSuffixList;
	
	public BaseTemplateProcessor() {
		mWorkers = new LinkedList<>();
		mSupportSuffixList = new LinkedList<>();
	}
	
	@Override
	public void addTaskWorker(AGPsychicWorker worker) {
		mWorkers.add(worker);
		mSupportSuffixList.add(worker.getSupportSuffix());
	}

	@Override
	public void addTaskWorker(AGPyroWorker worker) {
		mWorkers.add(worker);
		mSupportSuffixList.addAll(worker.getSupportSuffixList());
	}

	@Override
	public List<String> getSupportSuffixList() {
		return mSupportSuffixList;
	}
}
