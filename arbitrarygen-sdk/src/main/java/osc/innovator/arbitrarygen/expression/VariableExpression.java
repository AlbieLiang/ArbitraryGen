package osc.innovator.arbitrarygen.expression;

import osc.innovator.arbitrarygen.core.Word;

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
