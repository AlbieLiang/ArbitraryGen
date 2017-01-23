package cc.suitalk.arbitrarygen.demo.psychic;

import cc.suitalk.arbitrarygen.extension.psychic.DependsOn;
import cc.suitalk.arbitrarygen.extension.psychic.ParseJavaRule;
import cc.suitalk.arbitrarygen.extension.psychic.PsychicTask;

/**
 * Created by AlbieLiang on 2016/12/2.
 */
@ParseJavaRule(name = "JsApiList", rule = "src/main/java/cc/suitalk/arbitrarygen/demo/jsapi/JsApiFunc_*")
@PsychicTask
public class AGPsychicAnnotationTestCase {

    /*@@@#SCRIPT-BEGIN#
    <%if (JsApiList && JsApiList.length > 0) {%>
       <%for (var i = 0; i < JsApiList.length; i++) {%>
    //<%=JsApiList[i].toString()%><%}%>
    <%}%>
    // TaskInfo
    //
    // name : <%=_name%>
    // processor : <%=_processor%>
    // template : <%=template%>
    // dependsOn : <%=JSON.stringify(dependsOn)%>
    // JsApiList : <%=JSON.stringify(JsApiList)%>
    #SCRIPT-END#@@@*///@@@#AUTO-GEN-BEGIN#
    
    // TaskInfo
    //
    // name : AGPsychicTask_AGPsychicAnnotationTestCase
    // processor : hybrid-template-processor
    // template : /Volumes/Development/git-repository/tools-repository/ArbitraryGen/arbitrarygen-demo/../arbitrarygen-demo/src/main/java/cc/suitalk/arbitrarygen/demo/psychic/AGPsychicAnnotationTestCase.java
    // dependsOn : [{"_name":"JsApiList","_processor":"parse-java","_type":"input","rule":"src/main/java/cc/suitalk/arbitrarygen/demo/jsapi/JsApiFunc_*.java"}]
    // JsApiList : {"JsApiList":[{"_package":"cc.suitalk.arbitrarygen.demo.jsapi","_import":["org.json.JSONObject","cc.suitalk.arbitrarygen.demo.jsapi.annotation.JsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.base.BaseJsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.base.JsApiContext"],"_class":[{"_parent":{"name":"BaseJsApiFunc"},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"class","_name":"JsApiFunc_A","_annotation":{"JsApiFunc":{"name":"A","id":0}},"method":[{"_annotation":[{}],"codeBlock":{"statement":[{"_type":"return","_expression":" false"}]},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"boolean","_name":"invoke","_args":[{"_type":{"name":"JsApiContext"},"_name":"context"},{"_type":{"name":"JSONObject"},"_name":"args"},{"_type":{"name":"InvokedCallback"},"_name":"callback"}]}]}]},{"_package":"cc.suitalk.arbitrarygen.demo.jsapi","_import":["org.json.JSONObject","cc.suitalk.arbitrarygen.demo.jsapi.annotation.JsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.base.BaseJsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.base.JsApiContext"],"_class":[{"_parent":{"name":"BaseJsApiFunc"},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"class","_name":"JsApiFunc_B","_annotation":{"JsApiFunc":{"name":"B","id":1}},"method":[{"_annotation":[{}],"codeBlock":{"statement":[{"_type":"return","_expression":" false"}]},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"boolean","_name":"invoke","_args":[{"_type":{"name":"JsApiContext"},"_name":"context"},{"_type":{"name":"JSONObject"},"_name":"args"},{"_type":{"name":"InvokedCallback"},"_name":"callback"}]}]}]},{"_package":"cc.suitalk.arbitrarygen.demo.jsapi","_import":["org.json.JSONObject","cc.suitalk.arbitrarygen.demo.jsapi.annotation.JsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.base.BaseJsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.base.JsApiContext"],"_class":[{"_parent":{"name":"BaseJsApiFunc"},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"class","_name":"JsApiFunc_C","_annotation":{"JsApiFunc":{"name":"C","id":2}},"method":[{"_annotation":[{}],"codeBlock":{"statement":[{"_type":"return","_expression":" false"}]},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"boolean","_name":"invoke","_args":[{"_type":{"name":"JsApiContext"},"_name":"context"},{"_type":{"name":"JSONObject"},"_name":"args"},{"_type":{"name":"InvokedCallback"},"_name":"callback"}]}]}]},{"_package":"cc.suitalk.arbitrarygen.demo.jsapi","_import":["org.json.JSONObject","cc.suitalk.arbitrarygen.demo.jsapi.annotation.JsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.annotation.Sync","cc.suitalk.arbitrarygen.demo.jsapi.base.BaseJsApiFunc","cc.suitalk.arbitrarygen.demo.jsapi.base.JsApiContext"],"_class":[{"_parent":{"name":"BaseJsApiFunc"},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"class","_name":"JsApiFunc_D","_annotation":{"Sync":{},"JsApiFunc":{"name":"D","id":3}},"method":[{"_annotation":[{}],"codeBlock":{"statement":[{"_type":"return","_expression":" false"}]},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"boolean","_name":"invoke","_args":[{"_type":{"name":"JsApiContext"},"_name":"context"},{"_type":{"name":"JSONObject"},"_name":"args"},{"_type":{"name":"InvokedCallback"},"_name":"callback"}]}]}]}]}
    
    //@@@#AUTO-GEN-END#
}
