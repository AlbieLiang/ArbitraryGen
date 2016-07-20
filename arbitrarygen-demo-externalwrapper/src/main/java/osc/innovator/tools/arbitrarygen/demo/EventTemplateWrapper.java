package osc.innovator.tools.arbitrarygen.demo;

import java.util.List;

import osc.innovator.arbitrarygen.base.Session;
import osc.innovator.arbitrarygen.block.ConstructorMethodCodeBlock;
import osc.innovator.arbitrarygen.block.FieldCodeBlock;
import osc.innovator.arbitrarygen.block.MethodCodeBlock;
import osc.innovator.arbitrarygen.block.TypeDefineCodeBlock;
import osc.innovator.arbitrarygen.core.KeyWords;
import osc.innovator.arbitrarygen.core.TemplateConstants;
import osc.innovator.arbitrarygen.extension.ITemplateWrapper;
import osc.innovator.arbitrarygen.model.DefaultKeyValuePair;
import osc.innovator.arbitrarygen.statement.NormalStatement;
import osc.innovator.arbitrarygen.template.FastAssignRawTemplate;
import osc.innovator.arbitrarygen.template.RawTemplate;
import osc.innovator.arbitrarygen.utils.TemplateAttributeHelper;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 * @since 1.0
 * 
 */
public class EventTemplateWrapper implements ITemplateWrapper {

	private static final String TAG = "CodeGen.EventTemplateWrapper";
	private static final String TAG_NAME = "Event";
	private Session session;

	public EventTemplateWrapper() {
		session = new Session();
		session.Token = TAG.hashCode();
	}

	@Override
	public boolean doWrap(RawTemplate template) {
		if (template != null && TAG_NAME.equalsIgnoreCase(template.getName())) {
			TemplateAttributeHelper.setAttribute(template, TemplateConstants.TEMPLATE_KEYWORDS_PARENT, "IEvent");
			String name = template.getAttributes().get(TemplateConstants.TEMPLATE_KEYWORDS_NAME);
			TemplateAttributeHelper.appendAttribute(template, TemplateConstants.TEMPLATE_KEYWORDS_NAME, TAG_NAME);
			TemplateAttributeHelper.appendAttribute(template, TemplateConstants.TEMPLATE_KEYWORDS_IMPORT,
					"com.albie.test.sdk.IEvent", KeyWords.V_JAVA_KEYWORDS_SIGN_COMMA);
			// Add a field
			FastAssignRawTemplate fart = new FastAssignRawTemplate(KeyWords.V_JAVA_KEYWORDS_PUBLIC, true, true,
					KeyWords.V_JAVA_KEYWORDS_DATA_BASE_TYPE_STRING, "ID");
			fart.setAttrDefault("\"" + name + "\"").setName(TemplateConstants.TEMPLATE_KEYWORDS_FIELD);
			List<RawTemplate> elements = template.getElements();
			elements.add(fart);
			// Assign session token.
			template.Token = session.Token;
			return true;
		}
		return false;
	}

	@Override
	public boolean doWrap(TypeDefineCodeBlock template) {
		if (template != null && template.Token == session.Token) {//
			for (int i = 0; i < template.countOfTypeDefCodeBlocks(); i++) {
				TypeDefineCodeBlock ct = template.getTypeDefCodeBlock(i);
				if (KeyWords.V_JAVA_KEYWORDS_CLASS.equals(ct.getType().genCode(""))) {
					FieldCodeBlock field = new FieldCodeBlock();
					field.setName(Util.changeFirstChatToLower(ct.getName()));
					field.setType(ct.getName());
					field.setDefault(KeyWords.V_JAVA_KEYWORDS_NEW + " " + ct.getName().getName() + "()");
					ct.setIsStatic(true);
					ct.setIsFinal(true);
					template.addField(field);
				}
			}
			MethodCodeBlock t = new ConstructorMethodCodeBlock(template, new DefaultKeyValuePair("callback", "ICallback"));
			t.addStatement(new NormalStatement("this.callback = callback"));
			template.addMethod(t);

			t = new ConstructorMethodCodeBlock(template);
			t.addStatement(new NormalStatement("id = ID"));
			template.addMethod(t);
			template.setIsFinal(true);
			return true;
		}
		return false;
	}
}
