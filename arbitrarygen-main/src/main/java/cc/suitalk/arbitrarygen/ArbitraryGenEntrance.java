package cc.suitalk.arbitrarygen;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.suitalk.arbitrarygen.core.ArbitraryGenCore;
import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.debug.Debuger;
import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.tools.DefaultUncaughtExceptionHandler;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * The entrance of the code generator.
 * 
 * @author AlbieLiang
 * 
 */
public class ArbitraryGenEntrance {

	private static final String TAG = "AG.ArbitraryGenEntrance";

	private static final void printArgs(String[] args) {
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				Log.i(TAG, "arg[" + i + "] = " + args[i]);
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Crash handler
		Thread.setDefaultUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler());
		//
		Map<String, String> argsKvPair = ExternalArgsParser.extractArgs(args);

		String enableArg = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_ENABLE);
		boolean enable = Util.isNullOrNil(enableArg) ? true : Boolean.parseBoolean(enableArg);
		if (!enable) {
			return;
		}
		// For new engine framework
		AGCore core = new ArbitraryGenCore();
		JSONObject jsonObject = null;
		try {
			String json = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_ARG_JSON);
			jsonObject = JSONObject.fromObject(json);
		} catch (Exception e) {
			Log.e(TAG, "getArgJson error : %s", e);
		}
		if (jsonObject != null) {
			core.initialize(jsonObject);
			Log.i(TAG, "\n\n\n\n\n\n\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>begin<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n\n\n\n\n");
			if (Debuger.debug) {
				printArgs(args);
				// TODO: 16/11/6 albieliang, add doAction feature into AGEngine, and then it can get arguments by this interface
				Log.v(TAG, "argJson : %s", jsonObject);
			}
			core.start();
			Log.i(TAG, "\n\n\n\n\n\n\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>end<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n\n\n\n\n");
		}
		Log.close();
//		if (configInfo != null) {
//			Runtime runtime = Runtime.getRuntime();
//	//		String command = String.format("protoc --java_out=%s %s", "./" + argsKvPair.get("dest"), argsKvPair.get("src") + "/entity.proto");
//			String command = String.format("protoc --java_out=%s %s", configInfo.getDestPath(), configInfo.getSrcPath() + "/entity.proto");
//			Log.i(TAG, command);
//			try {
//				runtime.exec(command);
//			} catch (IOException e) {
//				Log.e(TAG, "exec protoc error : %s", e);
//			}
//		}
	}

	/**
	 * 
	 * @author AlbieLiang
	 *
	 */
	public static final class ExternalArgsParser {

		public static final String ARGS_SEPARATOR = ":";
		public static final String ARGS_LIST_SEPARATOR = ",";

		public static Map<String, String> extractArgs(String[] args) {
			Map<String, String> kvPair = new HashMap<>();
			if (args != null && args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					if (!Util.isNullOrNil(args[i])) {
						int index = args[i].indexOf(ARGS_SEPARATOR);
						if (index <= 0) {
							continue;
						}
						String key = args[i].substring(0, index);
						String value = index + 1 == args[i].length() ? "" : args[i].substring(index + 1, args[i].length());
						kvPair.put(key, value);
					}
				}
			}
			return kvPair;
		}

		public static List<String> extractList(String[] args, String prefix) {
			if (args == null || args.length == 0 || Util.isNullOrNil(prefix)) {
				return null;
			}
			List<String> results = new LinkedList<String>();
			for (int i = 0; i < args.length; i++) {
				if (!Util.isNullOrNil(args[i])) {
					int index = args[i].indexOf(ARGS_SEPARATOR);
					if (index <= 0) {
						continue;
					}
					String key = args[i].substring(0, index);
					String value = index + 1 == args[i].length() ? "" : args[i].substring(index + 1, args[i].length());
					if (prefix.equalsIgnoreCase(key)) {
						results.add(value);
					}
				}
			}
			return results;
		}
	}
}
