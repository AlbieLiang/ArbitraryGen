package cc.suitalk.arbitrarygen.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by AlbieLiang on 16/11/6.
 */
public class JSONArgsUtils {

    public static JSONArray getJSONArray(JSONObject jsonObject, String key, boolean convertToArray) {
        if (jsonObject == null) {
            return null;
        }
        Object o = jsonObject.opt(key);
        if (o == null) {
            return null;
        }
        if (o instanceof JSONArray) {
            return (JSONArray) o;
        }
        if (convertToArray) {
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(o);
            return jsonArray;
        }
        return null;
    }

    public static JSONObject validKey(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        JSONObject result = new JSONObject();
        for (Object o : jsonObject.keySet()) {
            if (!(o instanceof String)) {
                continue;
            }
            String key = (String) o;
            Object value = jsonObject.opt(key);
            if (value instanceof JSONObject) {
                value = validKey((JSONObject) value);
            } else if (value instanceof JSONArray) {
                value = validKey((JSONArray) value);
            }
            result.put(toValidKey(key), value);
        }
        return result;
    }

    public static JSONArray validKey(JSONArray jsonArray) {
        if (jsonArray == null) {
            return null;
        }
        JSONArray result = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            Object value = jsonArray.opt(i);
            if (value instanceof JSONObject) {
                value = validKey((JSONObject) value);
            }
            result.add(value);
        }
        return result;
    }

    private static String toValidKey(String key) {
        String result = key;
        if (key != null) {
            result = key.replaceAll("@", "_").replaceAll("-", "_");
        }
        return result;
    }
}
