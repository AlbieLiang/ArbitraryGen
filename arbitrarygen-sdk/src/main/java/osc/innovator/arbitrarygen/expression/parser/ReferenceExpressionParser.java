package osc.innovator.arbitrarygen.expression.parser;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.base.BaseExpressionParser;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.core.Word.WordType;
import osc.innovator.arbitrarygen.expression.ReferenceExpression;
import osc.innovator.arbitrarygen.extension.ILexer;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ReferenceExpressionParser extends BaseExpressionParser {

	private boolean mVariableOnly;

	public ReferenceExpressionParser(boolean variableOnly) {
		mVariableOnly = variableOnly;
	}

	@Override
	public ReferenceExpression parse(IReader reader, ILexer lexer, Word curWord) throws IOException {
		ReferenceExpression expression = new ReferenceExpression();
		Word word = null;
		if (curWord != null) {
			if (curWord.type != WordType.STRING) {
				throw new RuntimeException("parse ReferenceExpression error.(current word is : " + curWord.value + ").");
			}
			expression.appendNode(curWord);
			word = nextWord(reader, lexer);
			if (!".".equals(word.value)) {
				return expression;
			}
			expression.appendNode(word);
		}
		while ((word = nextWord(reader, lexer)) != null && word.type == WordType.STRING) {
			expression.appendNode(word);
			if ((word = nextWord(reader, lexer)) != null) {
				if (".".equals(word.value)) {
					expression.appendNode(word);
					continue;
				} else if ("*".equals(word.value)) {
					if (!mVariableOnly) {
						expression.appendNode(word);
						nextWord(reader, lexer);
						break;
					} else {
						throw new RuntimeException("'*' can not place here.");
					}
				}
			}
			break;
		}
		return expression;
	}
}
