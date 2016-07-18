package osc.innovator.arbitrarygen.extension;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.core.Word;

public interface ILexer {

	Word getWord(IReader reader) throws IOException;
}
