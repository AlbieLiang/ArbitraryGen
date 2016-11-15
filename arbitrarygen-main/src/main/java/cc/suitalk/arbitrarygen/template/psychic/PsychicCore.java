package cc.suitalk.arbitrarygen.template.psychic;

import net.sf.json.JSONObject;

import cc.suitalk.arbitrarygen.template.psychic.base.PsychicExecutor;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by albieliang on 16/11/14.
 */

public class PsychicCore {

    private static final String TAG = "AG.PsychicCore";

    private PsychicExecutorManager mExecutorManager;

    public PsychicCore() {
        mExecutorManager = new PsychicExecutorManager();
    }

    public void initialize(JSONObject args) {
        //
        prepare(args);
    }

    public void start() {
        //
    }

    private void prepare(JSONObject args) {
        mExecutorManager.addPsychicExecutor(new ScriptExecutor());
    }

    public JSONObject execTask(JSONObject taskInfo) {
        if (taskInfo == null) {
            Log.w(TAG, "exec task failed, task info is null.");
            return null;
        }
        //
        String worker = taskInfo.optString("worker");
        if (Util.isNullOrNil(worker)) {
            Log.i(TAG, "exec task failed, worker is null or nil.");
            return null;
        }
        PsychicExecutor executor = mExecutorManager.getPsychicExecutor(worker);
        if (executor == null) {
            return null;
        }
        return executor.exec(taskInfo);
    }

}
