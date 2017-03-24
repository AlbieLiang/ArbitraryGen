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

package cc.suitalk.arbitrarygen.impl;

import net.sf.json.JSONObject;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ConfigInfo;
import cc.suitalk.arbitrarygen.extension.AGAnnotationProcessor;
import cc.suitalk.arbitrarygen.extension.AGTaskWorker;
import cc.suitalk.arbitrarygen.extension.TypeDefineWrapper;
import cc.suitalk.arbitrarygen.protocol.EnvArgsConstants;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/12.
 */
public class AGAnnotationWrapper implements TypeDefineWrapper {

	private static final String TAG = "AG.AGAnnotationWrapper";

	private List<AGAnnotationProcessor> mProcessorList;

	public AGAnnotationWrapper() {
		mProcessorList = new LinkedList<>();
		// Just for test
//		addAnnotationProcessor(new TestAGAnnotationProcessor());
	}
	
	@Override
	public boolean doWrap(ConfigInfo configInfo, JavaFileObject fileObject) {
		if (fileObject != null) {
			File file = configInfo.getFile();
			JSONObject env = new JSONObject();
			env.put(EnvArgsConstants.KEY_PACKAGE, fileObject.getPackage());
			env.put(EnvArgsConstants.KEY_INPUT_DIR, configInfo.getSrcPath());
			env.put(EnvArgsConstants.KEY_OUTPUT_DIR, configInfo.getDestPath());
			if (file != null) {
				env.put(EnvArgsConstants.KEY_FILE_PATH, file.getAbsolutePath());
			}
			for (int i = 0; i < fileObject.getCountOfTypeDefCodeBlock(); i++) {
				TypeDefineCodeBlock codeBlock = fileObject.getTypeDefineCodeBlock(i);
				Map<String, Set<BaseStatement>> map = Util.extractContainsAnnotationStatementOfTypeDefine(codeBlock);
				Set<BaseStatement> set = new HashSet<>();
				for (AGAnnotationProcessor processor : mProcessorList) {
					Set<String> supportTypes = processor.getSupportedAnnotationTypes();
					set.clear();
					if (supportTypes != null && !supportTypes.isEmpty()) {
						for (String type : supportTypes) {
							Set<BaseStatement> baseStatementSet = map.get(type);
							if (baseStatementSet == null || baseStatementSet.isEmpty()) {
								continue;
							}
							set.addAll(baseStatementSet);
						}
					}
					if (!set.isEmpty()) {
						processor.process(env, fileObject, codeBlock, set);
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean doWrap(ConfigInfo configInfo, TypeDefineCodeBlock codeBlock) {
		return false;
	}
	
	@Override
	public boolean addIAGTaskWorker(AGTaskWorker worker) {
		return false;
	}
	
	@Override
	public boolean removeIAGTaskWorker(AGTaskWorker worker) {
		return false;
	}

	public boolean addAnnotationProcessor(AGAnnotationProcessor processor) {
		if (processor == null || mProcessorList.contains(processor)) {
			return false;
		}
		return mProcessorList.add(processor);
	}

	public boolean removeAnnotationProcessor(AGAnnotationProcessor processor) {
		if (processor == null) {
			return false;
		}
		return mProcessorList.remove(processor);
	}
}
