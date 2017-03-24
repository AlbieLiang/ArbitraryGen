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

package cc.suitalk.arbitrarygen.rule;

/**
 * 
 * @author AlbieLiang
 *
 */
public interface RuleConstants {

	String PREFIX = "@";
	String AUTHOR = PREFIX + "Author";
	String ROOT = PREFIX + "Root";
	String PROJECT = PREFIX + "Project";
	String PROJECT_BEGIN = PREFIX + "Project-begin";
	String PROJECT_END = PREFIX + "Project-end";
	String DATE = PREFIX + "Date";
	
	String SRC = PREFIX + "Src";
	String SRC_FORMAT = PREFIX + "Src-format";
	String SRC_FILE = PREFIX + "Src-file";
	String SRC_DIR = PREFIX + "Src-dir";
	String SRC_DIR_RECURSION = PREFIX + "Src-dir-recursion";
}
