package osc.innovator.arbitrarygen.base;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.extension.ILexer;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BaseExpressionParser extends BaseCodeParser {

	public abstract Expression parse(IReader reader, ILexer lexer, Word curWord) throws IOException;
}
