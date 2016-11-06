package cc.suitalk.arbitrarygen.extension;

import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ContextInfo;
import cc.suitalk.arbitrarygen.template.RawTemplate;

public interface ICustomizeConverter {

	TypeDefineCodeBlock convert(ContextInfo contextInfo, RawTemplate template);

	boolean canConvert(RawTemplate template);
}
