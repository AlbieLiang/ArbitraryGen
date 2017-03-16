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

import java.util.HashMap;
import java.util.Map;

import cc.suitalk.arbitrarygen.demo.jsapi.base.BaseJsApiFunc;
import cc.suitalk.arbitrarygen.extension.psychic.DependsOn;
import cc.suitalk.arbitrarygen.extension.psychic.ParseJavaRule;
import cc.suitalk.arbitrarygen.extension.psychic.PsychicTask;

/**
 * Created by AlbieLiang on 2016/12/2.
 */
@ParseJavaRule(name = "JsApiList", rule = "${project.projectDir}/src/cc/suitalk/arbitrarygen/demo/jsapi/*")
@PsychicTask
public class AGPsychicAnnotationTestCase {

    private final Map<String, BaseJsApiFunc> mJsApiFunc = new HashMap<>();

    public void initialize() {
        BaseJsApiFunc jsApiFunc = null;
        /*@@@#SCRIPT-BEGIN#
        // TaskInfo
        //
        // name : <%=_name%>
        // processor : <%=_processor%>
        <% var jsApiFuncArray = JsApiList.JsApiList;
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
                var jfAnn = _class._annotation.JsApiFunc;
                var syncAnn = _class._annotation.Sync;
                %>
        // AG. gen JsApiFunc : <%=_class._name%>
        jsApiFunc = new <%=fileObj._package%>.<%=_class._name%>();
        jsApiFunc.setId(<%=jfAnn.id%>);
        jsApiFunc.setName("<%=jfAnn.name%>");
        mJsApiFunc.put(jsApiFunc.getName(), jsApiFunc);
            <%}
        } %>
        #SCRIPT-END#@@@*///@@@#AUTO-GEN-BEGIN#
        // TaskInfo
        //
        // name : AGPsychicTask_AGPsychicAnnotationTestCase
        // processor : hybrid-template-processor
        
        // AG. gen JsApiFunc : JsApiFunc_A
        jsApiFunc = new cc.suitalk.arbitrarygen.demo.jsapi.JsApiFunc_A();
        jsApiFunc.setId(0);
        jsApiFunc.setName("A");
        mJsApiFunc.put(jsApiFunc.getName(), jsApiFunc);
            
        // AG. gen JsApiFunc : JsApiFunc_B
        jsApiFunc = new cc.suitalk.arbitrarygen.demo.jsapi.JsApiFunc_B();
        jsApiFunc.setId(1);
        jsApiFunc.setName("B");
        mJsApiFunc.put(jsApiFunc.getName(), jsApiFunc);
            
        // AG. gen JsApiFunc : JsApiFunc_C
        jsApiFunc = new cc.suitalk.arbitrarygen.demo.jsapi.JsApiFunc_C();
        jsApiFunc.setId(2);
        jsApiFunc.setName("C");
        mJsApiFunc.put(jsApiFunc.getName(), jsApiFunc);
            
        // AG. gen JsApiFunc : JsApiFunc_D
        jsApiFunc = new cc.suitalk.arbitrarygen.demo.jsapi.JsApiFunc_D();
        jsApiFunc.setId(3);
        jsApiFunc.setName("D");
        mJsApiFunc.put(jsApiFunc.getName(), jsApiFunc);
            
        // AG. gen JsApiFunc : JsApiFunc_E
        jsApiFunc = new cc.suitalk.arbitrarygen.demo.jsapi.recursion.JsApiFunc_E();
        jsApiFunc.setId(4);
        jsApiFunc.setName("E");
        mJsApiFunc.put(jsApiFunc.getName(), jsApiFunc);
            
        
        //@@@#AUTO-GEN-END#
    }
}
