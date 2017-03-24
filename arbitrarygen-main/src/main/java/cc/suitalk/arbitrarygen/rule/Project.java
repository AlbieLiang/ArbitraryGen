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
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class Project {

	private RuleFileObject mRuleFileObject;

	private String mName;
	private String mSrc;
	private List<Rule> mRuleList;
	
	public Project() {
		mRuleList = new LinkedList<>();
	}
	
	public RuleFileObject getRuleFileObject() {
		return mRuleFileObject;
	}

	public void setRuleFileObject(RuleFileObject ruleFileObject) {
		this.mRuleFileObject = ruleFileObject;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = RuntimeContextHelper.replace(name);
	}

	public String getSrc() {
		return mSrc;
	}
	
	public void setSrc(String src) {
		mSrc = RuntimeContextHelper.replace(src);
	}

	public List<Rule> getRuleList() {
		return mRuleList;
	}
	
	public void addRuleList(List<Rule> ruleList) {
		Util.addAll(mRuleList, ruleList);
	}
	
	public boolean addRule(Rule rule) {
		return Util.add(mRuleList, rule);
	}
	
	public boolean removeSrcDir(String rule) {
		return mRuleList.remove(rule);
	}
}
