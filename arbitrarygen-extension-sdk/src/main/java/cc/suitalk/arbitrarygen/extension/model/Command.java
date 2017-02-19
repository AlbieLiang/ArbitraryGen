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
public final class Command {
	
	public static final class Type {
		public static final int COMMAND_DO_NOTHING = 0;
		public static final int COMMAND_EXTRACT_VIEW_ID = 1;
		public static final int COMMAND_RUN_IN_MAIN_THREAD = 2;
		public static final int COMMAND_RUN_IN_WORKER_THREAD = 3;
		public static final int COMMAND_KEEP = 4;
		public static final int COMMAND_RENAME_CLASS = 5;
	}
	
	public static final class Status {
		public static final int NORMAL = 0;
		public static final int FINISHED = 1;
		public static final int NEED_UPDATE = 2;
		public static final int IGNORE = 3;
	}
	
	public static final class Location {
		public static final int INSIDE = 0;
		public static final int BEFORE = 1;
		public static final int AFTER = 2;
		public static final int REPLACE = 3;
	}
}