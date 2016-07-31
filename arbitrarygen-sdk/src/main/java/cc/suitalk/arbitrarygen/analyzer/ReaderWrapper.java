package cc.suitalk.arbitrarygen.analyzer;

import java.io.IOException;
import java.io.Reader;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ReaderWrapper implements IReader {

	private Reader mReader;
	
	public ReaderWrapper(Reader reader) {
		this.mReader = reader;
	}
	
	@Override
	public int read() throws IOException {
		return mReader.read();
	}

	@Override
	public void close() throws IOException {
		mReader.close();
	}
}
