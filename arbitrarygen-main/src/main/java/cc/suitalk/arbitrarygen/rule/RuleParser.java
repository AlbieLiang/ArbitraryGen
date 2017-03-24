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

package cc.suitalk.arbitrarygen.rule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.tools.RuntimeContextHelper;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class RuleParser {

	private static final String TAG = "AG.RuleParser";

	private RuleParser() {
	}

	public static List<String> parseAndScan(String path) {
		List<String> pathList = new LinkedList<>();
		RuleFileObject ruleFileObject = RuleParser.parse(path);
		if (ruleFileObject != null) {
			List<Project> projects = ruleFileObject.getProjects();
			for (int i = 0; i < projects.size(); i++) {
				pathList.addAll(RuleParser.scan(projects.get(i)));
			}
		}
		return pathList;
	}

	public static RuleFileObject parse(String path) {
		File file = new File(RuntimeContextHelper.replace(path));
		if (!file.exists() || !file.isFile()) {
			Log.i(TAG, "parse failed, path(%s) do not exist or it is not a file.", path);
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
					attachRootProjectArgsIfNeed(defaultProject, project);
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
				Log.e(TAG, "close reader error : %s", Log.getStackTraceString(e));
			}
		}
		return ruleFileObject;
	}

	public static List<String> scan(Project p) {
		List<String> fileList = new LinkedList<>();
		List<String> ruleFileList = new LinkedList<>();
		List<String> ruleList = new LinkedList<>();

		RuleFileObject fileObject = p.getRuleFileObject();
		String dir = Util.jointWhenNoNil(File.separator,
				(fileObject != null ? fileObject.getRoot() : ""), p.getName(), p.getSrc());
		if (!Util.isNullOrNil(dir) && !dir.endsWith(File.separator)) {
			dir += File.separator;
		}
		Log.d(TAG, "scan files by rule with base dir(%s).", dir);
		for (Rule rule : p.getRuleList()) {
			final String content = rule.getContent();
			Log.v(TAG, "rule(%s)", content);
			switch (rule.getType()) {
				case Rule.TYPE_RULE:
					int index = content.indexOf("*");
					if (index >= 0) {
						File file = new File(dir + content.substring(0, index));
						if (file.isDirectory()) {
							Log.v(TAG, "list rule files(%s)", file.getAbsolutePath());
							ruleFileList.addAll(FileOperation.listFilePaths(file, true));
							ruleList.add(file.getAbsolutePath() + File.separator
									+ content.substring(index).replaceAll("\\*", "(.)+"));
						} else {
							File parentFile = file.getParentFile();
							if (parentFile != null && parentFile.isDirectory()) {
								Log.v(TAG, "list rule files(%s)", parentFile.getAbsolutePath());
								ruleFileList.addAll(FileOperation.listFilePaths(parentFile, true));
								if ("/".equals(content.substring(index - 1, index))) {
									ruleList.add(file.getAbsolutePath() + File.separator
											+ content.substring(index).replaceAll("\\*", "(.)+"));
								} else  {
									ruleList.add(file.getAbsolutePath() + content.substring(index).replaceAll("\\*", "(.)+"));
								}
							}
						}
						break;
					}
				case Rule.TYPE_FILE:
					File file = new File(dir + content);
					if (file.isFile()) {
						fileList.add(file.getAbsolutePath());
					}
					break;
				case Rule.TYPE_DIRECTORY:
					file = new File(dir + content);
					if (!file.isDirectory()) {
						break;
					}
					fileList.addAll(FileOperation.listFilePaths(file, false));
					break;
				case Rule.TYPE_RECURSION_DIRECTORY:
					file = new File(dir + content);
					if (!file.isDirectory()) {
						break;
					}
					fileList.addAll(FileOperation.listFilePaths(file, true));
					break;
			}
		}
		for (String path : ruleFileList) {
			for (String r : ruleList) {
				boolean match = path.matches(r);
				if (match) {
					fileList.add(path);
				}
				Log.v(TAG, "rule(%s) match(%s) result : %b", r, path, match);
			}
		}
		return fileList;
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
				rule.setContent(line);
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

	private static void attachRootProjectArgsIfNeed(Project rootProject, Project project) {
		if (rootProject == null || project == null) {
			return;
		}
		if (project.getSrc() == null) {
			project.setSrc(rootProject.getSrc());
		}
	}

}
