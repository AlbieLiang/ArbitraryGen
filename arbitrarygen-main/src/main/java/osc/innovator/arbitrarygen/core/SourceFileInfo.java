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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SourceFileInfo(");
		builder.append("suffix:");
		builder.append(suffix);
		builder.append(", ");
		builder.append("path:");
		builder.append(file.getAbsolutePath());
		builder.append(")");
		return builder.toString();
	}
}