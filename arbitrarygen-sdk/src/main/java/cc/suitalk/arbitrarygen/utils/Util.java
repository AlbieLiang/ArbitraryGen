package cc.suitalk.arbitrarygen.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseCodeParser;
import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.block.FieldCodeBlock;
import cc.suitalk.arbitrarygen.block.MethodCodeBlock;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Value;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Environment.EnvironmentArgs;
import cc.suitalk.arbitrarygen.core.KeyWords.Sign;
import cc.suitalk.arbitrarygen.core.KeyWords.Sign.Type;
import cc.suitalk.arbitrarygen.core.Value.ValueType;
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.expression.ReferenceExpression;
import cc.suitalk.arbitrarygen.expression.VariableExpression;
import cc.suitalk.arbitrarygen.expression.parser.ReferenceExpressionParser;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.model.TypeName;
import cc.suitalk.arbitrarygen.statement.AnnotationStatement;
import cc.suitalk.arbitrarygen.statement.DefinitionStatement;
import cc.suitalk.arbitrarygen.statement.PlainStatement;
import cc.suitalk.arbitrarygen.statement.parser.AnnotationStatementParser;
import cc.suitalk.arbitrarygen.statement.parser.NormalStatementParser;

/**
 * 
 * @author AlbieLiang
 *
 */
public class Util {

	private final static String TAG = "AG.Util";
	
	public static final boolean isNullOrNil(String str) {
		return str == null || str.length() == 0;
	}

	public static final String nullAsNil(String str) {
		return str == null ? "" : str;
	}

	public static final String getStrWithDef(String str, String def) {
		return str == null ? def : str;
	}

	public static List<String> extractStrList(String str, String separator) {
		List<String> results = new LinkedList<String>();
		if (!Util.isNullOrNil(str)) {
			String[] s = str.split(separator);
			if (s != null && s.length > 0) {
				for (int i = 0; i < s.length; i++) {
					results.add(s[i]);
				}
			}
		}
		return results;
	}
	
	public static String joint(String separator, String... str) {
		if (str == null || str.length == 0) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append(str[0]);
		for (int i = 1; i < str.length; i++) {
			builder.append(separator);
			builder.append(str[i]);
		}
		return builder.toString();
	}

	public static String jointWhenNoNil(String separator, String... str) {
		if (str == null || str.length == 0) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append(str[0]);
		for (int i = 1; i < str.length; i++) {
			if (Util.isNullOrNil(str[i])) {
				continue;
			}
			builder.append(separator);
			builder.append(str[i]);
		}
		return builder.toString();
	}

	public static List<TypeName> convertTo(List<String> names) {
		List<TypeName> results = new LinkedList<TypeName>();
		if (names != null) {
			for (int i = 0; i < names.size(); i++) {
				results.add(Util.createSimpleTypeName(names.get(i)));
			}
		}
		return results;
	}
	
	public static final void mkdirsIfNeed(File file) {
		if (file == null) {
			return;
		}
		if (file.isFile()) {
			file = file.getParentFile();
		}
		if (file != null && !file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * Create a new file if it is not exists. If parent {@link File}s are not
	 * exists that it will make them automatically.
	 * 
	 * @param file the file to create if need
	 * @see #mkdirsIfNeed(File)
	 * @return true : if a new file was created, false : otherwise.
	 */
	public static boolean createFileIfNeed(File file) {
		if (file != null) {
			try {
				if (!file.exists()) {
					File parent = file.getParentFile();
					if (parent != null) {
						parent.mkdirs();
					}
					file.createNewFile();
					return true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * Get the suffix of the given file name.
	 * 
	 * @param fileName the file name
	 * @return the suffix of the file name
	 */
	public static String getSuffix(String fileName) {
		if (isNullOrNil(fileName)) {
			return null;
		}
		int lastIndex = fileName.lastIndexOf(".");
		if (lastIndex == -1 || fileName.length() == (lastIndex + 1)) {
			return null;
		}
		return fileName.substring(lastIndex + 1);
	}

	public static String exchangeToPath(String packageStr) {
		if (!Util.isNullOrNil(packageStr)) {
			return packageStr.replaceAll("\\.", "/");
		}
		return null;
	}

	public static String getPackageDir(JavaFileObject javaFileObject) {
		if (javaFileObject == null) {
			return null;
		}
		String path = exchangeToPath(javaFileObject.getPackage());
		if (!Util.isNullOrNil(path)) {
			path = "/" + path;
		}
		return Util.nullAsNil(path);
	}

	public static TypeName changeFirstChatToUpper(TypeName typeName) {
		if (typeName == null) {
			return null;
		}
		return Util.createSimpleTypeName(changeFirstChatToUpper(typeName.genCode("")));
	}
	
	public static String changeFirstChatToUpper(String src) {
		if (src == null || src.equals("")) {
			Log.d(TAG, "src is null or nil.");
			return src;
		}
		if (Character.isUpperCase(src.charAt(0)))
			return src;
		else {
			return (new StringBuilder())
					.append(Character.toUpperCase(src.charAt(0)))
					.append(src.substring(1)).toString();
		}
	}

	public static String changeFirstChatToLower(String src) {
		if (src == null || src.equals("")) {
			return src;
		}
		if (Character.isLowerCase(src.charAt(0)))
			return src;
		else {
			return (new StringBuilder())
					.append(Character.toLowerCase(src.charAt(0)))
					.append(src.substring(1)).toString();
		}
	}
	
	public static TypeName changeFirstChatToLower(TypeName typeName) {
		if (typeName == null) {
			return null;
		}
		return Util.createSimpleTypeName(changeFirstChatToLower(typeName.genCode("")));
	}
	
	public static Value convertTo(Word word) {
//		StringBuilder builder = new StringBuilder();
		Value value = null;
		switch (word.type) {
		case STRING_VALUE:
//			builder.append("\"");
//			builder.append(word.value);
//			builder.append("\"");
			value = new Value(word.value.substring(1, word.value.length() - 1), ValueType.STRING, word.value);
			break;
		case CHAR_VALUE:
//			builder.append("\'");
//			builder.append(word.value);
//			builder.append("\'");
			value = new Value(word.value.substring(1, word.value.length() - 1), ValueType.CHARACTER, word.value);
			break;
		case LONG:
			String longValue = word.value;
			if (longValue.endsWith("l") || longValue.endsWith("L")) {
				longValue = longValue.substring(0, longValue.length() - 1);
			}
			value = new Value(Long.parseLong(longValue), ValueType.LONG, word.value);
			break;
		case INTEGER:
			value = new Value(Integer.parseInt(word.value), ValueType.INTEGER, word.value);
			break;
		case HEX_INTEGER:
			value = new Value(Integer.parseInt(word.value, 16), ValueType.INTEGER, word.value);
			break;
		case FLOAT:
			String floatValue = word.value;
			if (floatValue.endsWith("f") || floatValue.endsWith("F")) {
				floatValue = floatValue.substring(0, floatValue.length() - 1);
			}
			value = new Value(Float.parseFloat(floatValue), ValueType.FLOAT, word.value);
			break;
		case DOUBLE:
			String doubleValue = word.value;
			if (doubleValue.endsWith("d") || doubleValue.endsWith("D")) {
				doubleValue = doubleValue.substring(0, doubleValue.length() - 1);
			}
			value = new Value(Double.parseDouble(doubleValue), ValueType.DOUBLE, word.value);
			break;
		default:
		}
		if (value != null) {
			value.setBlankStr(word.blankStr);
		}
		return value;
	}
	
	public static Value getValue(IReader reader, Lexer lexer, BaseCodeParser parser) throws IOException {
		StringBuilder builder = new StringBuilder();
		Value value = null;
		Word word = parser.nextWord(reader, lexer);
		if (word.type == WordType.STRING) {
			ReferenceExpressionParser refExprParser = ParserFactory.getRefExpressionParser(true);
			ReferenceExpression e = refExprParser.parse(reader, lexer, word);
			parser.setLastWord(refExprParser.getLastWord());
			builder.append(e.genCode(""));
			return new Value(null, ValueType.REFERENCE, builder.toString());
		} else {
			value = Util.convertTo(word);
			parser.nextWord(reader, lexer);
		}
		return value;
	}

	public static List<AnnotationStatement> parseAndAddAnnotation(IReader reader, Lexer lexer, Word curWord, BaseCodeParser parser) {
		AnnotationStatementParser asParser = ParserFactory.getAnnotationStatementParser();
		AnnotationStatement as = null;
		Word word = curWord;
		LinkedList<AnnotationStatement> ans = new LinkedList<AnnotationStatement>();
		// Parse Annotation
		do {
			as = asParser.parse(reader, lexer, word);
			parser.setLastWord(asParser.getLastWord());
			if (as != null) {
				ans.add(as);
				word = asParser.getLastWord();
				parser.setLastWord(word);
			} else {
				break;
			}
		} while (true);
		return ans.size() > 0 ? ans : null;
	}
	
	public static Expression extractExpressionFromBlacket(IReader reader, Lexer lexer, Word curWord, BaseCodeParser parser) throws IOException {
		Word word = curWord;
		if (!"(".equals(word.value)) {
			throw new RuntimeException("missed '(' when parse statement.");
		}
		word = parser.nextWord(reader, lexer);
		Expression e = Util.extractExpression(reader, lexer, word, parser, ")");
		word = parser.getLastWord();
		if (e == null) {
			throw new RuntimeException("condition expression error, when parse statement.");
		}
		if (!")".equals(word.value)) {
			throw new RuntimeException("missed ')' when parse statement.");
		}
		word = parser.nextWord(reader, lexer);
		return e;
	}

	public static void getAndAttachCodeBlock(IReader reader, Lexer lexer, Word curWord, BaseStatement statement, BaseCodeParser parser) throws IOException {
		NormalStatementParser nsp = ParserFactory.getNormalStatementParser();
		BaseStatement s = nsp.parse(reader, lexer, curWord);
		parser.setLastWord(nsp.getLastWord());
		if (s == null) {
			throw new RuntimeException("Parse Normal statement failed.");
		}
		if (s instanceof PlainStatement) {
			statement.setCodeBlock(s.getCodeBlock());
		} else {
			PlainCodeBlock codeblock = statement.getCodeBlock();
			codeblock.setDisplayBrace(false);
			statement.addStatement(s);
		}
	}

	public static Map<String, Set<BaseStatement>> extractContainsAnnotationStatementOfTypeDefine(TypeDefineCodeBlock codeBlock) {
		Map<String, Set<BaseStatement>> map = new HashMap<>();
		extractStatement(map, codeBlock);
		for (int m = 0; m < codeBlock.countOfMethods(); m++) {
			MethodCodeBlock mcb = codeBlock.getMethod(m);
			extractStatement(map, mcb);
		}
		for (int f = 0; f < codeBlock.countOfFields(); f++) {
			FieldCodeBlock fcb = codeBlock.getField(f);
			extractStatement(map, fcb);
		}
		return map;
	}

	private static void extractStatement(Map<String, Set<BaseStatement>> map, BaseStatement statement) {
		if (map == null || statement == null) {
			return;
		}
		for (int i = 0; i < statement.countOfAnnotations(); i++) {
			AnnotationStatement as = statement.getAnnotation(i);
			String name = as.getName().getName();
			Set<BaseStatement> set = map.get(name);
			if (set == null) {
				set = new HashSet<>();
				map.put(name, set);
			}
			set.add(statement);
		}
	}

	public static String extractExpressionWithEndSign(IReader reader, Lexer lexer, BaseCodeParser parser, String closeSign) throws IOException {
//		if (curWord != null && curWord.value.equals(closeSign)) {
//			return "";
//		}
		StringBuilder builder = new StringBuilder();
		Word word = null;
		String cs = closeSign;
//		builder.append(word.value);
		while ((word = parser.nextWord(reader, lexer)) != null && word.type != WordType.DOC_END) {
			if (word.value.equals(closeSign)) {
				break;
			}
			builder.append(word);
			if ("(".equals(word.value)) {
				cs = ")";
			} else if ("{".equals(word.value)) {
				cs = "}";
			} else {
				continue;
			}
			builder.append(extractExpressionWithEndSign(reader, lexer, parser, cs));
			word = parser.getLastWord();
			builder.append(word);
		}
		return builder.toString();
	}
	
	public static final Expression extractExpression(IReader reader, Lexer lexer, Word curWord, BaseCodeParser parser, String closeSign) throws IOException {
		StringBuilder builder = new StringBuilder();
		Word word = curWord;
		String cs = null;
		while (!closeSign.equals(word.value)) {
			builder.append(word);
			if ("(".equals(word.value)) {
				cs = ")";
			} else if ("{".equals(word.value)) {
				cs = "}";
			} else {
				word = parser.nextWord(reader, lexer);
				continue;
			}
			builder.append(Util.nullAsNil(Util.extractExpressionWithEndSign(reader, lexer, parser, cs)));
			word = parser.getLastWord();
			if (closeSign.equals(cs)) {
				builder.append(word);
				word = parser.nextWord(reader, lexer);
			}
		}
		Expression e = new Expression();
		e.setVariable(builder.toString());
		return e;
	}
	
	public static final TypeName createSimpleTypeName(String name) {
		if (Util.isNullOrNil(name)) {
			return null;
		}
		TypeName tn = new TypeName();
		tn.setName(new VariableExpression(name));
		return tn;
	}
	
	public static final TypeName createSimpleTypeName(Word word) {
		if (word == null) {
			return null;
		}
		TypeName tn = new TypeName();
		tn.setName(new VariableExpression(word));
		return tn;
	}
	
	public static final Word createSignWord(String value, Type type) {
		Word word = new Word();
		word.sign = new Sign(value, type);
		word.value = value;
		word.mark = type;
		word.type = WordType.SIGN;
		return word;
	}
	
	public static final Word createKeyWord(String value) {
		Word word = new Word();
		word.value = value;
		word.type = WordType.STRING;
		return word;
	}
	
	public static final String getLeftBracket(BaseStatement stm) {
		if (stm != null) {
			Word word = stm.getWordLeftBracket();
			if (word != null) {
				return word.toString();
			}
		}
		return "(";
	}
	
	public static final String getRightBracket(BaseStatement stm) {
		if (stm != null) {
			Word word = stm.getWordRightBracket();
			if (word != null) {
				return word.toString();
			}
		}
		return ")";
	}
	
	public static final String getPrefix(BaseStatement stm, String def) {
		if (stm != null) {
			Word word = stm.getPrefixWord();
			if (word != null) {
				return word.toString();
			}
		}
		return def;
	}
	
	public static final String getSuffix(BaseStatement stm, String def) {
		if (stm != null) {
			Word word = stm.getSuffixWord();
			if (word != null) {
				return word.toString();
			}
		}
		return def;
	}
	
	public static final String getFinal(DefinitionStatement stm) {
		if (stm != null) {
			Word word = stm.getWordFinal();
			if (word != null) {
				return word.toString();
			}
		}
		return "final";
	}
	
	public static final String getCodeStr(List<Word> words) {
		StringBuilder builder = new StringBuilder();
		if (words != null) {
			for (int i = 0; i < words.size(); i++) {
				Word word = words.get(i);
				builder.append(word);
			}
		}
		return builder.toString();
	}
	
	public static final String getCodeValue(List<Word> words) {
		StringBuilder builder = new StringBuilder();
		if (words != null) {
			for (int i = 0; i < words.size(); i++) {
				builder.append(words.get(i).value);
			}
		}
		return builder.toString();
	}

	public static final<T> void addAll(List<T> list, List<T> str) {
		if (list != null && str != null) {
			list.addAll(str);
		}
	}
	
	public static final<T> boolean add(List<T> list, T str) {
		if (list != null && str != null && !list.contains(str)) {
			list.add(str);
		}
		return false;
	}
	
	public static final int parseInt(String str, int def) {
		int result = def;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			result = def;
		}
		return result;
	}
	
	public static final boolean checkBitSetValue(int bitset, int flag) {
		return (bitset & flag) != 0;
	}
	
	public static final int setBitSetValue(int bitset, int flag, boolean value) {
		if (value) {
			bitset |= flag;
		} else {
			bitset &= (~flag);
		}
		return bitset;
	}
	

	public static EnvironmentArgs obtainEnvArgs(BaseStatement stm) {
		EnvironmentArgs args = stm != null ? stm.getEnvironmentArgs() : null;
		if (args != null) {
			args = args.copy();
		}
		if (args == null) {
			args = new EnvironmentArgs();
		}
		args.setSourceFileType(EnvironmentArgs.MODE_NORMAL);
		args.addFlag(EnvironmentArgs.MODE_FORCE_LINE_FEED);
		return args;
	}
	

	public static String getDateFormat(String format, long msec) {
		return (new SimpleDateFormat(format)).format(new Date(msec));
	}
}
