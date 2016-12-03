package cc.suitalk.arbitrarygen.demo.psychic;

import cc.suitalk.arbitrarygen.extension.psychic.DependsOn;
import cc.suitalk.arbitrarygen.extension.psychic.ParseJavaRule;
import cc.suitalk.arbitrarygen.extension.psychic.PsychicTask;

/**
 * Created by AlbieLiang on 2016/12/2.
 */
@ParseJavaRule(name = "JsApiList", rule = "src/main/java/cc/suitalk/arbitrarygen/demo/jsapi/*")
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
    // dependsOn : [{"_name":"JsApiList","_processor":"parse-java","_type":"input","rule":" \"src/main/java/cc/suitalk/arbitrarygen/demo/jsapi/*\""}]
    // JsApiList : {"JsApiList":[]}
    
//@@@#AUTO-GEN-END#
}
