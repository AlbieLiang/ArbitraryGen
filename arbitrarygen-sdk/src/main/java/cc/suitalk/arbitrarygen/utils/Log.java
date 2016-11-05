package cc.suitalk.arbitrarygen.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
		sb.append("[msec:").append(msec).append("]");
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
