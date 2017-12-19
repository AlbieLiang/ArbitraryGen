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

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.extension.AGContext;
import cc.suitalk.arbitrarygen.extension.TypeDefineWrapper;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TypeDefineWrapperMgr {

	private static TypeDefineWrapperMgr sMgr;

	private List<TypeDefineWrapper> mWrapperList;

	public static TypeDefineWrapperMgr getMgr() {
		if (sMgr == null) {
			sMgr = new TypeDefineWrapperMgr();
		}
		return sMgr;
	}

	public TypeDefineWrapperMgr() {
		mWrapperList = new LinkedList<>();
	}

	public void doWrap(AGContext context, ConfigInfo configInfo, JavaFileObject fileObject) {
		for (int i = 0; i < mWrapperList.size(); i++) {
			TypeDefineWrapper p = mWrapperList.get(i);
			// TODO
			if (p.doWrap(context, configInfo, fileObject)) {
//				return;
			}
		}
	}

	public void doWrap(AGContext context, ConfigInfo configInfo, TypeDefineCodeBlock codeblock) {
		for (int i = 0; i < mWrapperList.size(); i++) {
			TypeDefineWrapper p = mWrapperList.get(i);
			if (p.doWrap(context, configInfo, codeblock)) {
				return;
			}
		}
	}

	public List<TypeDefineWrapper> getWrapperList() {
		return mWrapperList;
	}

	public void addWrapper(TypeDefineWrapper wrapper) {
		if (wrapper != null) {
			this.mWrapperList.add(0, wrapper);
		}
	}

	public void removeWrapper(TypeDefineWrapper wrapper) {
		mWrapperList.remove(wrapper);
	}
}
