package cc.suitalk.tools.arbitrarygen.demo;

import net.sf.json.JSONObject;

import java.util.Map;

import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.utils.Log;

/**
 * Created by AlbieLiang on 16/11/28.
 */
public class TestProcessor implements ArbitraryGenProcessor {

    private static final String TAG = "AG.TestProcessor";

    @Override
    public String getName() {
        return "testProcessor";
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute processor error, code is '%d', message is '%s'", errorCode, message);
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        Log.i(TAG, "execute processor(%s), args(%s)", getName(), args);
        return null;
    }

    @Override
    public void initialize(AGCore core, JSONObject args) {
        Log.i(TAG, "initialize processor(%s), args(%s)", getName(), args);
    }
}
