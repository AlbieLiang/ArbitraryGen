package cc.suitalk.arbitrarygen.template.psychic;

import java.util.HashMap;
import java.util.Map;

import cc.suitalk.arbitrarygen.template.psychic.base.PsychicExecutor;

/**
 * Created by AlbieLiang on 16/11/14.
 */
public class PsychicExecutorManager {

    private Map<String, PsychicExecutor> mPsychicExecutorMap;

    public PsychicExecutorManager() {
        mPsychicExecutorMap = new HashMap<>();
    }

    public boolean addPsychicExecutor(PsychicExecutor executor) {
        if (executor == null || mPsychicExecutorMap.containsKey(executor.getName())) {
            return false;
        }
        mPsychicExecutorMap.put(executor.getName(), executor);
        return true;
    }

    public PsychicExecutor removePsychicExecutor(String name) {
        if (name == null) {
            return null;
        }
        return mPsychicExecutorMap.remove(name);
    }

    public PsychicExecutor getPsychicExecutor(String name) {
        return mPsychicExecutorMap.get(name);
    }
}
