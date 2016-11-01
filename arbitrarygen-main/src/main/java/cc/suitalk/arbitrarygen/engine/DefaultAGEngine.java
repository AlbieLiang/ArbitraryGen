package cc.suitalk.arbitrarygen.engine;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.suitalk.arbitrarygen.core.ArbitraryGenCore;
import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.core.JarClassLoaderWrapper;
import cc.suitalk.arbitrarygen.core.base.ArbitraryGenEngine;
import cc.suitalk.arbitrarygen.core.base.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.core.base.SourceFileParser;
import cc.suitalk.arbitrarygen.parser.DefaultParser;
import cc.suitalk.arbitrarygen.parser.ScriptTemplateParser;
import cc.suitalk.arbitrarygen.processor.ScannerAGProcessor;
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
        mParserMgr.addParser(new ScriptTemplateParser());
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
}
