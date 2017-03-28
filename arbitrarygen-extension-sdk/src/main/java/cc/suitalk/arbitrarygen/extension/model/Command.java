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

package cc.suitalk.arbitrarygen.extension.model;

/**
 * 
 * @author AlbieLiang
 *
 */
public interface Command {
	
	interface Type {
		int COMMAND_DO_NOTHING = 0;
		int COMMAND_EXTRACT_VIEW_ID = 1;
		int COMMAND_RUN_IN_MAIN_THREAD = 2;
		int COMMAND_RUN_IN_WORKER_THREAD = 3;
		int COMMAND_KEEP = 4;
		int COMMAND_RENAME_CLASS = 5;
	}
	
	interface Status {
		int NORMAL = 0;
		int FINISHED = 1;
		int NEED_UPDATE = 2;
		int IGNORE = 3;
	}
	
	interface Location {
		int INSIDE = 0;
		int BEFORE = 1;
		int AFTER = 2;
		int REPLACE = 3;
	}
}