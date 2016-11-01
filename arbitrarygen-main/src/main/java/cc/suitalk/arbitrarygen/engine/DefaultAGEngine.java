package cc.suitalk.arbitrarygen.engine;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ArbitraryGenCore;
import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.core.CodeGenerator;
import cc.suitalk.arbitrarygen.core.ContextInfo;
import cc.suitalk.arbitrarygen.core.GenCodeTaskInfo;
import cc.suitalk.arbitrarygen.core.JarClassLoaderWrapper;
import cc.suitalk.arbitrarygen.core.TemplateConstants;
import cc.suitalk.arbitrarygen.core.TemplateConvertorMgr;
import cc.suitalk.arbitrarygen.core.TemplateParserMgr;
import cc.suitalk.arbitrarygen.core.TemplateWrapperMgr;
import cc.suitalk.arbitrarygen.core.base.ArbitraryGenEngine;
import cc.suitalk.arbitrarygen.core.base.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.core.base.SourceFileParser;
import cc.suitalk.arbitrarygen.extension.ICustomizeConvertor;
import cc.suitalk.arbitrarygen.extension.ICustomizeGenerator;
import cc.suitalk.arbitrarygen.extension.ICustomizeParser;
import cc.suitalk.arbitrarygen.extension.ITemplateWrapper;
import cc.suitalk.arbitrarygen.impl.DefaultRawTemplateParser;
import cc.suitalk.arbitrarygen.processor.ScannerAGProcessor;
import cc.suitalk.arbitrarygen.template.RawTemplate;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by albieliang on 16/10/28.
 */
public class DefaultAGEngine implements ArbitraryGenEngine {

    private static final String TAG = "AG.DefaultAGEngine";

    private SourceFileParserManager mParserMgr;

    public DefaultAGEngine() {
        mParserMgr = new SourceFileParserManager();
    }

    @Override
    public String getName() {
        return "general";
    }

    @Override
    public void initialize(ArbitraryGenCore core, JSONObject args) {
        JSONArray parserArray = args.getJSONArray(ArgsConstants.EXTERNAL_ARGS_KEY_PARSER);
        if (parserArray != null && !parserArray.isEmpty()) {
            List<String> needToLoadClass = new LinkedList<>();
            JarClassLoaderWrapper loader = core.getJarClassLoader();
            for (int i = 0; i < parserArray.size(); i++) {
                JSONObject parser = parserArray.getJSONObject(i);
                if (parser == null) {
                    continue;
                }
                String jar = parser.getString(ArgsConstants.EXTERNAL_ARGS_KEY_JAR);
                String clazz = parser.getString(ArgsConstants.EXTERNAL_ARGS_KEY_CLASS);
                if (Util.isNullOrNil(jar) || Util.isNullOrNil(clazz)) {
                    continue;
                }
                File file = new File(jar);
                if (loader.contains(file) || loader.addJar(file)) {
                    needToLoadClass.add(clazz);
                }
            }

            if (needToLoadClass.size() > 0) {
                for (int i = 0; i < needToLoadClass.size(); i++) {
                    try {
                        Class<?> clazz = loader.loadClass(needToLoadClass.get(i));
                        Object o = clazz.newInstance();
                        if (o instanceof SourceFileParser) {
                            mParserMgr.addParser((SourceFileParser) o);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        JSONArray suffixList = args.getJSONArray(ArgsConstants.EXTERNAL_ARGS_KEY_FORMAT);
        List<String> list = new LinkedList<>();
        if (suffixList != null) {
            for (int i = 0; i < suffixList.size(); i++) {
                String suffix = suffixList.getString(i);
                if (Util.isNullOrNil(suffix)) {
                    continue;
                }
                list.add(suffix);
            }
        }
        // Add more hardcode Parser here.
        DefaultParser parser = new DefaultParser(core, args);
        parser.addSuffixList(list);
        mParserMgr.addParser(parser);
    }

    @Override
    public String[] getDependencies() {
        return new String[] { "scanner" };
    }

    @Override
    public JSONObject exec(ArbitraryGenCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        JSONObject argsJSONObject = new JSONObject();
        argsJSONObject.put(ScannerAGProcessor.KEY_SCAN_MODE, ScannerAGProcessor.SCAN_MODE_CLASSIFY);
        argsJSONObject.put(ScannerAGProcessor.KEY_SRC_DIR, args.getString(ArgsConstants.EXTERNAL_ARGS_KEY_SRC));
        argsJSONObject.put(ScannerAGProcessor.KEY_SUFFIX_LIST, args.getJSONArray(ArgsConstants.EXTERNAL_ARGS_KEY_FORMAT));

        JSONObject jsonObject = core.execProcess(processors, "scanner", argsJSONObject);
        if (jsonObject == null) {
            return null;
        }
        Set<String> keySet = jsonObject.keySet();
        if (keySet == null || keySet.isEmpty()) {
            return null;
        }
        for (String key : keySet) {
            JSONArray array = jsonObject.getJSONArray(key);
            if (array == null || array.isEmpty()) {
                continue;
            }
            SourceFileParser<JSONObject> parser = mParserMgr.getParser(JSONObject.class, key);
            if (parser == null) {
                continue;
            }
            for (int i = 0; i < array.size(); i++) {
                String path = array.getString(i);
                if (Util.isNullOrNil(path)) {
                    continue;
                }
                File file = new File(path);
                JSONObject sourceJSON = parser.parse(file);
                if (sourceJSON == null) {
                    continue;
                }
                // for extend
            }
        }
        return null;
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }

    /**
     *
     */
    private static class SourceFileParserManager {

        private List<SourceFileParser> mSrcFileParserList;

        public SourceFileParserManager() {
            mSrcFileParserList = new LinkedList<>();
        }

        public boolean addParser(SourceFileParser parser) {
            if (parser == null) {
                return false;
            }
            return mSrcFileParserList.add(parser);
        }

        public boolean remove(SourceFileParser parser) {
            if (parser == null) {
                return false;
            }
            return mSrcFileParserList.remove(parser);
        }

        public <T> SourceFileParser<T> getParser(Class<T> clazz, String suffix) {
            for (SourceFileParser parser : mSrcFileParserList) {
                if (parser.match(suffix)) {
                    try {
                        return (SourceFileParser<T>) parser;
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
            return null;
        }
    }

    /**
     *
     */
    public static class DefaultParser implements SourceFileParser<JSONObject> {

        private JSONObject mArgs;
        private DefaultRawTemplateParser mDefaultRawTemplateParser;

        private TemplateParserMgr mParserMgr;
        private TemplateConvertorMgr mConverterMgr;
        protected TemplateWrapperMgr mWrapperMgr;

        public DefaultParser(ArbitraryGenCore core, JSONObject args) {
            mArgs = args;
            mParserMgr = new TemplateParserMgr();
            mConverterMgr = new TemplateConvertorMgr();
            mWrapperMgr = new TemplateWrapperMgr();

            // load extension jar
            JSONArray parserArray = args.getJSONArray(ArgsConstants.EXTERNAL_ARGS_KEY_EXTENSION);
            if (parserArray != null && !parserArray.isEmpty()) {
                List<String> needToLoadClass = new LinkedList<>();
                JarClassLoaderWrapper loader = core.getJarClassLoader();
                for (int i = 0; i < parserArray.size(); i++) {
                    JSONObject parser = parserArray.getJSONObject(i);
                    if (parser == null) {
                        continue;
                    }
                    String jar = parser.getString(ArgsConstants.EXTERNAL_ARGS_KEY_JAR);
                    String clazz = parser.getString(ArgsConstants.EXTERNAL_ARGS_KEY_CLASS);
                    if (Util.isNullOrNil(jar) || Util.isNullOrNil(clazz)) {
                        continue;
                    }
                    File file = new File(jar);
                    if (loader.contains(file) || loader.addJar(file)) {
                        needToLoadClass.add(clazz);
                    }
                }

                if (needToLoadClass.size() > 0) {
                    for (int i = 0; i < needToLoadClass.size(); i++) {
                        try {
                            Class<?> clazz = loader.loadClass(needToLoadClass.get(i));
                            Object o = clazz.newInstance();
                            if (o instanceof ICustomizeParser) {
                                mParserMgr.addParser((ICustomizeParser) o);
                            } else if (o instanceof ICustomizeConvertor) {
                                mConverterMgr.addConvertor((ICustomizeConvertor) o);
                            } else if (o instanceof ITemplateWrapper) {
                                mWrapperMgr.addWrapper((ITemplateWrapper) o);
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
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
        public JSONObject parse(File file) {
            String suffix = "";
            ICustomizeParser parser = mParserMgr.getFirstMatchParser(suffix);
            if (parser == null || !parser.canParse(suffix)) {
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
                    ICustomizeConvertor converter = mConverterMgr.getFirstMatchConvertor(t);
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
         *
         * @param suffix
         */
        public void addSuffix(String suffix) {
            if (suffix == null) {
                return;
            }
            mDefaultRawTemplateParser.addSuffix(suffix);
        }

        /**
         *
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
}
