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

package cc.suitalk.arbitrarygen.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.gencode.GenCodeTaskInfo;
import cc.suitalk.arbitrarygen.gencode.SourceFileInfo;

/**
 * 
 * @author AlbieLiang
 *
 */
public class FileOperation {

	private static final String TAG = "AG.FileOperation";

	public static List<File> listFiles(File file, boolean recursion) {
		List<File> resultList = new LinkedList<>();
		File[] files = file.listFiles();
		if (files != null && files.length > 0) {
			for (File f : files) {
				if (f.isFile()) {
					resultList.add(f);
				} else if (f.isDirectory() && recursion) {
					resultList.addAll(listFiles(f, true));
				}
			}
		}
		return resultList;
	}

	public static List<String> listFilePaths(File file, boolean recursion) {
		List<String> resultList = new LinkedList<>();
		File[] files = file.listFiles();
		if (files != null && files.length > 0) {
			for (File f : files) {
				if (f.isFile()) {
					resultList.add(f.getAbsolutePath());
				} else if (f.isDirectory() && recursion) {
					resultList.addAll(listFilePaths(f, true));
				}
			}
		}
		return resultList;
	}
	public static final List<SourceFileInfo> scan(String path, List<String> suffixList) {
		if (Util.isNullOrNil(path) || suffixList == null || suffixList.size() == 0) {
			Log.d(TAG, "path : %s, suffixList is null = %s", path, (suffixList == null));
			return null;
		}
		return scan(new File(path), suffixList);
	}

	private static final List<SourceFileInfo> scan(File file, List<String> suffixList) {
		if (file == null || !file.exists() || suffixList == null || suffixList.size() == 0) {
			Log.d(TAG, "file : " + file + ", exists = " + (file != null ? file.exists() : false));
			return null;
		}
		List<SourceFileInfo> results = new LinkedList<>();
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files == null || files.length == 0) {
				return null;
			}
			for (int i = 0; i < files.length; i++) {
				results.addAll(scan(files[i], suffixList));
			}
		} else {
			String suffix = Util.getSuffix(file.getName());
			if (!Util.isNullOrNil(suffix) && suffixList.contains(suffix)) {
				results.add(new SourceFileInfo(suffix, file));
			}
		}
		return results;
	}

	public static void saveToFile(GenCodeTaskInfo info, String code) {
		FileWriter fw = null;
		try {
			String fileName = info.RootDir + File.separator + info.FileName + "." + info.Suffix;
			File f = new File(fileName);
			Util.mkdirsIfNeed(f.getParentFile());
			fw = new FileWriter(f);
			fw.write(code);
			fw.flush();
			Log.i(TAG, "Gen file : " + fileName + " Successfully.");
		} catch (IOException e) {
			Log.e(TAG, "exception occurred : %s", Log.getStackTraceString(e));
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					Log.e(TAG, "exception occurred : %s", Log.getStackTraceString(e));
				}
			}
		}
	}
	
	public static void write(String fileName, String code) {
		FileWriter fw = null;
		try {
			File f = new File(fileName);
			Util.mkdirsIfNeed(f.getParentFile());
			fw = new FileWriter(f);
			fw.write(code);
			fw.flush();
			Log.i(TAG, "Gen file : " + fileName + " Successfully.");
		} catch (IOException e) {
			Log.e(TAG, "exception occurred : %s", Log.getStackTraceString(e));
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					Log.e(TAG, "exception occurred : %s", Log.getStackTraceString(e));
				}
			}
		}
	}
	
	public static String read(String file) {
		File f = new File(file);
		if (!f.isFile() || !f.exists()) {
			return "";
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
			return read(fis);
		} catch (FileNotFoundException e) {
			Log.e(TAG, "exception occurred : %s", Log.getStackTraceString(e));
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					Log.e(TAG, "exception occurred : %s", Log.getStackTraceString(e));
				}
			}
		}
		return "";
	}

	public static String read(InputStream fis) {
		if (fis == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		byte[] buf = new byte[1024];
		int size = 0;
		try {
			while ((size = fis.read(buf)) != -1) {
				sb.append(new String(buf, 0, size));
			}
		} catch (FileNotFoundException e) {
			Log.e(TAG, "exception occurred : %s", Log.getStackTraceString(e));
		} catch (IOException e) {
			Log.e(TAG, "exception occurred : %s", Log.getStackTraceString(e));
		} finally {
		}
		return sb.toString();
	}

	public static String getSuffix(String fileName) {
		int index = -1;
		return Util.isNullOrNil(fileName) || (index = fileName.indexOf(".")) < 0 ? "" : fileName.substring(index + 1);
	}

	public static String getNameWithoutSuffix(String fileName) {
		int index = -1;
		return Util.isNullOrNil(fileName) || (index = fileName.indexOf(".")) < 0 ? fileName : fileName.substring(0, index);
	}
}
