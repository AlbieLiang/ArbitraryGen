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
		;
	}

	public Rule parse(String path) {
		File file = new File(path);
		if (!file.exists() || !file.isFile()) {
			return null;
		}
		Rule rule = new Rule();
		FileReader reader = null;
		BufferedReader bufReader = null;
		try {
			reader = new FileReader(file);
			bufReader = new BufferedReader(reader);

			String line = null;
			Project project = new Project();
			rule.setDefaultProject(project);
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
				if ((args = extractCommandArgs(RuleConstants.AUTHOR, line)) != null && args.length > 0) {
					project.setAuthor(args[0]);
				} else if ((args = extractCommandArgs(RuleConstants.DATE, line)) != null && args.length > 0) {
					// TODO
					project.setDate(args[0]);
				} else if ((args = extractCommandArgs(RuleConstants.PROJECT_BEGIN, line)) != null && args.length > 0) {
					// TODO
					project = new Project();
					project.setName(args[0]);
					project.setRule(rule);
					rule.addProject(project);
				} else if ((args = extractCommandArgs(RuleConstants.PROJECT_END, line)) != null && args.length > 0) {
					project = rule.getDefaultProject();
				} else if ((args = extractCommandArgs(RuleConstants.PROJECT, line)) != null && args.length > 0) {
					project.setName(args[0]);
				} else if ((args = extractCommandArgs(RuleConstants.ROOT, line)) != null && args.length > 0) {
					project.setRoot(args[0]);
				} else if ((args = extractCommandArgs(RuleConstants.SRC_DIR_RECURSION, line)) != null && args.length > 0) {
					project.addSrcDirRecursion(args[0]);
				} else if ((args = extractCommandArgs(RuleConstants.SRC_DIR, line)) != null && args.length > 0) {
					project.addSrcDir(args[0]);
				} else if ((args = extractCommandArgs(RuleConstants.SRC_FILE, line)) != null && args.length > 0) {
					project.addSrcfile(args[0]);
				} else if ((args = extractCommandArgs(RuleConstants.SRC_FORMAT, line)) != null && args.length > 0) {
					project.addFormat(args[0]);
				} else if ((args = extractCommandArgs(RuleConstants.SRC, line)) != null && args.length > 0) {
					project.addSrc(args[0]);
				} else {
					int i = line.indexOf('*');
					if (i == 0) {
						// TODO
					} else if (i > 0) {
						if (line.charAt(i - 1) == '.') {
							// TODO
							if (i > 1) {
								// TODO
								project.addSrcDir(line.substring(0, i - 2));
							} else {
								// TODO
							}
						} else {
							// TODO
						}
					} else {
						project.addSrcfile(line);
					}
				}
				
			}
		} catch (FileNotFoundException e) {
			rule = null;
			e.printStackTrace();
		} catch (IOException e) {
			rule = null;
			e.printStackTrace();
		} finally {
			try {
				if (bufReader != null) {
					bufReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rule;
	}

	public String[] extractCommandArgs(String prefix, String msg) {
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
