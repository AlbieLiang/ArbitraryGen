package osc.innovator.arbitrarygen.extension;

import java.util.List;

import osc.innovator.arbitrarygen.block.TypeDefineCodeBlock;
import osc.innovator.arbitrarygen.template.RawTemplate;

public interface ICustomizeConvertor {
	TypeDefineCodeBlock convert(RawTemplate template);

	List<String> getSupportSuffixs();

	boolean canConvert(RawTemplate template);
}
