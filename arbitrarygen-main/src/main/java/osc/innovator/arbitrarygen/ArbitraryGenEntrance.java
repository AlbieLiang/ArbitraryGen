package osc.innovator.arbitrarygen;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import osc.innovator.arbitrarygen.core.ArgsConstants;
import osc.innovator.arbitrarygen.core.ConfigInfo;
import osc.innovator.arbitrarygen.core.IGenCodeEngine;
import osc.innovator.arbitrarygen.core.JarClassLoaderWrapper;
import osc.innovator.arbitrarygen.debug.Debuger;
import osc.innovator.arbitrarygen.engine.ArbitraryGenEngine;
import osc.innovator.arbitrarygen.engine.DefaultGenCodeEngine;
import osc.innovator.arbitrarygen.engine.ScriptTemplateGenCodeEngine;
import osc.innovator.arbitrarygen.extension.ICustomizeConvertor;
import osc.innovator.arbitrarygen.extension.ICustomizeParser;
import osc.innovator.arbitrarygen.extension.IDirector;
import osc.innovator.arbitrarygen.extension.ITemplateWrapper;
import osc.innovator.arbitrarygen.extension.ITypeDefineWrapper;
import osc.innovator.arbitrarygen.impl.DefaultRawTemplateParser;
import osc.innovator.arbitrarygen.impl.DefaultTemplateConvertor;
import osc.innovator.arbitrarygen.impl.DefaultTypeDefineWrapper;
import osc.innovator.arbitrarygen.rule.RuleParser;
import osc.innovator.arbitrarygen.template.GenVigorDBTask;
import osc.innovator.arbitrarygen.template.PsychicGenTask;
import osc.innovator.arbitrarygen.template.JsTemplateProcessor;
import osc.innovator.arbitrarygen.template.TemplateConfig;
import osc.innovator.arbitrarygen.template.base.ITemplateProcessor;
import osc.innovator.arbitrarygen.template.hybrids.GenHybridsTask;
import osc.innovator.arbitrarygen.tools.DefaultUncaughtExceptionHandler;
import osc.innovator.arbitrarygen.utils.Log;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * The entrance of the code generator.
 * 
 * @author AlbieLiang
 * 
 */
public class ArbitraryGenEntrance {

	private static final String TAG = "CodeGen.ArbitraryGenEntrance";

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
		String printSeparator = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_PRINT_SEPARATOR);
		boolean enablePrintSeparator = Util.isNullOrNil(printSeparator) ? true : Boolean.parseBoolean(printSeparator);
		if (enablePrintSeparator) {
			Log.i(TAG, "\n\n\n\n\n\n\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>begin<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n\n\n\n\n");
		}
		String logToFileArg = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_LOG_TO_FILE);
		boolean logToFile = !Util.isNullOrNil(logToFileArg);
		if (logToFile) {
			String logFile = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_LOG_FILE);
			if (Util.isNullOrNil(logFile)) {
				logFile = "./output/ag.log";
			}
			Log.setPath(logFile);
			Log.prepare();
		}
		IGenCodeEngine engine = new DefaultGenCodeEngine();
		// Add default template director, it contains raw template parser and
		// code template analyzer
		// TODO: 16/7/20 albieliang
//		engine.addDirector(new DefaultTemplateDirector());
		engine.addConvertor(new DefaultTemplateConvertor());
		// Add a detail type template director
		// engine.addDirector(new EventTemplateDirector());
		ConfigInfo configInfo = null;
		boolean arbitraryEnable = false;
		if (argsKvPair != null) {
			// Common Arguments
			Debuger.debug = Boolean.parseBoolean(argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_LOG_DEBUG));
			Log.setPrintLogLevel(Util.parseInt(argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_LOG_LEVEL), Log.LOG_LEVEL_N));
			Log.setPrintTag(Boolean.parseBoolean(argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_LOG_PRINT_TAG)));
			Log.setPrintLevel(Boolean.parseBoolean(argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_LOG_PRINT_LEVEL)));
			boolean printArgs = Boolean.parseBoolean(argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_PRINT_ARGS));
			
			if (printArgs) {
				// Print args
				printArgs(args);
			}
			configInfo = new ConfigInfo();
			// Extract the destination path arg
			String dest = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_DEST);
			if (!Util.isNullOrNil(dest)) {
				configInfo.setDestPath(dest);
			}
			// Extract the source template path arg
			String src = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_SRC);
			if (!Util.isNullOrNil(src)) {
				configInfo.setSrcPath(src);
			}
			// Extract the format suffix of source template file arg
			String formatChain = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_FORMAT);
			if (!Util.isNullOrNil(formatChain)) {
				List<String> suffixList = Util.extractStrList(formatChain, ExternalArgsParser.ARGS_LIST_SEPARATOR);
				engine.addAllSuffixList(suffixList);
				DefaultRawTemplateParser parser = new DefaultRawTemplateParser();
				parser.addSuffixList(suffixList);
				engine.addParser(parser);
			}

			// Extract ArbitraryEnable flag
			String arbitraryEnableArg = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_ARBITRARY_ENABLE);
			if (!Util.isNullOrNil(arbitraryEnableArg)) {
				arbitraryEnable = Boolean.parseBoolean(arbitraryEnableArg);
			}
			//
			engine.setConfigInfo(configInfo);
			JarClassLoaderWrapper loader = new JarClassLoaderWrapper();
			List<String> parserArgs = ExternalArgsParser.extractList(args, ArgsConstants.EXTERNAL_ARGS_KEY_PARSER);
			List<String> needToLoadClass = new LinkedList<String>();
			if (parserArgs != null && parserArgs.size() > 0) {
				for (int i = 0; i < parserArgs.size(); i++) {
					String argPair = parserArgs.get(i);
					String[] a = argPair.split(ExternalArgsParser.ARGS_LIST_SEPARATOR);
					if (a != null && a.length >= 2 && !Util.isNullOrNil(a[0]) && !Util.isNullOrNil(a[1])) {
						File file = new File(a[0]);
						if (loader.contains(file) || loader.addJar(file)) {
							needToLoadClass.add(a[1]);
						}
					}
				}
			}
			if (needToLoadClass.size() > 0) {
				for (int i = 0; i < needToLoadClass.size(); i++) {
					try {
						Class<?> clazz = loader.loadClass(needToLoadClass.get(i));
						Object o = clazz.newInstance();
						if (o instanceof IDirector) {
							engine.addDirector((IDirector) o);
						} else if (o instanceof ICustomizeParser) {
							engine.addParser((ICustomizeParser) o);
						} else if (o instanceof ICustomizeConvertor) {
							engine.addConvertor((ICustomizeConvertor) o);
						} else if (o instanceof ITemplateWrapper) {
							engine.addWrapper((ITemplateWrapper) o);
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		engine.start();
		if (argsKvPair != null) {
			// TODO
			if (arbitraryEnable) {
				String ruleArg = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_ARBITRARY_RULE);
				if (!Util.isNullOrNil(ruleArg)) {
					// Java file
					ArbitraryGenEngine agEngine = new ArbitraryGenEngine();
					agEngine.setConfigInfo(configInfo);
					RuleParser parser = new RuleParser();
					agEngine.addRule(parser.parse(ruleArg));
					ITypeDefineWrapper wrapper = new DefaultTypeDefineWrapper();
					// TODO Add more type worker here
//					wrapper.addIAGTaskWorker(worker);
					agEngine.addTypeDefWrapper(wrapper);
					agEngine.start();
				}
			}
			// For script template engine
			String coreLibs = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_CORE_LIBS);
			String templateLibs = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_TEMPLATE_LIBS);
			List<String> suffixList = Util.extractStrList(argsKvPair.get(
					ArgsConstants.EXTERNAL_ARGS_KEY_SCRIPT_ENGINE_FORMAT), ExternalArgsParser.ARGS_LIST_SEPARATOR);

			if (!Util.isNullOrNil(coreLibs) && !Util.isNullOrNil(templateLibs)) {
				ScriptTemplateGenCodeEngine scriptTemplateEngine = new ScriptTemplateGenCodeEngine();
				TemplateConfig cfg = new TemplateConfig(coreLibs, templateLibs);
				scriptTemplateEngine.setConfigInfo(configInfo);
				scriptTemplateEngine.setTemplateConfig(cfg);

				ITemplateProcessor processor = new JsTemplateProcessor();

				processor.addTaskWorker(new GenVigorDBTask(cfg));
				processor.addTaskWorker(new GenHybridsTask(cfg));
				processor.addTaskWorker(new PsychicGenTask(cfg, suffixList));

				scriptTemplateEngine.setTemplateProcessor(processor);
				scriptTemplateEngine.start();
			}
		}
		if (configInfo != null) {
			Runtime runtime = Runtime.getRuntime();
	//		String command = String.format("protoc --java_out=%s %s", "./" + argsKvPair.get("dest"), argsKvPair.get("src") + "/entity.proto");
			String command = String.format("protoc --java_out=%s %s", configInfo.getDestPath(), configInfo.getSrcPath() + "/entity.proto");
			Log.i(TAG, command);
			try {
				runtime.exec(command);
			} catch (IOException e) {
				Log.e(TAG, "exec protoc error : %s", e);
			}
		}
		if (enablePrintSeparator) {
			Log.i(TAG, "\n\n\n\n\n\n\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>end<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n\n\n\n\n");
		}
		if (logToFile) {
			Log.close();
		}
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
			Map<String, String> kvPair = new HashMap<String, String>();
			if (args != null && args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					if (!Util.isNullOrNil(args[i])) {
						String[] argKv = args[i].split(ARGS_SEPARATOR);
						if (argKv.length != 2) {
							continue;
						}
						kvPair.put(argKv[0], argKv[1]);
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
					String[] argKv = args[i].split(ARGS_SEPARATOR);
					if (argKv.length != 2) {
						continue;
					}
					if (prefix.equalsIgnoreCase(argKv[0])) {
						results.add(argKv[1]);
					}
				}
			}
			return results;
		}
	}
}
