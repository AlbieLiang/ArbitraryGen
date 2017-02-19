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

package cc.suitalk.arbitrarygen.utils;

import cc.suitalk.arbitrarygen.block.FieldCodeBlock;
import cc.suitalk.arbitrarygen.core.OutcomeConstants;

/**
 * 
 * @author AlbieLiang
 *
 */
public final class MethodCreatorHelper {

	public static final String joint(String str1, String str2) {
		StringBuilder builder = new StringBuilder();
		builder.append(str1);
		builder.append(str2);
		return builder.toString();

	}

	public static final String genGetterName(FieldCodeBlock template) {
		if (template != null) {
			return joint(OutcomeConstants.GET,
					Util.changeFirstChatToUpper(template.getName().getName()));
		}
		return null;

	}

	public static final String genSetterName(FieldCodeBlock template) {
		if (template != null) {
			return joint(OutcomeConstants.SET,
					Util.changeFirstChatToUpper(template.getName().getName()));
		}
		return null;

	}
}