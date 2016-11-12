package cc.suitalk.arbitrarygen.engine;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.core.JarClassLoaderWrapper;
import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenEngine;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.extension.SourceFileParser;
import cc.suitalk.arbitrarygen.parser.DefaultParser;
import cc.suitalk.arbitrarygen.parser.ScriptTemplateParser;
import cc.suitalk.arbitrarygen.processor.ScannerAGProcessor;
import cc.suitalk.arbitrarygen.utils.ExtJarClassLoaderTools;
import cc.suitalk.arbitrarygen.utils.JSONArgsUtils;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/10/28.
 */
public class DefaultAGEngine implements ArbitraryGenEngine {

    private static final String TAG = "AG.DefaultAGEngine";

    private SourceFileParserManager mParserMgr;
    private volatile boolean mInitialized;

    public DefaultAGEngine() {
        mParserMgr = new SourceFileParserManager();
    }

    @Override
    public String getName() {
        return "general";
    }

    @Override
    public void initialize(AGCore core, JSONObject args) {
        if (args == null) {
            Log.w(TAG, "initialize failed, args is null.");
            return;
        }
        boolean enable = args.optBoolean(ArgsConstants.EXTERNAL_ARGS_KEY_ENABLE, true);
        if (!enable) {
            Log.i(TAG, "initialize failed, the engine is disable.");
            return;
        }
        Log.i(TAG, "initialize, args(%s).", args);
        // load parser jar
        JSONObject parserJson = args.optJSONObject(ArgsConstants.EXTERNAL_ARGS_KEY_PARSER);
        if (parserJson != null) {
            JarClassLoaderWrapper loader = core.getJarClassLoader();
            ExtJarClassLoaderTools.loadJar(loader,
                    JSONArgsUtils.getJSONArray(parserJson, ArgsConstants.EXTERNAL_ARGS_KEY_JAR, true));
            ExtJarClassLoaderTools.loadClass(loader,
                    JSONArgsUtils.getJSONArray(parserJson, ArgsConstants.EXTERNAL_ARGS_KEY_CLASS, true),
                    new ExtJarClassLoaderTools.OnLoadedClass() {
                        @Override
                        public void onLoadedClass(Object o) {
                            if (o instanceof SourceFileParser) {
                                mParserMgr.addParser((SourceFileParser) o);
                                Log.v(TAG, "add parser(%s)", o.getClass().getName());
                            }
                        }
                    });
        }
        JSONArray suffixList = JSONArgsUtils.getJSONArray(args, ArgsConstants.EXTERNAL_ARGS_KEY_FORMAT, true);
        List<String> list = new LinkedList<>();
        if (suffixList != null) {
            for (int i = 0; i < suffixList.size(); i++) {
                String suffix = suffixList.optString(i);
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
        mInitialized = true;
    }

    @Override
    public String[] getDependencies() {
        return new String[] { "scanner" };
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        if (!mInitialized) {
            Log.w(TAG, "exec failed, haven't initialized.");
            return null;
        }
        Log.v(TAG, "execute general engine, args(%s)", args);
        JSONObject argsJSONObject = new JSONObject();
        argsJSONObject.put(ScannerAGProcessor.KEY_SCAN_MODE, ScannerAGProcessor.SCAN_MODE_CLASSIFY);
        argsJSONObject.put(ScannerAGProcessor.KEY_SRC_DIR, args.getString(ArgsConstants.EXTERNAL_ARGS_KEY_SRC));
        argsJSONObject.put(ScannerAGProcessor.KEY_SUFFIX_LIST, JSONArgsUtils.getJSONArray(args, ArgsConstants.EXTERNAL_ARGS_KEY_FORMAT, true));

        JSONObject jsonObject = core.execProcess(processors, "scanner", argsJSONObject);
        if (jsonObject == null) {
            Log.i(TAG, "exec failed, scan out  file list is null.");
            return null;
        }
        Set<String> keySet = jsonObject.keySet();
        if (keySet == null || keySet.isEmpty()) {
            Log.i(TAG, "exec failed, scan out  file list is nil.");
            return null;
        }
        for (String key : keySet) {
            JSONArray array = jsonObject.optJSONArray(key);
            if (array == null || array.isEmpty()) {
                continue;
            }
            SourceFileParser<JSONObject, JSONObject> parser = mParserMgr.getParser(JSONObject.class, JSONObject.class, key);
            if (parser == null) {
                continue;
            }
            for (int i = 0; i < array.size(); i++) {
                String path = array.optString(i);
                if (Util.isNullOrNil(path)) {
                    continue;
                }
                File file = new File(path);
                JSONObject sourceJSON = parser.parse(args, file);
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

        public <T1, T2> SourceFileParser<T1, T2> getParser(Class<T1> t1Class, Class<T2> t2Class, String suffix) {
            for (int i = 0; i < mSrcFileParserList.size(); i++) {
                SourceFileParser parser = mSrcFileParserList.get(i);
                if (parser.match(suffix)) {
                    try {
                        return (SourceFileParser<T1, T2>) parser;
                    } catch (Exception e) {
                        Log.w(TAG, "cast parser error : %s", e);
                        continue;
                    }
                }
            }
            return null;
        }
    }
}
