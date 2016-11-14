package cc.suitalk.arbitrarygen.model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseCodeParser;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.base.ICodeGenerator;
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.expression.ReferenceExpression;
import cc.suitalk.arbitrarygen.expression.VariableExpression;
import cc.suitalk.arbitrarygen.expression.parser.ReferenceExpressionParser;
import cc.suitalk.arbitrarygen.extension.Lexer;
//import cc.suitalk.arbitrarygen.statement.AnnotationStatement;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TypeName implements ICodeGenerator {

//	private List<AnnotationStatement> mAnnotationStatements;
	private ReferenceExpression mName;
	private List<TypeName> mGenericityTypes;
	private List<Expression> mArrayArgs;
	
	public TypeName() {
		mGenericityTypes = new LinkedList<TypeName>();
		mArrayArgs = new LinkedList<Expression>();
//		mAnnotationStatements = new LinkedList<AnnotationStatement>();
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
//		builder.append(genAnnotationBlock(linefeed));
		builder.append(mName.genCode(linefeed));
		if (mGenericityTypes.size() > 0) {
			builder.append("<");
			builder.append(mGenericityTypes.get(0).genCode(linefeed));
			for (int i = 1; i < mGenericityTypes.size(); i++) {
				builder.append(", ");
				builder.append(mGenericityTypes.get(i).genCode(linefeed));
			}
			builder.append(">");
		}
		for (int i = 0; i < mArrayArgs.size(); i++) {
			builder.append("[");
			Expression expression = mArrayArgs.get(i);
			if (expression != null) {
				builder.append(expression.genCode(""));
			}
			builder.append("]");
		}
		return builder.toString();
	}
	
	@Override
	public String toString() {
		return genCode("");
	}

//	protected String genAnnotationBlock(String linefeed) {
//		StringBuilder builder = new StringBuilder();
//		int i = 0;
//		for (; i < mAnnotationStatements.size(); i++) {
//			AnnotationStatement s = mAnnotationStatements.get(i);
////			builder.append(getLinefeed(linefeed));
//			builder.append(s.genCode(linefeed));
//		}
//		if (i > 0) {
////			builder.append(getLinefeed(linefeed));
//		}
//		return builder.toString();
//	}
//
//	public boolean addAnnotation(AnnotationStatement s) {
//		if (s == null || mAnnotationStatements.contains(s)) {
//			return false;
//		}
//		return mAnnotationStatements.add(s);
//	}
//
//	public void addAnnotations(List<AnnotationStatement> annoStms) {
//		if (annoStms == null || annoStms.size() == 0) {
//			return;
//		}
//		for (AnnotationStatement as : annoStms) {
//			addAnnotation(as);
//		}
//	}
//
//	public AnnotationStatement removeAnnotation(int index) {
//		if (index >= 0 && index < mAnnotationStatements.size()) {
//			return mAnnotationStatements.remove(index);
//		}
//		return null;
//	}
//	
//	public boolean removeAnnotation(AnnotationStatement stm) {
//		if (stm != null) {
//			return mAnnotationStatements.remove(stm);
//		}
//		return false;
//	}
//	
//	public AnnotationStatement getAnnotation(int index) {
//		if (index >= 0 && index < mAnnotationStatements.size()) {
//			return mAnnotationStatements.get(index);
//		}
//		return null;
//	}
//	
//	public AnnotationStatement getAnnotation(String name) {
//		if (!Util.isNullOrNil(name)) {
//			for (int i = 0; i < mAnnotationStatements.size(); i++) {
//				AnnotationStatement as = mAnnotationStatements.get(i);
//				String aname = as.getName().getName();
//				if (aname.equals(name) || (aname.length() > name.length() && aname.endsWith(name)
//						&& aname.charAt(aname.length() - name.length() - 1) == '.')) {
//					return as;
//				}
//			}
//		}
//		return null;
//	}
//	
//	public int countOfAnnotations() {
//		return mAnnotationStatements.size();
//	}
	
	public ReferenceExpression getNameRefExpression() {
		return mName;
	}
	
	public String getName() {
		return mName.getVariable();
	}

	public void setName(ReferenceExpression name) {
		mName = name;
	}

	public List<TypeName> getGenericityType() {
		return mGenericityTypes;
	}

	public void addGenericityType(TypeName genericityType) {
		mGenericityTypes.add(genericityType);
	}

	public List<Expression> getArrayArgs() {
		return mArrayArgs;
	}
	
	public void addArrayArg(Expression e) {
		mArrayArgs.add(e);
	}
	/**
	 * 
	 * @author AlbieLiang
	 *
	 */
	public static final class Parser extends BaseCodeParser {

		private static final String TAG = "TypeName.Parser";

		@Override
		public TypeName parse(IReader reader, Lexer lexer, Word curWord) throws IOException {
			ReferenceExpressionParser parser = ParserFactory.getRefExpressionParser(true);
			ReferenceExpression refExpr = parser.parse(reader, lexer, curWord);
			if (refExpr == null) {
				Log.i(TAG, "Parse refExpr is null.");
				return null;
			}
			setLastWord(parser.getLastWord());
			Word word = getLastWord();
			TypeName typeName = new TypeName();
			typeName.setName(refExpr);
			if (word.value.equals("<")) {
				word = nextWord(reader, lexer);
				if ("?".equals(word.value)) {
					word = nextWord(reader, lexer);
					if (!">".equals(word.value)) {
						throw new RuntimeException("missing '>' sign.");
					}
					TypeName gt = new TypeName();
					gt.setName(new VariableExpression("?"));
					typeName.addGenericityType(gt);
					nextWord(reader, lexer);
				} else {
					TypeName.Parser p = new TypeName.Parser();
					do {
						TypeName tn = p.parse(reader, lexer, word);
						typeName.addGenericityType(tn);
						setLastWord(p.getLastWord());
						word = getLastWord();
						if (">".equals(word.value)) {
							word = nextWord(reader, lexer);
							break;
						} else if (",".equals(word.value)) {
							word = nextWord(reader, lexer);
							continue;
						} else {
							throw new RuntimeException("Parse TypeName error.(last word : " + word.value + ").");
						}
					} while (word.type != WordType.DOC_END);
				}
			}
			while ("[".equals(word.value)) {
				Expression e = Util.extractExpression(reader, lexer, nextWord(reader, lexer), this, "]");
				typeName.addArrayArg(e);
				word = nextWord(reader, lexer);
			}
			return typeName;
		}
		
	}
}
