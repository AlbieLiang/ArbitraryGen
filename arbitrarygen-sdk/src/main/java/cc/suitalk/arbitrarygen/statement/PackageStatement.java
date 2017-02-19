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

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.expression.ReferenceExpression;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class PackageStatement extends BaseStatement {

	private ReferenceExpression mPackage;

	public PackageStatement(ReferenceExpression packageName) {
		mPackage = packageName;
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "package"));
		builder.append(getWordBlank(BLANK_1));
		builder.append(mPackage.genCode(linefeed));
		builder.append(Util.getSuffix(this, ";"));
		return builder.toString();
	}

	public ReferenceExpression getPackageRefExpression() {
		return mPackage;
	}

	public String getPackage() {
		return mPackage != null ? mPackage.genCode("").trim() : "";
	}
}
