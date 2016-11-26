package cc.suitalk.arbitrarygen.rule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class RuleParser {

	private static final String TAG = "AG.RuleParser";

	public RuleParser() {
	}

	public RuleFileObject parse(String path) {
		File file = new File(path);
		if (!file.exists() || !file.isFile()) {
			return null;
		}
		RuleFileObject ruleFileObject = new RuleFileObject();
		FileReader reader = null;
		BufferedReader bufReader = null;
		try {
			reader = new FileReader(file);
			bufReader = new BufferedReader(reader);

			String line = null;
			Project defaultProject = new Project();
			ruleFileObject.addProject(defaultProject);
			defaultProject.setRuleFileObject(ruleFileObject);
			while ((line = bufReader.readLine()) != null) {
				Log.v(TAG, line);
				line = line.trim();
				if (line.startsWith("#")) {
					continue;
				}
				int index = line.indexOf('#');
				if (index != -1) {
					line = line.substring(0, index);
					line = line.trim();
				}
				if (line.equals("")) {
					continue;
				}
				String[] args = null;
				if ((args = splitArgs(RuleConstants.AUTHOR, line)) != null && args.length > 0) {
					ruleFileObject.setAuthor(args[0]);
				} else if ((args = splitArgs(RuleConstants.DATE, line)) != null && args.length > 0) {
					String date = args[0];
					if ("current".equalsIgnoreCase(date)) {
						date = Util.getDateFormat("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis());
					}
					ruleFileObject.setDate(date);
				} else if ((args = splitArgs(RuleConstants.ROOT, line)) != null && args.length > 0) {
					ruleFileObject.setRoot(args[0]);
				} else if ((args = splitArgs(RuleConstants.SRC_DIR_RECURSION, line)) != null && args.length > 0) {
					Rule rule = new Rule();
					rule.setType(Rule.TYPE_RECURSION_DIRECTORY);
					rule.setContent(args[0]);
					defaultProject.addRule(rule);
				} else if ((args = splitArgs(RuleConstants.SRC_DIR, line)) != null && args.length > 0) {
					Rule rule = new Rule();
					rule.setType(Rule.TYPE_DIRECTORY);
					rule.setContent(args[0]);
					defaultProject.addRule(rule);
				} else if ((args = splitArgs(RuleConstants.SRC_FILE, line)) != null && args.length > 0) {
					Rule rule = new Rule();
					rule.setType(Rule.TYPE_FILE);
					rule.setContent(args[0]);
					defaultProject.addRule(rule);
				} else if ((args = splitArgs(RuleConstants.SRC, line)) != null && args.length > 0) {
					defaultProject.setSrc(args[0]);
				} else if ((args = splitArgs(RuleConstants.PROJECT_BEGIN, line)) != null && args.length > 0) {
					Project project = parseProject(args[0], bufReader);
					project.setRuleFileObject(ruleFileObject);
					ruleFileObject.addProject(project);
				} else if ((args = splitArgs(RuleConstants.PROJECT, line)) != null && args.length > 0) {
					defaultProject.setName(args[0]);
				} else {
					// TODO: 2016/11/26 albieliang, handle illegal format
					Rule rule = new Rule();
					rule.setType(Rule.TYPE_RULE);
					rule.setContent(line);
					defaultProject.addRule(rule);
				}
			}
		} catch (FileNotFoundException e) {
			ruleFileObject = null;
			Log.e(TAG, "parse error : %s", Log.getStackTraceString(e));
		} catch (IOException e) {
			ruleFileObject = null;
			Log.e(TAG, "parse error : %s", Log.getStackTraceString(e));
		} catch (Exception e) {
			ruleFileObject = null;
			Log.e(TAG, "parse error : %s", Log.getStackTraceString(e));
		} finally {
			try {
				if (bufReader != null) {
					bufReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ruleFileObject;
	}

	private static Project parseProject(String name, BufferedReader bufReader) throws IOException {
		Project project = new Project();
		project.setName(name);
		String line;
		while ((line = bufReader.readLine()) != null) {
			Log.v(TAG, line);
			line = line.trim();
			if (line.startsWith("#")) {
				continue;
			}
			int index = line.indexOf('#');
			if (index != -1) {
				line = line.substring(0, index);
				line = line.trim();
			}
			if (line.equals("")) {
				continue;
			}
			String[] args = null;
			if ((args = splitArgs(RuleConstants.SRC_DIR_RECURSION, line)) != null && args.length > 0) {
				Rule rule = new Rule();
				rule.setType(Rule.TYPE_RECURSION_DIRECTORY);
				rule.setContent(args[0]);
				project.addRule(rule);
			} else if ((args = splitArgs(RuleConstants.SRC_DIR, line)) != null && args.length > 0) {
				Rule rule = new Rule();
				rule.setType(Rule.TYPE_DIRECTORY);
				rule.setContent(args[0]);
				project.addRule(rule);
			} else if ((args = splitArgs(RuleConstants.SRC_FILE, line)) != null && args.length > 0) {
				Rule rule = new Rule();
				rule.setType(Rule.TYPE_FILE);
				rule.setContent(args[0]);
				project.addRule(rule);
			} else if ((args = splitArgs(RuleConstants.SRC, line)) != null && args.length > 0) {
				project.setSrc(args[0]);
			} else if (splitArgs(RuleConstants.PROJECT_END, line) != null) {
				Log.d(TAG, "end tag for Project(%s).", name);
				break;
			} else {
				// TODO: 2016/11/26 albieliang, handle illegal format
				Rule rule = new Rule();
				rule.setType(Rule.TYPE_RULE);
				rule.setContent(args[0]);
				project.addRule(rule);
			}
		}
		return project;
	}

	private static String[] splitArgs(String prefix, String msg) {
		if (Util.isNullOrNil(msg)) {
			Log.e(TAG, "msg is null or nil.");
			return null;
		}
		if (msg.startsWith(prefix)) {
			msg = msg.replace(prefix, "");
		} else {
			return null;
		}
		msg = msg.trim();
		msg = msg.replaceAll("[ ]+", "#");
		String[] command = msg.split("#");
		return command;
	}
}
