package cc.suitalk.arbitrarygen.base;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.extension.ILexer;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BaseExpressionParser extends BaseCodeParser {

	public abstract Expression parse(IReader reader, ILexer lexer, Word curWord) throws IOException;
}
