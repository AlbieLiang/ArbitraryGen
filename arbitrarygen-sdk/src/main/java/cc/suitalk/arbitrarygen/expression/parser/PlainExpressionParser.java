package cc.suitalk.arbitrarygen.expression.parser;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseExpressionParser;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * The suffix is ';'.
 * 
 * @author AlbieLiang
 *
 */
public class PlainExpressionParser extends BaseExpressionParser {

	@Override
	public Expression parse(IReader reader, Lexer lexer, Word curWord) throws IOException {
		return Util.extractExpression(reader, lexer, curWord, this, ";");
	}
}
