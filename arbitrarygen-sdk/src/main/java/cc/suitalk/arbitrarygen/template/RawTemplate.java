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

package cc.suitalk.arbitrarygen.template;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.suitalk.arbitrarygen.base.Session;
import cc.suitalk.arbitrarygen.model.KeyValuePair;

/**
 * 
 * @author AlbieLiang
 *
 */
public class RawTemplate extends Session {

	private String mName;
	private String mContent;
	private int mType;
	private Map<String, String> mAttributes;
	private List<RawTemplate> mElements;

	public RawTemplate() {
		mAttributes = new HashMap<String, String>();
		mElements = new LinkedList<RawTemplate>();
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String content) {
		this.mContent = content;
	}

	public int getType() {
		return mType;
	}

	public void setType(int type) {
		this.mType = type;
	}

	public Map<String, String> getAttributes() {
		return mAttributes;
	}

	public List<RawTemplate> getElements() {
		return mElements;
	}

	public void addAttribute(KeyValuePair<String, String> attr) {
		if (attr != null) {
			mAttributes.put(attr.getKey(), attr.getValue());
		}
	}

	public void addElement(RawTemplate e) {
		mElements.add(e);
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
