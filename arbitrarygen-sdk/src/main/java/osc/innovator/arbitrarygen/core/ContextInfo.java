package osc.innovator.arbitrarygen.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by albieliang on 16/7/22.
 */
public class ContextInfo {

    private Map<String, String> mAttributes;

    public ContextInfo() {
        mAttributes = new HashMap<>();
    }

    public ContextInfo(Map<String, String> map) {
        mAttributes = new HashMap<>();
        mAttributes.putAll(map);
    }

    public Map<String, String> getAttributes() {
        return mAttributes;
    }
}
