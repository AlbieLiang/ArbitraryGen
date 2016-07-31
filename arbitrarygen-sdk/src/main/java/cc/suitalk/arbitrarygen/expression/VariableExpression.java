package cc.suitalk.arbitrarygen.expression;

import cc.suitalk.arbitrarygen.core.Word;

/**
 * 
 * @author AlbieLiang
 *
 */
public class VariableExpression extends ReferenceExpression {

	public VariableExpression(String name) {
		super();
		setVariable(name);
	}
	
	public VariableExpression(Word word) {
		super();
		appendNode(word);
	}
}
