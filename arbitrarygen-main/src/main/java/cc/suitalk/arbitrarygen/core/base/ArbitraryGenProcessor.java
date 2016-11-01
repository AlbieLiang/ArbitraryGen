package cc.suitalk.arbitrarygen.core.base;

import net.sf.json.JSONObject;

import java.util.Map;

import cc.suitalk.arbitrarygen.core.ArbitraryGenCore;

/**
 * Created by albieliang on 16/10/27.
 */
public interface ArbitraryGenProcessor {

    /**
     * Get Engine name.
     *
     * @return
     */
    String getName();

    /**
     * Initialize the Engine with arguments.
     *
     * @param core
     * @param args
     */
    void initialize(ArbitraryGenCore core, JSONObject args);

    /**
     * Get dependencies {@link ArbitraryGenProcessor}'s name array.
     *
     * @return
     */
    String[] getDependencies();

    /**
     * The real time to execute the Engine logic.
     *
     * @param processors
     * @return if has result then return a {@link JSONObject}, otherwise return null.
     */
    JSONObject exec(ArbitraryGenCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args);

    /**
     * When error occur during the process.
     *
     * @param errorCode
     * @param message
     */
    void onError(int errorCode, String message);

    interface ErrorCode {
        int MISSING_DEPENDENCIES = 1;
    }
}
