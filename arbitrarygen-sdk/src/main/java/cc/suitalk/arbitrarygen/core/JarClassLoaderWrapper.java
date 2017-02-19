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

package cc.suitalk.arbitrarygen.core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author AlbieLiang
 *
 */
public class JarClassLoaderWrapper {

	private List<File> mLoadedJarFileList;
	private List<File> mNotLoadJarFileList;
	private URLClassLoader mLoader;

	public JarClassLoaderWrapper() {
		mLoadedJarFileList = new LinkedList<>();
		mNotLoadJarFileList = new LinkedList<>();
	}

	public Class<?> loadClass(String className) throws ClassNotFoundException,
			MalformedURLException {
		URLClassLoader loader = getClassLoader();
		if (loader != null) {
			return loader.loadClass(className);
		}
		return null;
	}

	private URLClassLoader getClassLoader() throws MalformedURLException {
		URL[] urls = new URL[0];
		if (!mNotLoadJarFileList.isEmpty()) {
			List<File> list = new LinkedList<>(mNotLoadJarFileList);
			mNotLoadJarFileList.clear();
			mLoadedJarFileList.addAll(list);
			urls = new URL[list.size()];
			for (int i = 0; i < urls.length; i++) {
				urls[i] = list.get(i).toURI().toURL();
			}
		}
		if (mLoader != null) {
			mLoader = new URLClassLoader(urls, mLoader);
		} else {
			mLoader = new URLClassLoader(urls);
		}
		return mLoader;
	}

	/**
	 * The method will add external Jar file into ClassLoader.
	 * 
	 * @param file adding file
	 * @return true means add the jar file success, otherwise false
	 */
	public boolean addJar(File file) {
		if (file != null && file.exists() && file.isFile() && !contains(file)) {
			mNotLoadJarFileList.add(file);
			return true;
		}
		return false;
	}

	/**
	 * The method will work only before the method
	 * {@link #loadClass(String className)} was invoked.
	 * 
	 * @param file removing file
	 */
	public void removeJar(File file) {
		mNotLoadJarFileList.remove(file);
	}

	public boolean contains(File file) {
		if (file != null) {
			for (int i = 0; i < mLoadedJarFileList.size(); i++) {
				File f = mLoadedJarFileList.get(i);
				if (f == file || f.getAbsolutePath().equals(file.getAbsolutePath())) {
					return true;
				}
			}
			for (int i = 0; i < mNotLoadJarFileList.size(); i++) {
				File f = mNotLoadJarFileList.get(i);
				if (f == file || f.getAbsolutePath().equals(file.getAbsolutePath())) {
					return true;
				}
			}
		}
		return false;
	}
}
