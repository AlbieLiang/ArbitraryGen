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

package cc.suitalk.arbitrarygen.extension;

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ConfigInfo;

/**
 * 
 * @author AlbieLiang
 *
 */
public interface TypeDefineWrapper {

	/**
	 * To wrap the {@link JavaFileObject}
	 *
	 * @param context current context {@link AGContext}
	 * @param configInfo current config {@link ConfigInfo}
	 * @param fileObject current {@link JavaFileObject} object
	 * @return true to interrupt the wrap action that means do not need to wrap
	 *         any more, otherwise false
	 */
	boolean doWrap(AGContext context, ConfigInfo configInfo, JavaFileObject fileObject);

	/**
	 * To wrap the {@link TypeDefineCodeBlock}
	 *
	 * @param context current context {@link AGContext}
	 * @param configInfo current config {@link ConfigInfo}
	 * @param codeblock current {@link TypeDefineCodeBlock} object
	 * @return true to interrupt the wrap action that means do not need to wrap
	 *         any more, otherwise false
	 */
	boolean doWrap(AGContext context, ConfigInfo configInfo, TypeDefineCodeBlock codeblock);
	
	/**
	 * Add a {@link AGTaskWorker} to the worker set.
	 * 
	 * @param worker the {@link AGTaskWorker} for {@link TypeDefineWrapper}
	 * @return added or not
	 */
	boolean addIAGTaskWorker(AGTaskWorker worker);
	
	/**
	 * Remove a {@link AGTaskWorker} from the worker set.
	 * 
	 * @param worker the {@link AGTaskWorker} added before
	 * @return removed success or not
	 */
	boolean removeIAGTaskWorker(AGTaskWorker worker);
}
