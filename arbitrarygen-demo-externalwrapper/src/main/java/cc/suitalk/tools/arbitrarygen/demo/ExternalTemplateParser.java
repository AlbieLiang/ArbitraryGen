package cc.suitalk.tools.arbitrarygen.demo;

import java.io.File;
import java.util.List;

import cc.suitalk.arbitrarygen.extension.ICustomizeParser;
import cc.suitalk.arbitrarygen.template.RawTemplate;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.XmlUtils;

public class ExternalTemplateParser  implements ICustomizeParser {

	private static final String TAG = "AG.demo.ExternalTemplateParser";

	@Override
	public boolean canParse(String suffix) {
		return "ext".equalsIgnoreCase(suffix);
	}

	@Override
	public List<RawTemplate> parse(File file) {
		if (file == null || !file.exists() || !file.isFile()) {
			Log.e(TAG, "The file is null or do not exists or isn't a file.");
			return null;
		}
		return XmlUtils.getXmlNotes(file);
	}

}
