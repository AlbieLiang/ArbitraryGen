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

package cc.suitalk.arbitrarygen.demo.event;

/**
 * 
 * @author AlbieLiang
 *
 */
public class MagicClassLoader extends ClassLoader {

	@Override
	protected Class<?> findClass(String className)
			throws ClassNotFoundException {
		Class<?> clazz = super.findClass(className);
		if (clazz != null) {
			MagicTest test = clazz.getAnnotation(MagicTest.class);
			if (test != null) {
				
			}
		}
		return clazz;
	}

	@Override
	public Class<?> loadClass(String className) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return super.loadClass(className);
	}

	@Override
	protected Class<?> loadClass(String className, boolean resolve)
			throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return super.loadClass(className, resolve);
	}

}
