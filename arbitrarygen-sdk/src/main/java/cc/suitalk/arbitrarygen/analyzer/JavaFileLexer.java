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

package cc.suitalk.arbitrarygen.analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.utils.Log;

/**
 * 
 * @author AlbieLiang
 *
 */
public class JavaFileLexer extends JavaLexer {

	private static final String TAG = "AG.JavaFileLexer";

	private File mFile;
	
	public JavaFileLexer(File file) {
		super();
		if (file == null || !file.exists() || !file.isFile()) {
			throw new RuntimeException("file is null or do not exists or isn't a file.");
		}
		mFile = file;
	}

	public JavaFileObject start() {
		IReader reader = null;
		JavaFileObject javaFileObject = null;
		try {
			Log.v(TAG, "start to analyze file(%s)", mFile.getAbsolutePath());
			reader = new ReaderWrapper(new InputStreamReader(new FileInputStream(mFile)));
			javaFileObject = analyzeJavaFileObject(reader);
		} catch (Exception e) {
			Log.e(TAG, "analyze java file error, exception : %s", Log.getStackTraceString(e));
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					Log.e(TAG, "close reader error, exception : %s", Log.getStackTraceString(e));
				}
			}
		}
		return javaFileObject;
	}
}