package osc.innovator.arbitrarygen.extension;

import java.io.File;
import java.util.List;

import osc.innovator.arbitrarygen.template.RawTemplate;

public interface ICustomizeParser {
	List<RawTemplate> parse(File file);

	boolean canParse(String suffix);
}
