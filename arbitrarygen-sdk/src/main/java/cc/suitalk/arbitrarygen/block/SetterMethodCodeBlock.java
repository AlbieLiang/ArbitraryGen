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

package cc.suitalk.arbitrarygen.block;

import cc.suitalk.arbitrarygen.core.KeyWords;
import cc.suitalk.arbitrarygen.core.OutcomeConstants;
import cc.suitalk.arbitrarygen.model.DefaultKeyValuePair;
import cc.suitalk.arbitrarygen.statement.NormalStatement;
import cc.suitalk.arbitrarygen.utils.MethodCreatorHelper;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class SetterMethodCodeBlock extends DefaultMethodCodeBlock {
	
	public SetterMethodCodeBlock(FieldCodeBlock template) {
		this(template.getName().getName(), template.getType().getName());
	}

	public SetterMethodCodeBlock(String fieldName, String returnType) {
		super(MethodCreatorHelper.joint(OutcomeConstants.SET, Util.changeFirstChatToUpper(fieldName)),
				KeyWords.V_JAVA_KEYWORDS_DATA_BASE_TYPE_VOID,
				new DefaultKeyValuePair(Util.createKeyWord(fieldName), Util.createSimpleTypeName(returnType)));
		NormalStatement stm = new NormalStatement();
		stm.setStatementStr(String.format("%s.%s %s %s",
				KeyWords.V_JAVA_KEYWORDS_THIS, fieldName,
				KeyWords.V_JAVA_KEYWORDS_SIGN_ASSIGNMENT, fieldName));
		addStatement(stm);
	}
}
