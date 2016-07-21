package osc.innovator.arbitrarygen.core;

import java.util.List;

import osc.innovator.arbitrarygen.extension.ICustomizeConvertor;
import osc.innovator.arbitrarygen.extension.ICustomizeParser;
import osc.innovator.arbitrarygen.extension.IDirector;
import osc.innovator.arbitrarygen.extension.ITemplateWrapper;
import osc.innovator.arbitrarygen.template.RawTemplate;

/**
 * 
 * @author AlbieLiang
 *
 */
public interface IGenCodeEngine {

	void start();

	void setConfigInfo(ConfigInfo configInfo);

	void addDirector(IDirector director);

	void addParser(ICustomizeParser parser);

	void addConvertor(ICustomizeConvertor convertor);

	void addWrapper(ITemplateWrapper wrapper);

	void doScanFiles(IGenCodeEngine engine);

	void doParseFiles(IGenCodeEngine engine);

	void doConvertTemplates(IGenCodeEngine engine);

	void doGenCode(IGenCodeEngine engine);

	ICustomizeParser getFirstMatchParser(String suffix);

	List<String> getSupportSuffixList();

	void addAllSuffixList(List<String> suffixList);

	ICustomizeConvertor getFirstMatchConvertor(RawTemplate rawTemplate);
}
