package cc.suitalk.arbitrarygen.core;

import java.util.List;

import cc.suitalk.arbitrarygen.extension.ICustomizeConvertor;
import cc.suitalk.arbitrarygen.extension.ICustomizeParser;
import cc.suitalk.arbitrarygen.extension.ITemplateWrapper;
import cc.suitalk.arbitrarygen.template.RawTemplate;

/**
 * 
 * @author AlbieLiang
 *
 */
public interface IGenCodeEngine {

	void start();

	void setConfigInfo(ConfigInfo configInfo);

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
