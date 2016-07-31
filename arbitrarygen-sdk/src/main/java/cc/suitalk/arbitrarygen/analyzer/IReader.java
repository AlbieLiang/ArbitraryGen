package cc.suitalk.arbitrarygen.analyzer;

import java.io.Closeable;
import java.io.IOException;

/**
 * 
 * @author AlbieLiang
 *
 */
public interface IReader extends Closeable {

	int read() throws IOException;
}
