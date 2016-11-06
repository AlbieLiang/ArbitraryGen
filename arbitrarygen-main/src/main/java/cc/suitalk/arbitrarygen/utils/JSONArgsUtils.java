package cc.suitalk.arbitrarygen.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by albieliang on 16/11/6.
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
}
