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

import cc.suitalk.arbitrarygen.base.BaseDefineCodeBlock;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BaseAGTaskWorker implements AGTaskWorker {

	private int mCommand;
	private BaseDefineCodeBlock mSourceCodeBlock;
	private BaseDefineCodeBlock mTargetCodeBlock;

	public BaseAGTaskWorker(int command) {
		this.mCommand = command;
	}

	public int getCommand() {
		return mCommand;
	}

	public void setCommand(int command) {
		this.mCommand = command;
	}

	public BaseDefineCodeBlock getSourceCodeBlock() {
		return mSourceCodeBlock;
	}

	public void setSourceCodeBlock(BaseDefineCodeBlock sourceCodeBlock) {
		this.mSourceCodeBlock = sourceCodeBlock;
	}

	public BaseDefineCodeBlock getTargetCodeBlock() {
		return mTargetCodeBlock;
	}

	public void setTargetCodeBlock(BaseDefineCodeBlock targetCodeBlock) {
		this.mTargetCodeBlock = targetCodeBlock;
	}
}
