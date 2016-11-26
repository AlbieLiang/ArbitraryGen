package cc.suitalk.arbitrarygen.rule;

import java.util.LinkedList;
import java.util.List;

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
		this.mName = name;
	}

	public String getSrc() {
		return mSrc;
	}
	
	public void setSrc(String src) {
		mSrc = src;
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
