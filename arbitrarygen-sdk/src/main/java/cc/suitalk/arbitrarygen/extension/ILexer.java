package cc.suitalk.arbitrarygen.extension;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.core.Word;

public interface ILexer {

	Word getWord(IReader reader) throws IOException;
}
