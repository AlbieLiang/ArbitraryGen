package osc.innovator.arbitrarygen.impl;

import java.io.File;
import java.util.List;

import osc.innovator.arbitrarygen.extension.ICustomizeParser;
import osc.innovator.arbitrarygen.template.RawTemplate;
import osc.innovator.arbitrarygen.utils.Log;
import osc.innovator.arbitrarygen.utils.XmlUtils;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DefaultRawTemplateParser implements ICustomizeParser {

	private static final String TAG = "CodeGen.DefaultRawTemplateParser";

	@Override
	public List<RawTemplate> parse(File file) {
		if (file == null || !file.exists() || !file.isFile()) {
			Log.e(TAG, "The file is null or do not exists or isn't a file.");
			return null;
		}
		return XmlUtils.getXmlNotes(file);
	}

	@Override
	public boolean canParse(String suffix) {
		return "xml".equalsIgnoreCase(suffix);
	}
}
