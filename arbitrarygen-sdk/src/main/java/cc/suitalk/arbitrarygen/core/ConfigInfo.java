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

/**
 * 
 * @author AlbieLiang
 *
 */
public class ConfigInfo {
//	private String mRootDir;
//	private String mDestDir;
//	private String mDestFolder;
//	private String mSrcFolder;
//	private String mSrcDir;

	
	private String mRootPath;
	private String mDestPath;
	private String mSrcPath;
	private File mFile;

	public ConfigInfo() {
		mRootPath = System.getProperty("user.dir");
		setDestPath(mRootPath + "/ag-gen");
		setSrcPath(mRootPath + "/ag-datasrc");
	}

//	public void setDestFolder(String destFolder) {
//		if (!Util.isNullOrNil(destFolder)) {
//			mDestFolder = destFolder;
//			mDestDir = mRootDir + File.separator + mDestFolder;
//		}
//	}
//
//	public void setSrcFolder(String srcFolder) {
//		if (!Util.isNullOrNil(srcFolder)) {
//			mSrcFolder = srcFolder;
//			mSrcDir = mRootDir + File.separator + mSrcFolder;
//		}
//	}

//	public String getDestDir() {
//		return mDestDir;
//	}
//
//	public String getSrcDir() {
//		return mSrcDir;
//	}
	
	public String getDestPath() {
		return mDestPath;
	}

	public void setDestPath(String destPath) {
		mDestPath = destPath;
	}

	public String getSrcPath() {
		return mSrcPath;
	}
	
	public void setSrcPath(String srcPath) {
		mSrcPath = srcPath;
	}
	
	public String getRootPath() {
		return mRootPath;
	}

	public File getFile() {
		return mFile;
	}

	public void setFile(File file) {
		mFile = file;
	}
}
