package osc.innovator.arbitrarygen.model;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import osc.innovator.arbitrarygen.analyzer.JavaStringLexer;
import osc.innovator.arbitrarygen.base.BaseDefineCodeBlock;
import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.base.Expression;
import osc.innovator.arbitrarygen.base.PlainCodeBlock;
import osc.innovator.arbitrarygen.block.FieldCodeBlock;
import osc.innovator.arbitrarygen.block.MethodCodeBlock;
import osc.innovator.arbitrarygen.block.TypeDefineCodeBlock;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.core.Word.WordType;
import osc.innovator.arbitrarygen.statement.ReturnStatement;
import osc.innovator.arbitrarygen.template.RawTemplate;
import osc.innovator.arbitrarygen.utils.Log;
import osc.innovator.arbitrarygen.utils.SaxXmlParserHandler;
import osc.innovator.arbitrarygen.utils.Util;
import osc.innovator.arbitrarygen.utils.XmlUtils;

/**
 * 
 * @author AlbieLiang
 *
 */
public class AutoGenFindViewHelper {

	private static final String TAG = "CodeGen.AutoGenFindViewHelper";
	public static final String ASSETS_FILE_DIR = "assets";
	public static final String RES_FILE_DIR = "res";
	public static final String LAYOUT_FILE_DIR = "layout";
	public static final String XML_SUFFIX = "xml";
	
	public static final String XML_ATTRIBUTE_ANDROID_ID = "android:id";
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
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
