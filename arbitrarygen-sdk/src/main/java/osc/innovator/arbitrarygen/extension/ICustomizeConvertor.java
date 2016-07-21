package osc.innovator.arbitrarygen.extension;

import java.util.List;

import osc.innovator.arbitrarygen.block.TypeDefineCodeBlock;
import osc.innovator.arbitrarygen.core.ContextInfo;
import osc.innovator.arbitrarygen.template.RawTemplate;

public interface ICustomizeConvertor {

	TypeDefineCodeBlock convert(ContextInfo contextInfo, RawTemplate template);

	List<String> getSupportSuffixList();

	boolean canConvert(RawTemplate template);
}
