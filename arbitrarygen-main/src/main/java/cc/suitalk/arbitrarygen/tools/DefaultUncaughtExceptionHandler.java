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

package cc.suitalk.arbitrarygen.tools;

import java.lang.Thread.UncaughtExceptionHandler;

import cc.suitalk.arbitrarygen.utils.Log;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DefaultUncaughtExceptionHandler implements UncaughtExceptionHandler {

	private static final String TAG = "Crash.DefaultUncaughtExceptionHandler";


	public DefaultUncaughtExceptionHandler() {
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (thread == null || ex == null) {
			return;
		}
		Log.e(TAG, ex.toString());
		StackTraceElement[] trace = ex.getStackTrace();
		for (StackTraceElement traceElement : trace) {
			Log.e(TAG, "\tat " + traceElement);
		}
	}

}
