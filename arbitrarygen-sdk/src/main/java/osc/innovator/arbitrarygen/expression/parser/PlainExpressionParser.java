package osc.innovator.arbitrarygen.expression.parser;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.base.BaseExpressionParser;
import osc.innovator.arbitrarygen.base.Expression;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.extension.ILexer;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * The suffix is ';'.
 * 
 * @author AlbieLiang
 *
 */
public class PlainExpressionParser extends BaseExpressionParser {

	@Override
	public Expression parse(IReader reader, ILexer lexer, Word curWord) throws IOException {
		return Util.extractExpression(reader, lexer, curWord, this, ";");
	}
}
