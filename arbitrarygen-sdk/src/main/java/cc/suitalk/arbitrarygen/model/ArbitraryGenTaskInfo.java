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