package cc.suitalk.arbitrarygen.expression.parser;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseExpressionParser;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.extension.ILexer;
import cc.suitalk.arbitrarygen.utils.Util;

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
