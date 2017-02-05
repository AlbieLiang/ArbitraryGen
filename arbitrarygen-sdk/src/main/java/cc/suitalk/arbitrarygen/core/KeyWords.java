package cc.suitalk.arbitrarygen.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Keywords constants of java language
 * 
 * @author AlbieLiang
 * 
 */
public class KeyWords {

	/**
	 * Java keywords list
	 */
	public static final String V_JAVA_KEYWORDS_PACKAGE = "package";
	public static final String V_JAVA_KEYWORDS_IMPORT = "import";
	public static final String V_JAVA_KEYWORDS_ENUM = "enum";
	public static final String V_JAVA_KEYWORDS_CLASS = "class";
	public static final String V_JAVA_KEYWORDS_INTERFACE = "interface";
	public static final String V_JAVA_KEYWORDS_PUBLIC = "public";
	public static final String V_JAVA_KEYWORDS_PRIVATE = "private";
	public static final String V_JAVA_KEYWORDS_PROTECTED = "protected";
	public static final String V_JAVA_KEYWORDS_STATIC = "static";
	public static final String V_JAVA_KEYWORDS_FINAL = "final";
	public static final String V_JAVA_KEYWORDS_ABSTRACT = "abstract";
	public static final String V_JAVA_KEYWORDS_SYNCHRONIZED = "synchronized";
	public static final String V_JAVA_KEYWORDS_VOLATILE = "volatile";
	public static final String V_JAVA_KEYWORDS_EXTENDS = "extends";
	public static final String V_JAVA_KEYWORDS_IMPLEMENTS = "implements";
	public static final String V_JAVA_KEYWORDS_THROWS = "throws";
	public static final String V_JAVA_KEYWORDS_NEW = "new";
	public static final String V_JAVA_KEYWORDS_IF = "if";
	public static final String V_JAVA_KEYWORDS_ELSE = "else";
	public static final String V_JAVA_KEYWORDS_SWITCH = "switch";
	public static final String V_JAVA_KEYWORDS_CASE = "case";
	public static final String V_JAVA_KEYWORDS_DEFAULT = "default";
	public static final String V_JAVA_KEYWORDS_BREAK = "break";
	public static final String V_JAVA_KEYWORDS_THROW = "throw";
	public static final String V_JAVA_KEYWORDS_RETURN = "return";
	public static final String V_JAVA_KEYWORDS_WHILE = "while";
	public static final String V_JAVA_KEYWORDS_DO = "do";
	public static final String V_JAVA_KEYWORDS_CONTINUE = "continue";
	public static final String V_JAVA_KEYWORDS_NULL = "null";
	public static final String V_JAVA_KEYWORDS_TRY = "try";
	public static final String V_JAVA_KEYWORDS_CATCH = "catch";
	public static final String V_JAVA_KEYWORDS_FINALLY = "finally";
	public static final String V_JAVA_KEYWORDS_THIS = "this";
	public static final String V_JAVA_KEYWORDS_SUPER = "super";
	public static final String V_JAVA_KEYWORDS_TRUE = "true";
	public static final String V_JAVA_KEYWORDS_FALSE = "false";

	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_VOID = "void";
	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_INT = "int";
	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_CHAR = "char";
	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_LONG = "long";
	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_FLOAT = "float";
	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_SHORT = "short";
	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_BYTE = "byte";
	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_DOUBLE = "double";
	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_STRING = "String";
	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT = "Object";
	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_INT = "Integer";
	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_CHARACTER = "Character";
	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_LONG = "Long";
	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_FLOAT = "Float";
	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_SHORT = "Short";
	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_BYTE = "Byte";
	public static final String V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_DOUBLE = "Double";

	public static class Sign {
		private String value;
		private boolean isSingleChar;
		private char first;
		private Type type;

		/**
		 * Creat a normal {@link Type#NORMAL} Sign
		 * 
		 * @param value the sign string
		 */
		public Sign(String value) {
			this(value, Type.NORMAL);
		}

		public Sign(String value, Type type) {
			if (value == null || value.equals("") || type == null) {
				throw new RuntimeException(
						"value is null or nil or type is null.");
			}
			first = value.charAt(0);
			this.value = value;
			isSingleChar = value.length() == 1;
			this.type = type;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public boolean isSingleChar() {
			return isSingleChar;
		}

		public void setSingleChar(boolean isSingleChar) {
			this.isSingleChar = isSingleChar;
		}

		public char getFirst() {
			return first;
		}

		public void setFirst(char first) {
			this.first = first;
		}

		public Type getType() {
			return type;
		}

		public void setType(Type type) {
			this.type = type;
		}

		public static enum Type {
			NORMAL, BEGIN, END
		}

	}

	public static final String V_JAVA_KEYWORDS_SIGN_LEFT_SEQUARE_BRACKET = "[";
	public static final String V_JAVA_KEYWORDS_SIGN_RIGHT_SEQUARE_BRACKET = "]";
	public static final String V_JAVA_KEYWORDS_SIGN_LEFT_BRACKET = "(";
	public static final String V_JAVA_KEYWORDS_SIGN_RIGHT_BRACKET = ")";
	public static final String V_JAVA_KEYWORDS_SIGN_LEFT_BRACE = "{";
	public static final String V_JAVA_KEYWORDS_SIGN_RIGHT_BRACE = "}";
	public static final String V_JAVA_KEYWORDS_SIGN_EQUAL = "==";
	public static final String V_JAVA_KEYWORDS_SIGN_ASSIGNMENT = "=";
	public static final String V_JAVA_KEYWORDS_SIGN_DOT = ".";
	public static final String V_JAVA_KEYWORDS_SIGN_COLON = ":";
	public static final String V_JAVA_KEYWORDS_SIGN_SEMICOLON = ";";
	public static final String V_JAVA_KEYWORDS_SIGN_QUESTION_MARK = "?";
	public static final String V_JAVA_KEYWORDS_SIGN_BIT_AND = "&";
	public static final String V_JAVA_KEYWORDS_SIGN_NOR = "^";
	public static final String V_JAVA_KEYWORDS_SIGN_BIT_OR = "|";
	public static final String V_JAVA_KEYWORDS_SIGN_BIT_NOT = "~";
	public static final String V_JAVA_KEYWORDS_SIGN_NOT = "!";
	public static final String V_JAVA_KEYWORDS_SIGN_AND = "&&";
	public static final String V_JAVA_KEYWORDS_SIGN_OR = "||";
	public static final String V_JAVA_KEYWORDS_SIGN_MULTIPLE = "*";
	public static final String V_JAVA_KEYWORDS_SIGN_PERCENT = "%";
	public static final String V_JAVA_KEYWORDS_SIGN_DOUBLE_DIVISION = "/";
	public static final String V_JAVA_KEYWORDS_SIGN_MINUS = "-";
	public static final String V_JAVA_KEYWORDS_SIGN_PLUS = "+";
	public static final String V_JAVA_KEYWORDS_SIGN_DOUBLE_MINUS = "--";
	public static final String V_JAVA_KEYWORDS_SIGN_DOUBLE_PLUS = "++";
	public static final String V_JAVA_KEYWORDS_SIGN_LT = "<";
	public static final String V_JAVA_KEYWORDS_SIGN_GT = ">";
	public static final String V_JAVA_KEYWORDS_SIGN_LE = "<=";
	public static final String V_JAVA_KEYWORDS_SIGN_GE = ">=";
	public static final String V_JAVA_KEYWORDS_SIGN_NE = "!=";
	public static final String V_JAVA_KEYWORDS_SIGN_OR_ASSIGNMENT = "|=";
	public static final String V_JAVA_KEYWORDS_SIGN_LEFT_SHIFT = "<<";
	public static final String V_JAVA_KEYWORDS_SIGN_RIGHT_SHIFT = ">>";
	public static final String V_JAVA_KEYWORDS_SIGN_PLUS_EQUAL = "+=";
	public static final String V_JAVA_KEYWORDS_SIGN_MINUS_EQUAL = "-=";
	public static final String V_JAVA_KEYWORDS_SIGN_COMMA = ",";
	public static final String V_JAVA_KEYWORDS_SIGN_BIT_NOT_EQUAL = "~=";

	/**
	 * size of java keywords
	 */
	public static final int JAVA_KEYWORDS_SIZE = 88;

	public static Map<String, String> Keywords;
	public static Map<String, String> BaseDataTypes;
	public static Map<String, String> Signs;

	static {
		Keywords = new HashMap<String, String>();
		Keywords.put(V_JAVA_KEYWORDS_PACKAGE, V_JAVA_KEYWORDS_PACKAGE);
		Keywords.put(V_JAVA_KEYWORDS_IMPORT, V_JAVA_KEYWORDS_IMPORT);
		Keywords.put(V_JAVA_KEYWORDS_CLASS, V_JAVA_KEYWORDS_CLASS);
		Keywords.put(V_JAVA_KEYWORDS_INTERFACE, V_JAVA_KEYWORDS_INTERFACE);
		Keywords.put(V_JAVA_KEYWORDS_EXTENDS, V_JAVA_KEYWORDS_EXTENDS);
		Keywords.put(V_JAVA_KEYWORDS_IMPLEMENTS, V_JAVA_KEYWORDS_IMPLEMENTS);
		Keywords.put(V_JAVA_KEYWORDS_PUBLIC, V_JAVA_KEYWORDS_PUBLIC);
		Keywords.put(V_JAVA_KEYWORDS_PRIVATE, V_JAVA_KEYWORDS_PRIVATE);
		Keywords.put(V_JAVA_KEYWORDS_PROTECTED, V_JAVA_KEYWORDS_PROTECTED);
		Keywords.put(V_JAVA_KEYWORDS_STATIC, V_JAVA_KEYWORDS_STATIC);
		Keywords.put(V_JAVA_KEYWORDS_FINAL, V_JAVA_KEYWORDS_FINAL);
		Keywords.put(V_JAVA_KEYWORDS_SYNCHRONIZED, V_JAVA_KEYWORDS_SYNCHRONIZED);
		Keywords.put(V_JAVA_KEYWORDS_VOLATILE, V_JAVA_KEYWORDS_VOLATILE);
		Keywords.put(V_JAVA_KEYWORDS_NEW, V_JAVA_KEYWORDS_NEW);
		Keywords.put(V_JAVA_KEYWORDS_IF, V_JAVA_KEYWORDS_IF);
		Keywords.put(V_JAVA_KEYWORDS_ELSE, V_JAVA_KEYWORDS_ELSE);
		Keywords.put(V_JAVA_KEYWORDS_SWITCH, V_JAVA_KEYWORDS_SWITCH);
		Keywords.put(V_JAVA_KEYWORDS_CASE, V_JAVA_KEYWORDS_CASE);
		Keywords.put(V_JAVA_KEYWORDS_DEFAULT, V_JAVA_KEYWORDS_DEFAULT);
		Keywords.put(V_JAVA_KEYWORDS_BREAK, V_JAVA_KEYWORDS_BREAK);
		Keywords.put(V_JAVA_KEYWORDS_THROW, V_JAVA_KEYWORDS_THROW);
		Keywords.put(V_JAVA_KEYWORDS_THROWS, V_JAVA_KEYWORDS_THROWS);
		Keywords.put(V_JAVA_KEYWORDS_RETURN, V_JAVA_KEYWORDS_RETURN);
		Keywords.put(V_JAVA_KEYWORDS_WHILE, V_JAVA_KEYWORDS_WHILE);
		Keywords.put(V_JAVA_KEYWORDS_DO, V_JAVA_KEYWORDS_DO);
		Keywords.put(V_JAVA_KEYWORDS_CONTINUE, V_JAVA_KEYWORDS_CONTINUE);
		Keywords.put(V_JAVA_KEYWORDS_ENUM, V_JAVA_KEYWORDS_ENUM);
		Keywords.put(V_JAVA_KEYWORDS_NULL, V_JAVA_KEYWORDS_NULL);
		Keywords.put(V_JAVA_KEYWORDS_ABSTRACT, V_JAVA_KEYWORDS_ABSTRACT);
		Keywords.put(V_JAVA_KEYWORDS_SIGN_COMMA, V_JAVA_KEYWORDS_SIGN_COMMA);
		Keywords.put(V_JAVA_KEYWORDS_TRY, V_JAVA_KEYWORDS_TRY);
		Keywords.put(V_JAVA_KEYWORDS_CATCH, V_JAVA_KEYWORDS_CATCH);
		Keywords.put(V_JAVA_KEYWORDS_FINALLY, V_JAVA_KEYWORDS_FINALLY);

		BaseDataTypes = new HashMap<String, String>();
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_VOID,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_VOID);
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_INT,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_INT);
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_CHAR,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_CHAR);
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_LONG,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_LONG);
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_FLOAT,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_FLOAT);
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_SHORT,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_SHORT);
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_BYTE,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_BYTE);
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_DOUBLE,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_DOUBLE);
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_STRING,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_STRING);
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT);
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_INT,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_INT);
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_CHARACTER,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_CHARACTER);
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_LONG,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_LONG);
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_FLOAT,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_FLOAT);
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_SHORT,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_SHORT);
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_BYTE,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_BYTE);
		BaseDataTypes.put(V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_DOUBLE,
				V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT_DOUBLE);

		Signs = new HashMap<String, String>();
		
		Signs.put(V_JAVA_KEYWORDS_SIGN_LEFT_SEQUARE_BRACKET, V_JAVA_KEYWORDS_SIGN_LEFT_SEQUARE_BRACKET);
		Signs.put(V_JAVA_KEYWORDS_SIGN_RIGHT_SEQUARE_BRACKET, V_JAVA_KEYWORDS_SIGN_RIGHT_SEQUARE_BRACKET);
		Signs.put(V_JAVA_KEYWORDS_SIGN_LEFT_BRACKET, V_JAVA_KEYWORDS_SIGN_LEFT_BRACKET);
		Signs.put(V_JAVA_KEYWORDS_SIGN_RIGHT_BRACKET, V_JAVA_KEYWORDS_SIGN_RIGHT_BRACKET);
		Signs.put(V_JAVA_KEYWORDS_SIGN_LEFT_BRACE, V_JAVA_KEYWORDS_SIGN_LEFT_BRACE);
		Signs.put(V_JAVA_KEYWORDS_SIGN_RIGHT_BRACE, V_JAVA_KEYWORDS_SIGN_RIGHT_BRACE);
		Signs.put(V_JAVA_KEYWORDS_SIGN_EQUAL, V_JAVA_KEYWORDS_SIGN_EQUAL);
		Signs.put(V_JAVA_KEYWORDS_SIGN_ASSIGNMENT, V_JAVA_KEYWORDS_SIGN_ASSIGNMENT);
		Signs.put(V_JAVA_KEYWORDS_SIGN_DOT, V_JAVA_KEYWORDS_SIGN_DOT);
		Signs.put(V_JAVA_KEYWORDS_SIGN_COLON, V_JAVA_KEYWORDS_SIGN_COLON);
		Signs.put(V_JAVA_KEYWORDS_SIGN_SEMICOLON, V_JAVA_KEYWORDS_SIGN_SEMICOLON);
		Signs.put(V_JAVA_KEYWORDS_SIGN_QUESTION_MARK, V_JAVA_KEYWORDS_SIGN_QUESTION_MARK);
		Signs.put(V_JAVA_KEYWORDS_SIGN_BIT_AND, V_JAVA_KEYWORDS_SIGN_BIT_AND);
		Signs.put(V_JAVA_KEYWORDS_SIGN_NOR, V_JAVA_KEYWORDS_SIGN_NOR);
		Signs.put(V_JAVA_KEYWORDS_SIGN_BIT_OR, V_JAVA_KEYWORDS_SIGN_BIT_OR);
		Signs.put(V_JAVA_KEYWORDS_SIGN_BIT_NOT, V_JAVA_KEYWORDS_SIGN_BIT_NOT);
		Signs.put(V_JAVA_KEYWORDS_SIGN_NOT, V_JAVA_KEYWORDS_SIGN_NOT);
		Signs.put(V_JAVA_KEYWORDS_SIGN_AND, V_JAVA_KEYWORDS_SIGN_AND);
		Signs.put(V_JAVA_KEYWORDS_SIGN_OR, V_JAVA_KEYWORDS_SIGN_OR);
		Signs.put(V_JAVA_KEYWORDS_SIGN_MULTIPLE, V_JAVA_KEYWORDS_SIGN_MULTIPLE);
		Signs.put(V_JAVA_KEYWORDS_SIGN_PERCENT, V_JAVA_KEYWORDS_SIGN_PERCENT);
		Signs.put(V_JAVA_KEYWORDS_SIGN_DOUBLE_DIVISION, V_JAVA_KEYWORDS_SIGN_DOUBLE_DIVISION);
		Signs.put(V_JAVA_KEYWORDS_SIGN_MINUS, V_JAVA_KEYWORDS_SIGN_MINUS);
		Signs.put(V_JAVA_KEYWORDS_SIGN_PLUS, V_JAVA_KEYWORDS_SIGN_PLUS);
		Signs.put(V_JAVA_KEYWORDS_SIGN_DOUBLE_MINUS, V_JAVA_KEYWORDS_SIGN_DOUBLE_MINUS);
		Signs.put(V_JAVA_KEYWORDS_SIGN_DOUBLE_PLUS, V_JAVA_KEYWORDS_SIGN_DOUBLE_PLUS);
		Signs.put(V_JAVA_KEYWORDS_SIGN_LT, V_JAVA_KEYWORDS_SIGN_LT);
		Signs.put(V_JAVA_KEYWORDS_SIGN_GT, V_JAVA_KEYWORDS_SIGN_GT);
		Signs.put(V_JAVA_KEYWORDS_SIGN_LE, V_JAVA_KEYWORDS_SIGN_LE);
		Signs.put(V_JAVA_KEYWORDS_SIGN_GE, V_JAVA_KEYWORDS_SIGN_GE);
		Signs.put(V_JAVA_KEYWORDS_SIGN_NE, V_JAVA_KEYWORDS_SIGN_NE);
		Signs.put(V_JAVA_KEYWORDS_SIGN_LEFT_SHIFT, V_JAVA_KEYWORDS_SIGN_LEFT_SHIFT);
		Signs.put(V_JAVA_KEYWORDS_SIGN_RIGHT_SHIFT, V_JAVA_KEYWORDS_SIGN_RIGHT_SHIFT);
		Signs.put(V_JAVA_KEYWORDS_SIGN_PLUS_EQUAL, V_JAVA_KEYWORDS_SIGN_PLUS_EQUAL);
		Signs.put(V_JAVA_KEYWORDS_SIGN_MINUS_EQUAL,V_JAVA_KEYWORDS_SIGN_MINUS_EQUAL);
		Signs.put(V_JAVA_KEYWORDS_SIGN_BIT_NOT_EQUAL,V_JAVA_KEYWORDS_SIGN_BIT_NOT_EQUAL);
		
		
	}
}
