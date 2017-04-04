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

package cc.suitalk.arbitrarygen.core;

import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.extension.CustomizeParser;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TemplateParserMgr {

	private List<CustomizeParser> mParsers;
	private static TemplateParserMgr mgr;

	public static TemplateParserMgr getMgr() {
		if (mgr == null) {
			mgr = new TemplateParserMgr();
		}
		return mgr;
	}

	public TemplateParserMgr() {
		mParsers = new LinkedList<CustomizeParser>();
	}

	public CustomizeParser getFirstMatchParser(String suffix) {
		for (int i = 0; i < mParsers.size(); i++) {
			CustomizeParser p = mParsers.get(i);
			if (p.canParse(suffix)) {
				return p;
			}
		}
		return null;
	}

	public List<CustomizeParser> getParsers() {
		return mParsers;
	}

	public void addParser(CustomizeParser parser) {
		if (parser != null) {
			this.mParsers.add(parser);
		}
	}

	public void removeParser(CustomizeParser parser) {
		mParsers.remove(parser);
	}
}
