package cc.suitalk.arbitrarygen.extension;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by AlbieLiang on 16/10/27.
 */
public interface ArbitraryGenProcessor {

    /**
     * Get Engine name.
     *
     * @return can not be null or nil
     */
    String getName();

    /**
     * Initialize the Engine with arguments.
     *
     * @param core the ArbitraryGen Engine Core
     * @param args input arguments
     */
    void initialize(AGCore core, JSONObject args);

    /**
     * Get dependencies {@link ArbitraryGenProcessor}'s name array.
     *
     * @return the dependencies processors of this processor
     */
    String[] getDependencies();

    /**
     * The real time to execute the Engine logic.
     *
     * @param core the ArbitraryGen Engine Core
     * @param processors dependencies processors
     * @param args input arguments
     * @return if has result then return a {@link JSONObject}, otherwise return null.
     */
    JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args);

    /**
     * When error occur during the process.
     *
     * @param errorCode code for different error case
     * @param message error message
     */
    void onError(int errorCode, String message);

    interface ErrorCode {
        int MISSING_DEPENDENCIES = 1;
    }
}
