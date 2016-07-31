package cc.suitalk.arbitrarygen.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.core.GenCodeTaskInfo;
import cc.suitalk.arbitrarygen.core.SourceFileInfo;

/**
 * 
 * @author AlbieLiang
 *
 */
public class FileOperation {

	private static final String TAG = "CodeGen.FileOperation";

	public static final List<SourceFileInfo> scan(String path, List<String> suffixs) {
		if (Util.isNullOrNil(path) || suffixs == null || suffixs.size() == 0) {
			Log.d(TAG, "path : " + path + ", suffixs is null = " + (suffixs == null));
			return null;
		}
		return scan(new File(path), suffixs);
	}

	private static final List<SourceFileInfo> scan(File file, List<String> suffixs) {
		if (file == null || !file.exists() || suffixs == null || suffixs.size() == 0) {
			Log.d(TAG, "file : " + file + ", exists = " + (file != null ? file.exists() : false));
			return null;
		}
		List<SourceFileInfo> results = new LinkedList<SourceFileInfo>();
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files == null || files.length == 0) {
				return null;
			}
			for (int i = 0; i < files.length; i++) {
				results.addAll(scan(files[i], suffixs));
			}
		} else {
			String suffix = Util.getSuffix(file.getName());
			if (!Util.isNullOrNil(suffix) && suffixs.contains(suffix)) {
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
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
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
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
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
		StringBuffer sb = new StringBuffer();
		byte[] buf = new byte[1024];
		int size = 0;
		try {
			fis = new FileInputStream(f);
			while ((size = fis.read(buf)) != -1) {
				sb.append(new String(buf, 0, size));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
}
