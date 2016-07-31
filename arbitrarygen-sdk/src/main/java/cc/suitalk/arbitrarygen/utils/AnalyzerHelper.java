package cc.suitalk.arbitrarygen.utils;

import java.util.List;
import java.util.Map;

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.block.FieldCodeBlock;
import cc.suitalk.arbitrarygen.block.MethodCodeBlock;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.KeyWords;
import cc.suitalk.arbitrarygen.core.TemplateConstants;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.expression.VariableExpression;
import cc.suitalk.arbitrarygen.model.KeyValuePair;
import cc.suitalk.arbitrarygen.model.TypeName;
import cc.suitalk.arbitrarygen.statement.NormalStatement;
import cc.suitalk.arbitrarygen.statement.ReturnStatement;
import cc.suitalk.arbitrarygen.template.RawTemplate;

/**
 * 
 * @author AlbieLiang
 *
 */
public final class AnalyzerHelper {

	public static String getName(RawTemplate rawTemplate) {
		if (rawTemplate == null) {
			return null;
		}
		Map<String, String> attrs = rawTemplate.getAttributes();
		String name = attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_NAME);
		if (Util.isNullOrNil(name)) {
			name = rawTemplate.getName();
		}
		return name;
	}
	
	public static TypeName getTypeName(RawTemplate rawTemplate) {
		if (rawTemplate == null) {
			return null;
		}
		Map<String, String> attrs = rawTemplate.getAttributes();
		String name = attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_NAME);
		if (Util.isNullOrNil(name)) {
			name = rawTemplate.getName();
		}
		return Util.createSimpleTypeName(name);
	}

	public static void extractFieldsAndSubTemplate(TypeDefineCodeBlock typeDef, RawTemplate rawTemplate) {
		if (typeDef == null || rawTemplate == null) {
			return;
		}
		List<RawTemplate> elements = rawTemplate.getElements();
		if (elements == null) {
			return;
		}
		for (int i = 0; i < elements.size(); i++) {
			RawTemplate t = elements.get(i);
			if (t == null || Util.isNullOrNil(t.getName())) {
				continue;
			}
			if (TemplateConstants.TEMPLATE_KEYWORDS_FIELD.equalsIgnoreCase(t.getName())) {
				typeDef.addField(createFieldCodeBlock(t));
			} else if (TemplateConstants.TEMPLATE_KEYWORDS_METHOD.equalsIgnoreCase(t.getName())) {
				typeDef.addMethod(createMethodCodeBlock(typeDef, t));
			} else {
				typeDef.addTypeDefineCodeBlock(createTypeDefineCodeBlock(t));
			}
		}
	}

	public static JavaFileObject createJavaFileObject(RawTemplate rawTemplate) {
		JavaFileObject javaFileObject = new JavaFileObject();
		if (rawTemplate == null) {
			return null;
		}
		Map<String, String> attrs = rawTemplate.getAttributes();
		javaFileObject.setPackage(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_PACKAGE));
		javaFileObject.addImports(Util.extractStrList(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_IMPORT), ","));
		TypeDefineCodeBlock codeblock = createTypeDefineCodeBlock(rawTemplate);
		if (codeblock != null) {
			javaFileObject.addTypeDefineCodeBlock(codeblock);
		}
		return javaFileObject;
	}

	public static TypeDefineCodeBlock createTypeDefineCodeBlock(RawTemplate rawTemplate) {
		if (rawTemplate == null) {
			return null;
		}
		Map<String, String> attrs = rawTemplate.getAttributes();
		TypeDefineCodeBlock t = new TypeDefineCodeBlock();
		if (attrs != null) {
			t.setModifier(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_MODIFIER));
			t.setIsStatic(new Boolean(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_IS_STATIC)));
			t.setIsFinal(new Boolean(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_IS_FINAL)));
			t.setType(Util.createSimpleTypeName(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_TYPE)));
			t.setName(Util.createSimpleTypeName(AnalyzerHelper.getName(rawTemplate)));
			t.setParent(Util.createSimpleTypeName(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_PARENT)));
			t.addInterfaces(Util.convertTo(Util.extractStrList(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_INTERFACES), ",")));
			AnalyzerHelper.extractFieldsAndSubTemplate(t, rawTemplate);
		}
		// Deal default case
		if (t.getType() == null) {
			t.setType(Util.createSimpleTypeName(KeyWords.V_JAVA_KEYWORDS_CLASS));
		}
		if (Util.isNullOrNil(t.getModifier())) {
			t.setModifier(KeyWords.V_JAVA_KEYWORDS_PUBLIC);
		}
		return t;
	}

	private static FieldCodeBlock createFieldCodeBlock(RawTemplate rawTemplate) {
		if (rawTemplate == null) {
			return null;
		}
		Map<String, String> attrs = rawTemplate.getAttributes();
		FieldCodeBlock t = new FieldCodeBlock();
		if (attrs != null) {
			t.setModifier(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_MODIFIER));
			t.setIsStatic(new Boolean(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_IS_STATIC)));
			t.setIsFinal(new Boolean(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_IS_FINAL)));
			t.setType(Util.createSimpleTypeName(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_TYPE)));
			t.setName(Util.createSimpleTypeName(AnalyzerHelper.getName(rawTemplate)));
			t.setDefault(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_DEFAULT));
			t.setGenGetter(new Boolean(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_GEN_GETTER)));
			t.setGenSetter(new Boolean(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_GEN_SETTER)));
			// Deal default value
			if (t.getType() == null) {
				t.setType(Util.createSimpleTypeName(KeyWords.V_JAVA_KEYWORDS_DATA_BASE_TYPE_OBJECT));
			}
			if (Util.isNullOrNil(t.getModifier())) {
				t.setModifier(KeyWords.V_JAVA_KEYWORDS_PUBLIC);
			}
		}
		return t;
	}

	private static MethodCodeBlock createMethodCodeBlock(TypeDefineCodeBlock typeDef, RawTemplate rawTemplate) {
		if (typeDef == null || rawTemplate == null) {
			return null;
		}
		Map<String, String> attrs = rawTemplate.getAttributes();
		MethodCodeBlock t = new MethodCodeBlock();
		t.setCodeBlock(new PlainCodeBlock());
		if (attrs != null) {
			t.setModifier(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_MODIFIER));
			t.setIsStatic(new Boolean(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_IS_STATIC)));
			t.setIsFinal(new Boolean(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_IS_FINAL)));
			t.setType(Util.createSimpleTypeName(attrs.get(TemplateConstants.TEMPLATE_KEYWORDS_TYPE)));
			t.setName(Util.createSimpleTypeName(AnalyzerHelper.getName(rawTemplate)));
			List<RawTemplate> args = rawTemplate.getElements();
			if (args != null && args.size() > 0) {
				for (int i = 0; i < args.size(); i++) {
					RawTemplate rt = args.get(i);
					if (rt == null) {
						continue;
					}
					if (TemplateConstants.TEMPLATE_KEYWORDS_ARG.equalsIgnoreCase(rt.getName())) {
						String name = rt.getAttributes().get(TemplateConstants.TEMPLATE_KEYWORDS_NAME);
						String type = rt.getAttributes().get(TemplateConstants.TEMPLATE_KEYWORDS_TYPE);
						if (!Util.isNullOrNil(name) && !Util.isNullOrNil(type)) {
							t.addArg(new KeyValuePair<Word, TypeName>(Util.createKeyWord(name), Util.createSimpleTypeName(type)));
						}
					} else if (TemplateConstants.TEMPLATE_KEYWORDS_RESULT.equalsIgnoreCase(rt.getName())) {
						// Insert result
						t.addStatement(new ReturnStatement(new VariableExpression(rt.getContent())));
					} else if (TemplateConstants.TEMPLATE_KEYWORDS_BLOCK.equalsIgnoreCase(rt.getName())) {

						// Insert method block
						List<RawTemplate> statementTemplates = rt.getElements();
						if (statementTemplates != null && statementTemplates.size() > 0) {
							for (int j = 0; j < statementTemplates.size(); j++) {
								RawTemplate sRT = statementTemplates.get(j);
								if (sRT != null && TemplateConstants.TEMPLATE_KEYWORDS_STATEMENT.equalsIgnoreCase(sRT.getName()) && !Util.isNullOrNil(sRT.getContent())) {
									t.addStatement(new NormalStatement(sRT.getContent()));
								}
							}
						}
						//
						if (!Util.isNullOrNil(rt.getContent())) {
							t.addStatement(new NormalStatement(rt.getContent()));
						}
					}
				}
			}
			// Deal default value
			if (t.getType() == null && typeDef.getName() != null && !typeDef.getName().getName().equals(t.getName().getName())) {
				t.setType(Util.createSimpleTypeName(KeyWords.V_JAVA_KEYWORDS_DATA_BASE_TYPE_VOID));
			}
			if (Util.isNullOrNil(t.getModifier())) {
				t.setModifier(KeyWords.V_JAVA_KEYWORDS_PUBLIC);
			}
		}
		return t;
	}
}
