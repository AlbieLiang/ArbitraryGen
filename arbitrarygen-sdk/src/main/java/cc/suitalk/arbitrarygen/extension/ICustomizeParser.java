package cc.suitalk.arbitrarygen.extension;

import java.io.File;
import java.util.List;

import cc.suitalk.arbitrarygen.template.RawTemplate;

public interface ICustomizeParser {
	List<RawTemplate> parse(File file);

	boolean canParse(String suffix);
}
