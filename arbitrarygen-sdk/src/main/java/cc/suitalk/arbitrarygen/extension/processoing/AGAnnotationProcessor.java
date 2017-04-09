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

package cc.suitalk.arbitrarygen.extension.processoing;

import net.sf.json.JSONObject;

import java.util.Set;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;

/**
 * Created by AlbieLiang on 16/11/12.
 */
public interface AGAnnotationProcessor {

    Set<String> getSupportedAnnotationTypes();

    boolean process(JSONObject env, JavaFileObject fileObject, TypeDefineCodeBlock typeDefineCodeBlock,
                    Set<? extends BaseStatement> containsSpecialAnnotationStatements);
}