/*
 *  Copyright (C) 2016-present Albie Liang. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package cc.suitalk.arbitrarygen.demo.psychic;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cc.suitalk.arbitrarygen.demo.jsapi.base.BaseJsApiFunc;
/*@@@#SCRIPT-BEGIN#
// TaskInfo
//
// name : <%=_name%>
// processor : <%=_processor%>
// template : <%=template%>
// dependsOn : <%=JSON.stringify(dependsOn)%>
// JsApiList : <%=JSON.stringify(context.JsApiList)%>
#SCRIPT-END#@@@*///@@@#AUTO-GEN-BEGIN#
// TaskInfo
//
// name : AGPsychicTask_TemplateProcessor
// processor : hybrid-template-processor
// template : /Volumes/Development/git-repository/tools-repository/ArbitraryGen/arbitrarygen-demo/src/main/java/cc/suitalk/arbitrarygen/demo/psychic/JsApiFrameworkCore.java
// dependsOn : {"_name":"JsApiList","_type":"input","_processor":"parse-java","ruleFile":"/Volumes/Development/git-repository/tools-repository/ArbitraryGen/arbitrarygen-demo/ag-datasrc/jsapi-demo-project.rule","rule":"/Volumes/Development/git-repository/tools-repository/ArbitraryGen/arbitrarygen-demo/src/main/java/cc/suitalk/arbitrarygen/demo/jsapi/recursion/*"}
// JsApiList : [{"_package":"cc.suitalk.arbitrarygen.demo.jsapi","_import":["org.json.JSONObject","cc.suitalk.arbitrarygen.demo.jsapi.annotation.JsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.base.BaseJsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.base.JsApiContext"],"_class":[{"_parent":{"isFinal":false,"name":"BaseJsApiFunc"},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"class","_name":"JsApiFunc_A","_annotation":{"JsApiFunc":{"_name":"JsApiFunc","name":"A","id":0}},"method":[{"_annotation":{"Override":{"_name":"Override"}},"codeBlock":{"statement":[{"_type":"return","_expression":" false"}]},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"boolean","_name":"invoke","_args":[{"_type":{"isFinal":false,"name":"JsApiContext"},"_name":"context"},{"_type":{"isFinal":false,"name":"JSONObject"},"_name":"args"},{"_type":{"isFinal":false,"name":"InvokedCallback"},"_name":"callback"}]}]}]},{"_package":"cc.suitalk.arbitrarygen.demo.jsapi","_import":["org.json.JSONObject","cc.suitalk.arbitrarygen.demo.jsapi.annotation.JsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.base.BaseJsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.base.JsApiContext"],"_class":[{"_parent":{"isFinal":false,"name":"BaseJsApiFunc"},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"class","_name":"JsApiFunc_B","_annotation":{"JsApiFunc":{"_name":"JsApiFunc","name":"B","id":1}},"method":[{"_annotation":{"Override":{"_name":"Override"}},"codeBlock":{"statement":[{"_type":"return","_expression":" false"}]},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"boolean","_name":"invoke","_args":[{"_type":{"isFinal":false,"name":"JsApiContext"},"_name":"context"},{"_type":{"isFinal":false,"name":"JSONObject"},"_name":"args"},{"_type":{"isFinal":false,"name":"InvokedCallback"},"_name":"callback"}]}]}]},{"_package":"cc.suitalk.arbitrarygen.demo.jsapi","_import":["org.json.JSONObject","cc.suitalk.arbitrarygen.demo.jsapi.annotation.JsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.base.BaseJsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.base.JsApiContext"],"_class":[{"_parent":{"isFinal":false,"name":"BaseJsApiFunc"},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"class","_name":"JsApiFunc_C","_annotation":{"JsApiFunc":{"_name":"JsApiFunc","name":"C","id":2}},"method":[{"_annotation":{"Override":{"_name":"Override"}},"codeBlock":{"statement":[{"_type":"return","_expression":" false"}]},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"boolean","_name":"invoke","_args":[{"_type":{"isFinal":false,"name":"JsApiContext"},"_name":"context"},{"_type":{"isFinal":false,"name":"JSONObject"},"_name":"args"},{"_type":{"isFinal":false,"name":"InvokedCallback"},"_name":"callback"}]}]}]},{"_package":"cc.suitalk.arbitrarygen.demo.jsapi","_import":["org.json.JSONObject","cc.suitalk.arbitrarygen.demo.jsapi.annotation.JsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.annotation.Sync","cc.suitalk.arbitrarygen.demo.jsapi.base.BaseJsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.base.JsApiContext"],"_class":[{"_parent":{"isFinal":false,"name":"BaseJsApiFunc"},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"class","_name":"JsApiFunc_D","_annotation":{"Sync":{"_name":"Sync"},"JsApiFunc":{"_name":"JsApiFunc","name":"D","id":3}},"method":[{"_annotation":{"Override":{"_name":"Override"}},"codeBlock":{"statement":[{"_type":"return","_expression":" false"}]},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"boolean","_name":"invoke","_args":[{"_type":{"isFinal":false,"name":"JsApiContext"},"_name":"context"},{"_type":{"isFinal":false,"name":"JSONObject"},"_name":"args"},{"_type":{"isFinal":false,"name":"InvokedCallback"},"_name":"callback"}]}]}]},{"_package":"cc.suitalk.arbitrarygen.demo.jsapi.recursion","_import":["org.json.JSONObject","cc.suitalk.arbitrarygen.demo.jsapi.annotation.JsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.annotation.Sync","cc.suitalk.arbitrarygen.demo.jsapi.base.BaseJsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.base.JsApiContext"],"_class":[{"_parent":{"isFinal":false,"name":"BaseJsApiFunc"},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"class","_name":"JsApiFunc_E","_annotation":{"Sync":{"_name":"Sync"},"JsApiFunc":{"_name":"JsApiFunc","name":"E","id":4}},"method":[{"_annotation":{"Override":{"_name":"Override"}},"codeBlock":{"statement":[{"_type":"return","_expression":" false"}]},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"boolean","_name":"invoke","_args":[{"_type":{"isFinal":false,"name":"JsApiContext"},"_name":"context"},{"_type":{"isFinal":false,"name":"JSONObject"},"_name":"args"},{"_type":{"isFinal":false,"name":"InvokedCallback"},"_name":"callback"}]}]}]}]

//@@@#AUTO-GEN-END#

/**
 * Created by AlbieLiang on 16/11/17.
 */
public class JsApiFrameworkCore {

    private Map<String, BaseJsApiFunc> mASyncJsApiFunc;
    private Map<String, BaseJsApiFunc> mSyncJsApiFunc;


    public JsApiFrameworkCore() {
        mASyncJsApiFunc = new HashMap<>();
        mSyncJsApiFunc = new HashMap<>();
    }

    public void initialize() {
        BaseJsApiFunc jsApiFunc = null;
        /*@@@#SCRIPT-BEGIN#
        <% var jsApiFuncArray = context.JsApiList;
        if (jsApiFuncArray && jsApiFuncArray.length > 0) {
            for (var i = 0; i < jsApiFuncArray.length; i++) {
                var fileObj = jsApiFuncArray[i];
                var _class = null;
                if (!fileObj || !fileObj._class || fileObj._class.length == 0) {
                    continue;
                }
                if (!(_class = fileObj._class[0]) || !_class._annotation || !_class._annotation.JsApiFunc) {
                    continue;
                }
                var jfAnn = _class._annotation.JsApiFunc;var syncAnn = _class._annotation.Sync;
                if (syncAnn) { %>
        // AG. gen Sync JsApiFunc : <%=_class._name%>
        jsApiFunc = new <%=fileObj._package%>.<%=_class._name%>();
        jsApiFunc.setId(<%=jfAnn.id%>);
        jsApiFunc.setName("<%=jfAnn.name%>");
        mSyncJsApiFunc.put(jsApiFunc.getName(), jsApiFunc);
                <%} else {%>
        // AG. gen ASync JsApiFunc : <%=_class._name%>
        jsApiFunc = new <%=fileObj._package%>.<%=_class._name%>();
        jsApiFunc.setId(<%=jfAnn.id%>);
        jsApiFunc.setName("<%=jfAnn.name%>");
        mASyncJsApiFunc.put(jsApiFunc.getName(), jsApiFunc);
                <%}
            }
        } %>
        #SCRIPT-END#@@@*///@@@#AUTO-GEN-BEGIN#
        
        // AG. gen ASync JsApiFunc : JsApiFunc_A
        jsApiFunc = new cc.suitalk.arbitrarygen.demo.jsapi.JsApiFunc_A();
        jsApiFunc.setId(0);
        jsApiFunc.setName("A");
        mASyncJsApiFunc.put(jsApiFunc.getName(), jsApiFunc);
                
        // AG. gen ASync JsApiFunc : JsApiFunc_B
        jsApiFunc = new cc.suitalk.arbitrarygen.demo.jsapi.JsApiFunc_B();
        jsApiFunc.setId(1);
        jsApiFunc.setName("B");
        mASyncJsApiFunc.put(jsApiFunc.getName(), jsApiFunc);
                
        // AG. gen ASync JsApiFunc : JsApiFunc_C
        jsApiFunc = new cc.suitalk.arbitrarygen.demo.jsapi.JsApiFunc_C();
        jsApiFunc.setId(2);
        jsApiFunc.setName("C");
        mASyncJsApiFunc.put(jsApiFunc.getName(), jsApiFunc);
                
        // AG. gen Sync JsApiFunc : JsApiFunc_D
        jsApiFunc = new cc.suitalk.arbitrarygen.demo.jsapi.JsApiFunc_D();
        jsApiFunc.setId(3);
        jsApiFunc.setName("D");
        mSyncJsApiFunc.put(jsApiFunc.getName(), jsApiFunc);
                
        // AG. gen Sync JsApiFunc : JsApiFunc_E
        jsApiFunc = new cc.suitalk.arbitrarygen.demo.jsapi.recursion.JsApiFunc_E();
        jsApiFunc.setId(4);
        jsApiFunc.setName("E");
        mSyncJsApiFunc.put(jsApiFunc.getName(), jsApiFunc);
                
        
        //@@@#AUTO-GEN-END#
        
    }

    public void start() {
        //
    }

    public boolean invokeJsApi(JSONObject args, int callbackId) {
        //
        return false;
    }
}
