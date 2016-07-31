package cc.suitalk.tools.arbitrarygen.demo.greendao;

import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ContextInfo;
import cc.suitalk.arbitrarygen.extension.ICustomizeConvertor;
import cc.suitalk.arbitrarygen.template.RawTemplate;
import cc.suitalk.arbitrarygen.utils.Log;

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
