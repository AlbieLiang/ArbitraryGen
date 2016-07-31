package cc.suitalk.arbitrarygen.core;

import cc.suitalk.arbitrarygen.expression.parser.PlainExpressionParser;
import cc.suitalk.arbitrarygen.expression.parser.ReferenceExpressionParser;
import cc.suitalk.arbitrarygen.model.TypeName;
import cc.suitalk.arbitrarygen.statement.parser.AnnotationStatementParser;
import cc.suitalk.arbitrarygen.statement.parser.DefinitionStatementParser;
import cc.suitalk.arbitrarygen.statement.parser.NormalStatementParser;
import cc.suitalk.arbitrarygen.statement.parser.PlainStatementParser;
import cc.suitalk.arbitrarygen.statement.parser.StaticStatementParser;
import cc.suitalk.arbitrarygen.statement.parser.TypeDefineStatementParser;

public class ParserFactory {

	private static ParserFactory sFactory;
	private ReferenceExpressionParser refExpressionParser;
	private ReferenceExpressionParser refExpParserVariableOnly;
	private PlainExpressionParser plainExpressionParser;
	private AnnotationStatementParser annotationStatementParser;
	private TypeDefineStatementParser typeDefineStatementParser;
	private PlainStatementParser plainStatementParser;
	private NormalStatementParser normalStatementParser;
	private StaticStatementParser staticStatementParser;
	private DefinitionStatementParser definitionStatementParser;
//	private CommendStatementParser commendStatementParser;
	private TypeName.Parser typeNameParser;
	
	private ParserFactory() {
	}
	public synchronized static ParserFactory getImpl() {
		if (sFactory == null) {
			sFactory = new ParserFactory();
		}
		return sFactory;
	}

	public static final ReferenceExpressionParser getRefExpressionParser(boolean variableOnly) {
		ParserFactory factory = ParserFactory.getImpl();
		if (variableOnly) {
			if (factory.refExpressionParser == null) {
				factory.refExpressionParser = new ReferenceExpressionParser(variableOnly);
			}
			return factory.refExpressionParser;
		} else {
			if (factory.refExpParserVariableOnly == null) {
				factory.refExpParserVariableOnly = new ReferenceExpressionParser(variableOnly);
			}
			return factory.refExpParserVariableOnly;
		}
	}

	public static final PlainExpressionParser getPlainExpressionParser() {
		ParserFactory factory = ParserFactory.getImpl();
		if (factory.plainExpressionParser == null) {
			factory.plainExpressionParser = new PlainExpressionParser();
		}
		return factory.plainExpressionParser;
	}
	
	public static final AnnotationStatementParser getAnnotationStatementParser() {
		ParserFactory factory = ParserFactory.getImpl();
		if (factory.annotationStatementParser == null) {
			factory.annotationStatementParser = new AnnotationStatementParser();
		}
		return factory.annotationStatementParser;
	}

	public static final TypeDefineStatementParser getTypeDefineStatementParser() {
		ParserFactory factory = ParserFactory.getImpl();
		if (factory.typeDefineStatementParser == null) {
			factory.typeDefineStatementParser = new TypeDefineStatementParser();
		}
		return factory.typeDefineStatementParser;
	}
	
	public static final PlainStatementParser getPlainStatementParser() {
		ParserFactory factory = ParserFactory.getImpl();
		if (factory.plainStatementParser == null) {
			factory.plainStatementParser = new PlainStatementParser();
		}
		return factory.plainStatementParser;
	}

	public static final NormalStatementParser getNormalStatementParser() {
		ParserFactory factory = ParserFactory.getImpl();
		if (factory.normalStatementParser == null) {
			factory.normalStatementParser = new NormalStatementParser();
		}
		return factory.normalStatementParser;
	}

	public static final StaticStatementParser getStaticStatementParser() {
		ParserFactory factory = ParserFactory.getImpl();
		if (factory.staticStatementParser == null) {
			factory.staticStatementParser = new StaticStatementParser();
		}
		return factory.staticStatementParser;
	}

	public static final DefinitionStatementParser getDefinitionStatementParser() {
		ParserFactory factory = ParserFactory.getImpl();
		if (factory.definitionStatementParser == null) {
			factory.definitionStatementParser = new DefinitionStatementParser();
		}
		return factory.definitionStatementParser;
	}
	
//	public static final CommendStatementParser getCommendStatementParser() {
//		ParserFactory factory = ParserFactory.getImpl();
//		if (factory.commendStatementParser == null) {
//			factory.commendStatementParser = new CommendStatementParser();
//		}
//		return factory.commendStatementParser;
//	}
	public static final TypeName.Parser getTypeNameParser() {
		ParserFactory factory = ParserFactory.getImpl();
		if (factory.typeNameParser == null) {
			factory.typeNameParser = new TypeName.Parser();
		}
		return factory.typeNameParser;
	}
}
