package cc.suitalk.arbitrarygen.demo.jsapi;

import org.json.JSONObject;

import cc.suitalk.arbitrarygen.demo.jsapi.annotation.JsApiFunc;
import cc.suitalk.arbitrarygen.demo.jsapi.base.BaseJsApiFunc;
import cc.suitalk.arbitrarygen.demo.jsapi.base.JsApiContext;

/**
 * Created by AlbieLiang on 16/11/16.
 */
@JsApiFunc(id = 2, name = "C")
public class JsApiFunc_C extends BaseJsApiFunc {

    @Override
    public boolean invoke(JsApiContext context, JSONObject args, InvokedCallback callback) {
        return false;
    }
}