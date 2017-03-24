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

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.analyzer.JavaStringLexer;
import cc.suitalk.arbitrarygen.base.BaseDefineCodeBlock;
import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.block.FieldCodeBlock;
import cc.suitalk.arbitrarygen.block.MethodCodeBlock;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.statement.ReturnStatement;
import cc.suitalk.arbitrarygen.template.RawTemplate;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.SaxXmlParserHandler;
import cc.suitalk.arbitrarygen.utils.Util;
import cc.suitalk.arbitrarygen.utils.XmlUtils;

/**
 * 
 * @author AlbieLiang
 *
 */
public class AutoGenFindViewHelper {

	private static final String TAG = "AG.AutoGenFindViewHelper";
	public static final String ASSETS_FILE_DIR = "assets";
	public static final String RES_FILE_DIR = "res";
	public static final String LAYOUT_FILE_DIR = "layout";
	public static final String XML_SUFFIX = "xml";
	
	public static final String XML_ATTRIBUTE_ANDROID_ID = "android:id";

	public static List<FindViewIdsTask> getIdsFromLayoutFile(String fileName) {
		if (Util.isNullOrNil(fileName)) {
			return null;
		}
		List<FindViewIdsTask> results = new LinkedList<FindViewIdsTask>();
		String path = System.getProperty("user.dir") + File.separator + RES_FILE_DIR + File.separator + LAYOUT_FILE_DIR + File.separator + fileName + "." + XML_SUFFIX;
		File file = new File(path);
		Log.d(TAG, "layout file path : " + path);
		if (file.exists() && file.isFile()) {
			SaxXmlParserHandler handler = new SaxXmlParserHandler();
			handler.setMode(SaxXmlParserHandler.MODE_EXTRACT_ATTR);
			handler.addExtractAttr(XML_ATTRIBUTE_ANDROID_ID);
			XmlUtils.getXmlNotes(file, handler);
			List<RawTemplate> notes = handler.getXmlNotesByAttr(XML_ATTRIBUTE_ANDROID_ID);
			if (notes != null) {
				for (RawTemplate rawTemplate : notes) {
					String androidId = rawTemplate.getAttributes().get(XML_ATTRIBUTE_ANDROID_ID);
					if (Util.isNullOrNil(androidId)) {
						continue;
					}
					int index = androidId.indexOf('/');
					if (index == -1 || index == androidId.length() - 1) {
						continue;
					}
					String name = androidId.substring(index + 1);
					FindViewIdsTask task = new FindViewIdsTask();
					task.setId(name);
					task.setType(rawTemplate.getName());
					results.add(task);
					Log.d(TAG, "android:id=\"" + androidId + "\", extract result : " + name);
				}
			}
		}
		return results;
	}
	
	public static String getLayoutFileName(BaseDefineCodeBlock codeBlock) {
		String result = null;
		do {
			if (codeBlock instanceof MethodCodeBlock) {
				MethodCodeBlock method = (MethodCodeBlock) codeBlock;
				PlainCodeBlock pcb = method.getCodeBlock();
				if (pcb == null || pcb.countOfStatement() == 0) {
					break;
				}
				BaseStatement stm = pcb.getStatement(pcb.countOfStatement() - 1);
				if (stm == null || !(stm instanceof ReturnStatement)) {
					break;
				}
				ReturnStatement rstm = (ReturnStatement) stm;
				Expression exp = rstm.getExpression();
				result = extractLastWord(exp);
			} else if (codeBlock instanceof FieldCodeBlock) {
				// TODO
			} else if (codeBlock instanceof TypeDefineCodeBlock) {
				// TODO
			}
		} while (false);
		return result;
	}
	
	private static String extractLastWord(Expression e) {
		if (e != null) {
			JavaStringLexer lexer = new JavaStringLexer();
			List<Word> words = null;
			try {
				words = lexer.parse(e.genCode(""));
				if (words != null) {
					Word w = null;
					for (int i = words.size() - 1; i >= 0; i--) {
						Word word = words.get(i);
						if (word.type == WordType.STRING) {
							w = word;
							break;
						}
					}
					if (w != null) {
						return w.value;
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	public static class FindViewIdsTask {
		private String id;
		private String type;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("id : ");
			builder.append(id);
			builder.append(", type : ");
			builder.append(type);
			return builder.toString();
		}
	}
}
