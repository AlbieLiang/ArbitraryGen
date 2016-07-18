package osc.innovator.arbitrarygen.core;

import java.io.File;

/**
 * 
 * @author AlbieLiang
 *
 */
public class SourceFileInfo {
	public String suffix;
	public File file;

	public SourceFileInfo(String suffix, File file) {
		this.suffix = suffix;
		this.file = file;
	}
}