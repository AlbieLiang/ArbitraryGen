package cc.suitalk.arbitrarygen.engine;

import net.sf.json.JSONObject;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.suitalk.arbitrarygen.analyzer.JavaFileLexer;
import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.core.ConfigInfo;
import cc.suitalk.arbitrarygen.core.JarClassLoaderWrapper;
import cc.suitalk.arbitrarygen.extension.AGAnnotationProcessor;
import cc.suitalk.arbitrarygen.gencode.SourceFileInfo;
import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenEngine;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.extension.ITypeDefineWrapper;
import cc.suitalk.arbitrarygen.impl.AGAnnotationWrapper;
import cc.suitalk.arbitrarygen.rule.Project;
import cc.suitalk.arbitrarygen.rule.Rule;
import cc.suitalk.arbitrarygen.rule.RuleParser;
import cc.suitalk.arbitrarygen.utils.ExtJarClassLoaderTools;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.JSONArgsUtils;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/2.
 */
public class JavaCodeAGEngine implements ArbitraryGenEngine {

    private static final String TAG = "AG.JavaCodeAGEngine";

    private volatile boolean mEnable;

    private List<Rule> mRules;
    private TypeDefineWrapperMgr mTypeDefWrapper;

    @Override
    public String getName() {
        return "javaCodeEngine";
    }

    @Override
    public void initialize(AGCore core, JSONObject args) {
        if (args == null) {
            Log.i(TAG, "initialize failed, args is null.");
            return;
        }
        mRules = new LinkedList<>();
        mTypeDefWrapper = new TypeDefineWrapperMgr();
        // Extract ArbitraryEnable flag
        mEnable = args.optBoolean(ArgsConstants.EXTERNAL_ARGS_KEY_ENABLE, true);
        if (mEnable) {
            String ruleArg = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_RULE);
            if (!Util.isNullOrNil(ruleArg)) {
                // Java file
                RuleParser parser = new RuleParser();
                addRule(parser.parse(ruleArg));
                final AGAnnotationWrapper annWrapper = new AGAnnotationWrapper();
                addTypeDefWrapper(annWrapper);
                // load extension jar
                JSONObject extensionJson = args.optJSONObject(ArgsConstants.EXTERNAL_ARGS_KEY_EXTENSION);
                if (extensionJson != null) {
                    JarClassLoaderWrapper loader = core.getJarClassLoader();
                    ExtJarClassLoaderTools.loadJar(loader,
                            JSONArgsUtils.getJSONArray(extensionJson, ArgsConstants.EXTERNAL_ARGS_KEY_JAR, true));
                    ExtJarClassLoaderTools.loadClass(loader,
                            JSONArgsUtils.getJSONArray(extensionJson, ArgsConstants.EXTERNAL_ARGS_KEY_CLASS, true),
                            new ExtJarClassLoaderTools.OnLoadedClass() {
                                @Override
                                public void onLoadedClass(Object o) {
                                    if (o instanceof ITypeDefineWrapper) {
                                        addTypeDefWrapper((ITypeDefineWrapper) o);
                                    }
                                }
                            });
                    ExtJarClassLoaderTools.loadClass(loader,
                            JSONArgsUtils.getJSONArray(extensionJson, ArgsConstants.EXTERNAL_ARGS_KEY_PROCESSOR_CLASS, true),
                            new ExtJarClassLoaderTools.OnLoadedClass() {
                                @Override
                                public void onLoadedClass(Object o) {
                                    if (o instanceof AGAnnotationProcessor) {
                                        annWrapper.addAnnotationProcessor((AGAnnotationProcessor) o);
                                    }
                                }
                            });
                }
//                ITypeDefineWrapper wrapper = new DefaultTypeDefineWrapper();
                // TODO: 16/11/2 albieliang, Add more type worker here
//                wrapper.addIAGTaskWorker(worker);
//                addTypeDefWrapper(wrapper);
            }
        }
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        if (!mEnable) {
            return null;
        }
        ConfigInfo configInfo = new ConfigInfo();
        // Extract the destination path arg
        String dest = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_DEST);
        if (!Util.isNullOrNil(dest)) {
            configInfo.setDestPath(dest);
        }
        // Extract the source template path arg
        String src = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_SRC);
        if (!Util.isNullOrNil(src)) {
            configInfo.setSrcPath(src);
        }
        List<SourceFileInfo> srcFileInfoList = new LinkedList<>();
        for (Rule r : mRules) {
            Project p = r.getDefaultProject();
            if (p != null) {
                srcFileInfoList.addAll(scan(p));
            }
            List<Project> projects = r.getProjects();
            for (int i = 0; i < projects.size(); i++) {
                srcFileInfoList.addAll(scan(projects.get(i)));
            }
        }
        for (int i = 0; i < srcFileInfoList.size(); i++) {
            SourceFileInfo info = srcFileInfoList.get(i);
            JavaFileLexer lexer = new JavaFileLexer(info.file);
            JavaFileObject javaFileObject = lexer.start();
            configInfo.setFile(info.file);
            mTypeDefWrapper.doWrap(configInfo, javaFileObject);
        }
        return null;
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }

    private List<SourceFileInfo> scan(Project p) {
        List<SourceFileInfo> results = new LinkedList<>();
        for (String path : p.getSrcfiles()) {
            String absolutePath = p.getRoot() + File.separator + p.getName() + File.separator + p.getSrcs().get(0) + File.separator + Util.exchangeToPath(path) + "." + p.getFormats().get(0);
            Log.v(TAG, "path : " + absolutePath);
            List<SourceFileInfo> r = FileOperation.scan(absolutePath, p.getFormats());
            if (r != null && r.size() > 0) {
                results.addAll(r);
            }
        }
        return results;
    }

    public List<Rule> getRules() {
        return mRules;
    }

    public boolean addRules(List<Rule> rules) {
        if (rules == null) {
            return false;
        }
        return this.mRules.addAll(rules);
    }

    public boolean addRule(Rule rule) {
        if (rule == null || mRules.contains(rule)) {
            return false;
        }
        return this.mRules.add(rule);
    }

    public boolean removeRule(Rule rule) {
        return mRules.remove(rule);
    }

    public void addTypeDefWrapper(ITypeDefineWrapper wrapper) {
        this.mTypeDefWrapper.addWrapper(wrapper);
    }

    public void removeTypeDefWrapper(ITypeDefineWrapper wrapper) {
        this.mTypeDefWrapper.removeWrapper(wrapper);
    }
}
