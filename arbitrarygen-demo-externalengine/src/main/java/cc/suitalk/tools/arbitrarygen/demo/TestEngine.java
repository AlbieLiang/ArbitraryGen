package cc.suitalk.tools.arbitrarygen.demo;

import net.sf.json.JSONObject;

import java.util.Map;

import cc.suitalk.arbitrarygen.extension.AGCore;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenEngine;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.utils.Log;

/**
 * Created by AlbieLiang on 16/11/6.
 */
public class TestEngine implements ArbitraryGenEngine {

    private static final String TAG = "AG.TestEngine";

    @Override
    public String getName() {
        return "testEngine";
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }

    @Override
    public JSONObject exec(AGCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        Log.i(TAG, "execute engine(%s), args(%s)", getName(), args);
        return null;
    }

    @Override
    public void initialize(AGCore core, JSONObject args) {
        Log.i(TAG, "initialize engine(%s), args(%s)", getName(), args);
    }
}
