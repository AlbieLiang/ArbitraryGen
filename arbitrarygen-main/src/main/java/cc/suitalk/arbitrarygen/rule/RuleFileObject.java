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

import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.tools.RuntimeContextHelper;

/**
 * 
 * @author AlbieLiang
 *
 */
public class RuleFileObject {

	private String root;
	private String author;
	private String date;

	private List<Project> projectList;

	public RuleFileObject() {
		projectList = new LinkedList<>();
	}
	
	public List<Project> getProjects() {
		return projectList;
	}

	public void addProjects(List<Project> projects) {
		if (projects != null) {
			projectList.addAll(projects);
		}
	}

	public boolean addProject(Project p) {
		if (p != null && !projectList.contains(p)) {
			return projectList.add(p);
		}
		return false;
	}
	
	public boolean removeProject(Project p) {
		return projectList.remove(p);
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = RuntimeContextHelper.replace(root);
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = RuntimeContextHelper.replace(author);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = RuntimeContextHelper.replace(date);
	}

}
