package cc.suitalk.arbitrarygen.model;

import java.util.HashMap;
import java.util.Map;

import cc.suitalk.arbitrarygen.base.BaseDefineCodeBlock;
import cc.suitalk.arbitrarygen.statement.AnnotationStatement;

/**
 * 
 * @author AlbieLiang
 *
 */
public final class ArbitraryGenTaskInfo {
	
	private String mKey;
	private BaseDefineCodeBlock mCodeBlock;
	private Map<String, AnnotationStatement> mMatchAnnotations;
	
	public ArbitraryGenTaskInfo() {
		mMatchAnnotations = new HashMap<String, AnnotationStatement>();
	}

	public BaseDefineCodeBlock getCodeBlock() {
		return mCodeBlock;
	}
	
	public void setCodeBlock(BaseDefineCodeBlock codeblock) {
		this.mCodeBlock = codeblock;
	}
	
	public Map<String, AnnotationStatement> getMatchAnnotations() {
		return mMatchAnnotations;
	}
	
	public void setMatchAnnotations(Map<String, AnnotationStatement> matchAnnotations) {
		this.mMatchAnnotations = matchAnnotations;
	}

	public String getKey() {
		return mKey;
	}

	public void setKey(String key) {
		this.mKey = key;
	}
}