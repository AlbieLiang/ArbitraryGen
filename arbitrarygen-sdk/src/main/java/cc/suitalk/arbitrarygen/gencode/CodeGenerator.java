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

package cc.suitalk.arbitrarygen.gencode;

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.extension.CustomizeGenerator;

/**
 * 
 * @author AlbieLiang
 * 
 */
public class CodeGenerator implements CustomizeGenerator {

	public static final String LINEFEED_UNIT = "\r\n";
	private JavaFileObject mJavaFileObject;
	private String mLinefeed = LINEFEED_UNIT;

	public CodeGenerator(JavaFileObject javaFileObject) {
		if (javaFileObject == null) {
			throw new RuntimeException("JavaFileObject can not be null.");
		}
		mJavaFileObject = javaFileObject;
		mLinefeed = LINEFEED_UNIT;
	}

	@Override
	public String genCode() {
		return mJavaFileObject.genCode(mLinefeed);
	}
}
