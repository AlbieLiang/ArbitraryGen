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

package cc.suitalk.arbitrarygen.statement;

import java.util.List;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.core.Value;

/**
 * 
 * @author AlbieLiang
 *
 */
public class MethodInvokeStatement extends BaseStatement {

	private String mName;
	private List<Value> mArgs;

	public MethodInvokeStatement(String name, List<Value> args) {
		mName = name;
		mArgs = args;
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();

		builder.append(genCommendBlock(linefeed));
		builder.append(mName);
		builder.append("(");
		if (mArgs != null && mArgs.size() > 0) {
			builder.append(mArgs.get(0));
			for (int i = 1; i < mArgs.size(); i++) {
				builder.append(", ");
				builder.append(mArgs.get(i).getValueStr());
			}
		}
		builder.append(")");
		builder.append(";");
		return builder.toString();
	}

}
