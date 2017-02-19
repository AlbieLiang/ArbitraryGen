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

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ConfigInfo;
import cc.suitalk.arbitrarygen.extension.AGTaskWorker;
import cc.suitalk.arbitrarygen.extension.TypeDefineWrapper;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TypeDefineWrapperImpl implements TypeDefineWrapper {

	@Override
	public boolean doWrap(ConfigInfo configInfo, JavaFileObject fileObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doWrap(ConfigInfo configInfo, TypeDefineCodeBlock codeblock) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addIAGTaskWorker(AGTaskWorker worker) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeIAGTaskWorker(AGTaskWorker worker) {
		// TODO Auto-generated method stub
		return false;
	}

}
