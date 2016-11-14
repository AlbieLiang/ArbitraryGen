package cc.suitalk.arbitrarygen.impl;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.extension.CustomizeParser;
import cc.suitalk.arbitrarygen.template.RawTemplate;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.XmlUtils;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DefaultRawTemplateParser implements CustomizeParser {

	private static final String TAG = "AG.DefaultRawTemplateParser";

	private List<String> mSuffixList;

	public DefaultRawTemplateParser() {
		mSuffixList = new LinkedList<>();
	}

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
		return suffix!= null && mSuffixList.contains(suffix.toLowerCase());
	}

	public void addSuffixList(List<String> suffixList) {
		if (suffixList == null || suffixList.isEmpty()) {
			return;
		}
		for (String suffix : suffixList) {
			addSuffix(suffix);
		}
	}

	public void addSuffix(String suffix) {
		if (suffix == null || suffix.length() == 0) {
			return;
		}
		mSuffixList.add(suffix.toLowerCase());
	}
}
