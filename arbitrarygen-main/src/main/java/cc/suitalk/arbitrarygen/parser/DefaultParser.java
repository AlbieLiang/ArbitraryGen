package cc.suitalk.arbitrarygen.parser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.core.CodeGenerator;
import cc.suitalk.arbitrarygen.core.ContextInfo;
import cc.suitalk.arbitrarygen.core.GenCodeTaskInfo;
import cc.suitalk.arbitrarygen.core.JarClassLoaderWrapper;
import cc.suitalk.arbitrarygen.core.TemplateConstants;
import cc.suitalk.arbitrarygen.core.TemplateConverterMgr;
import cc.suitalk.arbitrarygen.core.TemplateParserMgr;
import cc.suitalk.arbitrarygen.core.TemplateWrapperMgr;
import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.SourceFileParser;
import cc.suitalk.arbitrarygen.extension.ICustomizeConverter;
import cc.suitalk.arbitrarygen.extension.ICustomizeGenerator;
import cc.suitalk.arbitrarygen.extension.ICustomizeParser;
import cc.suitalk.arbitrarygen.extension.ITemplateWrapper;
import cc.suitalk.arbitrarygen.impl.DefaultRawTemplateParser;
import cc.suitalk.arbitrarygen.template.RawTemplate;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.JSONArgsUtils;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/02.
 */
public class DefaultParser implements SourceFileParser<JSONObject, JSONObject> {

    private static final String TAG = "AG.DefaultParser";

    private JSONObject mArgs;
    private DefaultRawTemplateParser mDefaultRawTemplateParser;

    private TemplateParserMgr mParserMgr;
    private TemplateConverterMgr mConverterMgr;
    protected TemplateWrapperMgr mWrapperMgr;

    public DefaultParser(AGCore core, JSONObject args) {
        mArgs = args;
        mParserMgr = new TemplateParserMgr();
        mConverterMgr = new TemplateConverterMgr();
        mWrapperMgr = new TemplateWrapperMgr();

        // load extension jar
        JSONObject extensionJson = args.optJSONObject(ArgsConstants.EXTERNAL_ARGS_KEY_EXTENSION);
        if (extensionJson != null) {
            JSONArray jarArray = JSONArgsUtils.getJSONArray(extensionJson, ArgsConstants.EXTERNAL_ARGS_KEY_JAR, true);
            JarClassLoaderWrapper loader = core.getJarClassLoader();
            if (jarArray != null && !jarArray.isEmpty()) {
                for (int i = 0; i < jarArray.size(); i++) {
                    String jar = jarArray.optString(i);
                    if (Util.isNullOrNil(jar)) {
                        continue;
                    }
                    File file = new File(jar);
                    if (!loader.contains(file) && loader.addJar(file)) {
                        Log.i(TAG, "Loaded Jar(%s) into ClassLoader.", jar);
                    }
                }
            }
            JSONArray classArray = JSONArgsUtils.getJSONArray(extensionJson, ArgsConstants.EXTERNAL_ARGS_KEY_CLASS, true);
            if (classArray != null && !classArray.isEmpty()) {
                for (int i = 0; i < classArray.size(); i++) {
                    String tClass = classArray.optString(i);
                    if (Util.isNullOrNil(tClass)) {
                        continue;
                    }
                    try {
                        Class<?> clazz = loader.loadClass(tClass);
                        Object o = clazz.newInstance();
                        if (o instanceof ICustomizeParser) {
                            mParserMgr.addParser((ICustomizeParser) o);
                        } else if (o instanceof ICustomizeConverter) {
                            mConverterMgr.addConverter((ICustomizeConverter) o);
                        } else if (o instanceof ITemplateWrapper) {
                            mWrapperMgr.addWrapper((ITemplateWrapper) o);
                        }
                    } catch (MalformedURLException e) {
                        Log.e(TAG, "load class error : %s", e);
                    } catch (ClassNotFoundException e) {
                        Log.e(TAG, "load class error : %s", e);
                    } catch (InstantiationException e) {
                        Log.e(TAG, "load class error : %s", e);
                    } catch (IllegalAccessException e) {
                        Log.e(TAG, "load class error : %s", e);
                    }
                }
            }
        }
        mDefaultRawTemplateParser = new DefaultRawTemplateParser();
        mParserMgr.addParser(mDefaultRawTemplateParser);
    }

    @Override
    public boolean match(String suffix) {
        if (suffix == null) {
            return false;
        }
        return mParserMgr.getFirstMatchParser(suffix.toLowerCase()) != null;
    }

    @Override
    public JSONObject parse(JSONObject args, File file) {
        Log.v(TAG, "parse file(%s)", file);
        String suffix = Util.getSuffix(file.getName());
        if (Util.isNullOrNil(suffix)) {
            Log.w(TAG, "parse failed, suffix of the file(%s) is null or nil.", file);
            return null;
        }
        ICustomizeParser parser = mParserMgr.getFirstMatchParser(suffix);
        if (parser == null || !parser.canParse(suffix)) {
            Log.i(TAG, "parse failed, do not has a parser can parse the file(%s).", file);
            return null;
        }
        List<RawTemplate> templates = parser.parse(file);
        if (templates != null && templates.size() > 0) {
            doConvert(templates);
        }
        return null;
    }

    private void doConvert(List<RawTemplate> templates) {
        final String destPath = mArgs.getString(ArgsConstants.EXTERNAL_ARGS_KEY_DEST);
        final List<RawTemplate> roots = templates;
        for (int i = 0; i < roots.size(); i++) {
            RawTemplate template = roots.get(i);
            List<RawTemplate> subTemplates = template.getElements();
            for (int j = 0; j < subTemplates.size(); j++) {
                RawTemplate t = subTemplates.get(j);
                ICustomizeConverter converter = mConverterMgr.getFirstMatchConverter(t);
                if (converter == null || !converter.canConvert(t)) {
                    continue;
                }
                long startMSec = System.currentTimeMillis();
                ContextInfo contextInfo = new ContextInfo(template.getAttributes());

                mWrapperMgr.doWrap(contextInfo, t);
                long endMsec = System.currentTimeMillis();
                Log.v(TAG, ">>>>>>doWrap(RawTemplate template : %s) cost %d msec.", t.getName(), (endMsec - startMSec));
                TypeDefineCodeBlock ct = converter.convert(contextInfo, t);
                if (ct == null) {
                    continue;
                }
                ct.Token = t.Token;
                mWrapperMgr.doWrap(contextInfo, ct);
                long msec = System.currentTimeMillis();
                Log.v(TAG, ">>>>>>doWrap(TypeDefineCodeBlock template : %s) cost %d msec.", t.getName(), (msec - endMsec));

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
                    info.RootDir = destPath + Util.getPackageDir(javaFileObject);
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
                doGenCode(info);
            }
        }
    }

    private void doGenCode(GenCodeTaskInfo info) {
        JavaFileObject javaFileObject = info.javaFileObject;
        ICustomizeGenerator generator = new CodeGenerator(javaFileObject);
        FileOperation.saveToFile(info, generator.genCode());
    }

    /**
     * @param suffix
     */
    public void addSuffix(String suffix) {
        if (suffix == null) {
            return;
        }
        mDefaultRawTemplateParser.addSuffix(suffix);
    }

    /**
     * @param suffixList
     */
    public void addSuffixList(List<String> suffixList) {
        if (suffixList == null || suffixList.isEmpty()) {
            return;
        }
        for (String suffix : suffixList) {
            addSuffix(suffix);
        }
    }
}