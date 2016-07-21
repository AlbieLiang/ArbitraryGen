package osc.innovator.tools.arbitrarygen.demo.greendao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import osc.innovator.arbitrarygen.block.TypeDefineCodeBlock;
import osc.innovator.arbitrarygen.core.ContextInfo;
import osc.innovator.arbitrarygen.core.TemplateConstants;
import osc.innovator.arbitrarygen.extension.ICustomizeConvertor;
import osc.innovator.arbitrarygen.template.RawTemplate;
import osc.innovator.arbitrarygen.utils.AnalyzerHelper;
import osc.innovator.arbitrarygen.utils.Log;

/**
 * 
 * @author albieliang
 * 
 */
public class GreenDaoAnalyzer implements ICustomizeConvertor {

	private static final String TAG = "GreenDao.GreenDaoAnalyzer";

	private static final String TAG_NAME = "table";

	public GreenDaoAnalyzer() {
	}

	@Override
	public TypeDefineCodeBlock convert(ContextInfo contextInfo, RawTemplate rawTemplate) {
		if (rawTemplate == null) {
			return null;
		}
		DatabaseGenerator.doGen(contextInfo, rawTemplate);
		Log.d(TAG, "do generate green dao code.");
		return null;
	}

	@Override
	public boolean canConvert(RawTemplate template) {
		return template != null && TAG_NAME.equalsIgnoreCase(template.getName());
	}

	@Override
	public List<String> getSupportSuffixList() {
		List<String> supportSuffixList = new LinkedList<String>();
		supportSuffixList.add("db");
		return supportSuffixList;
	}
}
