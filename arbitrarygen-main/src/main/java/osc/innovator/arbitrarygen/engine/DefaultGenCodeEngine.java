package osc.innovator.arbitrarygen.engine;

import java.io.File;
import java.util.List;

import osc.innovator.arbitrarygen.base.JavaFileObject;
import osc.innovator.arbitrarygen.block.TypeDefineCodeBlock;
import osc.innovator.arbitrarygen.core.BaseGenCodeEngine;
import osc.innovator.arbitrarygen.core.GenCodeTaskInfo;
import osc.innovator.arbitrarygen.core.IGenCodeEngine;
import osc.innovator.arbitrarygen.core.SourceFileInfo;
import osc.innovator.arbitrarygen.core.TemplateConstants;
import osc.innovator.arbitrarygen.extension.ICustomizeConvertor;
import osc.innovator.arbitrarygen.extension.ICustomizeParser;
import osc.innovator.arbitrarygen.template.RawTemplate;
import osc.innovator.arbitrarygen.utils.FileOperation;
import osc.innovator.arbitrarygen.utils.Log;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 * @since 1.0
 * 
 */
public class DefaultGenCodeEngine extends BaseGenCodeEngine {

	private static final String TAG = "CodeGen.DefaultGenCodeEngine";

	@Override
	public synchronized void doScanFiles(IGenCodeEngine engine) {
		// Auto pick the template file info;
		List<SourceFileInfo> list = FileOperation.scan(mConfigInfo.getSrcPath(), engine.getSupportSuffixs());
		if (list == null || list.isEmpty()) {
			Log.i(TAG, "scan finished, file list is empty.");
			return;
		}
		mSourceFileInfos.addAll(list);
	}

	@Override
	public synchronized void doParseFiles(IGenCodeEngine engine) {
		for (int i = 0; i < mSourceFileInfos.size(); i++) {
			SourceFileInfo info = mSourceFileInfos.get(i);
			File file = info.file;
			ICustomizeParser parser = engine.getFirstMatchParser(info.suffix);
			if (parser == null || !parser.canParse(info.suffix)) {
				continue;
			}
			List<RawTemplate> templates = parser.parse(file);
			if (templates != null && templates.size() > 0) {
				mRawTemplates.addAll(templates);
			}
		}
	}

	@Override
	public synchronized void doConvertTemplates(IGenCodeEngine engine) {
		ICustomizeConvertor convertor = null;
		final List<RawTemplate> roots = mRawTemplates;
		for (int i = 0; i < roots.size(); i++) {
			RawTemplate template = roots.get(i);
			List<RawTemplate> subTemplates = template.getElements();
			for (int j = 0; j < subTemplates.size(); j++) {
				RawTemplate t = subTemplates.get(j);
				convertor = engine.getFirstMatchConvertor(t);
				if (convertor == null || !convertor.canConvert(t)) {
					continue;
				}
				long startMsec = System.currentTimeMillis();
				mWrapperMgr.doWrap(t);
				long endMsec = System.currentTimeMillis();
				Log.v(TAG, ">>>>>>doWrap(RawTemplate template) cost " + (endMsec - startMsec) + " msec.");
				TypeDefineCodeBlock ct = convertor.convert(t);
				ct.Token = t.Token;
				mWrapperMgr.doWrap(ct);
				long msec = System.currentTimeMillis();
				Log.v(TAG, ">>>>>>doWrap(TypeDefineCodeBlock template) cost " + (msec - endMsec) + " msec.");
				if (ct != null) {
					GenCodeTaskInfo info = new GenCodeTaskInfo();
					// Need check
					info.FileName = ct.getName().getName();
					JavaFileObject javaFileObject = new JavaFileObject();
					javaFileObject.addTypeDefineCodeBlock(ct);
					info.javaFileObject = javaFileObject;
					// package
					if (Util.isNullOrNil(javaFileObject.getPackage())) {
						String pkg = t.getAttributes().get(TemplateConstants.TEMPLATE_KEYWORDS_PACKAGE);
						if (Util.isNullOrNil(pkg)) {
							pkg = template.getAttributes().get(TemplateConstants.TEMPLATE_KEYWORDS_PACKAGE);
						}
						javaFileObject.setPackage(pkg);
						info.RootDir = mConfigInfo.getDestPath() + Util.getPackageDir(javaFileObject);
					}
					// import
					String importStr = t.getAttributes().get(TemplateConstants.TEMPLATE_KEYWORDS_IMPORT);
					if (!Util.isNullOrNil(importStr)) {
						javaFileObject.addImports(Util.extractStrList(importStr, ","));
					}
					importStr = template.getAttributes().get(TemplateConstants.TEMPLATE_KEYWORDS_IMPORT);
					if (!Util.isNullOrNil(importStr)) {
						javaFileObject.addImports(Util.extractStrList(importStr, ","));
					}
					mTasks.add(info);
				}
			}
		}
	}
}
