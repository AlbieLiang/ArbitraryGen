package osc.innovator.tools.arbitrarygen.demo.greendao;

import osc.innovator.arbitrarygen.block.TypeDefineCodeBlock;
import osc.innovator.arbitrarygen.core.ContextInfo;
import osc.innovator.arbitrarygen.extension.ICustomizeConvertor;
import osc.innovator.arbitrarygen.template.RawTemplate;
import osc.innovator.arbitrarygen.utils.Log;

/**
 * 
 * @author albieliang
 * 
 */
public class GreenDaoConvertor implements ICustomizeConvertor {

	private static final String TAG = "GreenDao.GreenDaoConvertor";

	private static final String TAG_NAME = "table";

	public GreenDaoConvertor() {
	}

	@Override
	public TypeDefineCodeBlock convert(ContextInfo contextInfo, RawTemplate rawTemplate) {
		if (rawTemplate == null) {
			return null;
		}
		TypeDefineCodeBlock typeDefineCodeBlock = DatabaseGenerator.doGen(contextInfo, rawTemplate);
		Log.d(TAG, "do generate green dao code.");
		return typeDefineCodeBlock;
	}

	@Override
	public boolean canConvert(RawTemplate template) {
		return template != null && TAG_NAME.equalsIgnoreCase(template.getName());
	}
}
