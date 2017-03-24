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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import cc.suitalk.arbitrarygen.debug.Debuger;

/**
 * 
 * @author AlbieLiang
 *
 */
public class Log {

	public static final String CHARSET = "UTF-8";
	
	public static final int LOG_LEVEL_V = 1;
	public static final int LOG_LEVEL_D = 2;
	public static final int LOG_LEVEL_I = 3;
	public static final int LOG_LEVEL_W = 4;
	public static final int LOG_LEVEL_E = 5;
	public static final int LOG_LEVEL_A = 6;
	public static final int LOG_LEVEL_N = 7;
	
	private static int level = 0;
	private static boolean printTag = false;
	private static boolean printLevel = false;
	private static String path;
	private static FileOutputStream fos;
	
	public static final void setPrintLogLevel(int level) {
		Log.level = level;
	}
	
	public static final void setPrintTag(boolean printTag) {
		Log.printTag = printTag; 
	}
	
	public static final void setPrintLevel(boolean printLevel) {
		Log.printLevel = printLevel; 
	}
	
	public static final void setPath(String path) {
		Log.path = path; 
	}
	
	public static final void v(String tag, String format, Object... args) {
		printLog(LOG_LEVEL_V, "v", tag, format(format, args));
	}
	
	public static final void v(String tag, String msg) {
		printLog(LOG_LEVEL_V, "v", tag, msg);
	}

	public static final void d(String tag, String format, Object... args) {
		printLog(LOG_LEVEL_D, "d", tag, format(format, args));
	}
	
	public static final void d(String tag, String msg) {
		printLog(LOG_LEVEL_D, "d", tag, msg);
	}
	
	public static final void i(String tag, String format, Object... args) {
		printLog(LOG_LEVEL_I, "i", tag, format(format, args));
	}

	public static final void i(String tag, String msg) {
		printLog(LOG_LEVEL_I, "i", tag, msg);
	}

	public static final void w(String tag, String format, Object... args) {
		printLog(LOG_LEVEL_W, "w", tag, format(format, args));
	}
	
	public static final void w(String tag, String msg) {
		printLog(LOG_LEVEL_W, "w", tag, msg);
	}

	public static final void e(String tag, String format, Object... args) {
		printLog(LOG_LEVEL_E, "e", tag, format(format, args));
	}
	
	public static final void e(String tag, String msg) {
		printLog(LOG_LEVEL_E, "e", tag, msg);
	}
	
	public static final void a(String tag, String msg) {
		printLog(LOG_LEVEL_A, "a", tag, msg);
	}
	
	public static final void a(String tag, String format, Object... args) {
		printLog(LOG_LEVEL_A, "a", tag, format(format, args));
	}

	public static String getStackTraceString(Throwable tr) {
		if (tr == null) {
			return "";
		}
		// This is to reduce the amount of log spew that apps do in the non-error
		// condition of the network being unavailable.
		Throwable t = tr;
		while (t != null) {
			if (t instanceof UnknownHostException) {
				return "";
			}
			t = t.getCause();
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		tr.printStackTrace(pw);
		pw.flush();
		return sw.toString();
	}

	private static final void printLog(int level, String levelStr, String tag, String msg) {
		if (Debuger.debug || level >= Log.level) {
			System.out.println(getMsg(levelStr, tag, msg));
		}
		writeLog(level, levelStr, tag, msg);
	}
	
	private static final String getMsg(String levelStr, String tag, String msg) {
		StringBuilder builder = new StringBuilder();
		if (printTag) {
			builder.append("[");
			if (printLevel) {
				builder.append(levelStr);
				builder.append(":");
			}
			builder.append(tag);
			builder.append("]");
		}
		builder.append(msg);
		return builder.toString();
	}
	
	public static String format(String format, Object... args) {
		String msg = format;
		if (!Util.isNullOrNil(format)) {
			msg = (args == null || args.length == 0) ? format : String.format(format, args);
		}
		return msg;
	}
	
	public static boolean prepare() {
		if (Util.isNullOrNil(path)) {
			return false;
		}
		File file = new File(path);
		Util.createFileIfNeed(file);
		try {
			fos = new FileOutputStream(file, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static void close() {
		if (fos != null) {
			try {
				fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fos = null;
		}
	}
	
	private static void writeLog(int level, String levelStr, String tag, String msg) {
		if (fos == null || Util.isNullOrNil(msg)) {
			return;
		}
		String log = getMsg(levelStr, tag, msg);
		
		long msec = System.currentTimeMillis();
		
		StringBuffer sb = new StringBuffer();
		sb.append("[tid:").append(Thread.currentThread().getId()).append("]");
		sb.append("[time:").append(Util.getDateFormat("Z yyyy-MM-dd HH:mm:ss.SSS", msec)).append("]");
		sb.append(log);
		sb.append("\n");
		byte[] bytes = null;
		try {
			bytes = sb.toString().getBytes(CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (bytes != null && bytes.length > 0) {
			try {
				fos.write(bytes);
				fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
