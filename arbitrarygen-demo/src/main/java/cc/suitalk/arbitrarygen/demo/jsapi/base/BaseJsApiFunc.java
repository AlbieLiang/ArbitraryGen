package cc.suitalk.arbitrarygen.demo.jsapi.base;

import org.json.JSONObject;

/**
 * Created by AlbieLiang on 16/11/17.
 */
public abstract class BaseJsApiFunc {

    private int id;
    private String name;

    public abstract boolean invoke(JsApiContext context, JSONObject args, InvokedCallback callback);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public interface InvokedCallback {
        void doCallback(JSONObject result);
    }
}
