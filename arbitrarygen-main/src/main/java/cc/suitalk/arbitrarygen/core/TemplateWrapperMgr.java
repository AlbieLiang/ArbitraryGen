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

import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.extension.TemplateWrapper;
import cc.suitalk.arbitrarygen.template.RawTemplate;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TemplateWrapperMgr {

	private List<TemplateWrapper> mWrappers;
	private static TemplateWrapperMgr mgr;

	public static TemplateWrapperMgr getMgr() {
		if (mgr == null) {
			mgr = new TemplateWrapperMgr();
		}
		return mgr;
	}

	public TemplateWrapperMgr() {
		mWrappers = new LinkedList<>();
	}

	public void doWrap(ContextInfo contextInfo, RawTemplate template) {
		for (int i = 0; i < mWrappers.size(); i++) {
			TemplateWrapper p = mWrappers.get(i);
			if (p.doWrap(contextInfo, template)) {
				return;
			}
		}
	}

	public void doWrap(ContextInfo contextInfo, TypeDefineCodeBlock template) {
		for (int i = 0; i < mWrappers.size(); i++) {
			TemplateWrapper p = mWrappers.get(i);
			if (p.doWrap(contextInfo, template)) {
				return;
			}
		}
	}

	public List<TemplateWrapper> getWrappers() {
		return mWrappers;
	}

	public void addWrapper(TemplateWrapper wrapper) {
		if (wrapper != null) {
			this.mWrappers.add(0, wrapper);
		}
	}

	public void removeWrapper(TemplateWrapper wrapper) {
		mWrappers.remove(wrapper);
	}
}
