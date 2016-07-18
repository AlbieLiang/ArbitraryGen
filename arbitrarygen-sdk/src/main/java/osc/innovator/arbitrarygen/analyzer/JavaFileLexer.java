package osc.innovator.arbitrarygen.analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import osc.innovator.arbitrarygen.base.JavaFileObject;

/**
 * 
 * @author AlbieLiang
 *
 */
public class JavaFileLexer extends JavaLexer {

	private File mFile;
	
	public JavaFileLexer(File file) {
		super();
		if (file == null || !file.exists() || !file.isFile()) {
			throw new RuntimeException("file is null or do not exists or isn't a file.");
		}
		mFile = file;
	}

	public JavaFileObject start() {
		IReader reader = null;
		JavaFileObject javaFileObject = null;
		try {
			reader = new ReaderWrapper(new InputStreamReader(new FileInputStream(mFile)));
			javaFileObject = analyzeJavaFileObject(reader);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return javaFileObject;
	}
}