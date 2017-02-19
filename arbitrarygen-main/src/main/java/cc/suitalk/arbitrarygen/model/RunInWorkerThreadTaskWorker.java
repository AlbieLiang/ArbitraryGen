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

package cc.suitalk.arbitrarygen.model;

import java.util.Map;

import cc.suitalk.arbitrarygen.core.ConfigInfo;
import cc.suitalk.arbitrarygen.extension.model.Command;
import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.extension.BaseAGTaskWorker;

/**
 * 
 * @author AlbieLiang
 *
 */
public class RunInWorkerThreadTaskWorker extends BaseAGTaskWorker {

	public RunInWorkerThreadTaskWorker() {
		super(Command.Type.COMMAND_RUN_IN_WORKER_THREAD);
	}

	@Override
	public boolean doTask(ConfigInfo configInfo, ArbitraryGenTaskInfo task, JavaFileObject fileObject, Map<String, ArbitraryGenTaskInfo> srcGenTasks, Map<String, ArbitraryGenTaskInfo> targetTasks) {
		// TODO Auto-generated method stub
		return false;
	}

}
