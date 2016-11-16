package cc.suitalk.arbitrarygen.demo.psychic;

/**
 * Created by AlbieLiang on 16/11/16.
 */
public class AGPsychicTestCase {

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
        // name : AGPsychicTask_TemplateProcessor
        // processor : hybrid-template-processor
        // template : ./src/main/java/cc/suitalk/arbitrarygen/demo/psychic/AGPsychicTestCase.java
        // dependsOn : {"_name":"JsApiList","_type":"input","_processor":"parse-java","rule":"./autogen/parse-project.rule"}
        // JsApiList : {"JsApiList":[{"_package":"cc.suitalk.arbitrarygen.demo","_import":["cc.suitalk.arbitrarygen.extension.annotation.ArbitraryGenTask","cc.suitalk.arbitrarygen.extension.annotation.Keep","cc.suitalk.arbitrarygen.extension.annotation.RunInMainThread","cc.suitalk.arbitrarygen.extension.annotation.SourceLocation","cc.suitalk.arbitrarygen.extension.annotation.TargetLocation","cc.suitalk.arbitrarygen.extension.model.Command","android.app.Activity","android.os.Bundle","android.os.Handler","android.util.Log","android.view.Menu"],"_class":[{"_parent":{"name":"Activity"},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"class","_name":"MainActivity","_annotation":[{"_name":"ArbitraryGenTask"}],"field":[{"_modifier":"private","_static":true,"_final":true,"_abstract":false,"_synchronized":false,"_type":"String","_name":"TAG","_default":" \"AG.MainActivity\""}],"method":[{"_annotation":[{"_name":"Override"}],"codeBlock":{"statement":[{},{}]},"_modifier":"protected","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"void","_name":"onCreate","_args":[{"_type":{"name":"Bundle"},"_name":"savedInstanceState"}]},{"_annotation":[{"_name":"Override"}],"codeBlock":{"statement":[{},{},{},{}]},"_modifier":"public","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"boolean","_name":"onCreateOptionsMenu","_args":[{"_type":{"name":"Menu"},"_name":"menu"}]},{"_annotation":[{"_name":"SourceLocation","_arg":{"command":{"blankStr":"","type":"REFERENCE","value":null,"valueStr":" Command.Type.COMMAND_EXTRACT_VIEW_ID"}}}],"codeBlock":{"statement":[{}]},"_modifier":"protected","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"int","_name":"getResId"},{"_annotation":[{"_name":"TargetLocation","_arg":{"command":{"blankStr":"","type":"REFERENCE","value":null,"valueStr":" Command.Type.COMMAND_EXTRACT_VIEW_ID"}}}],"_modifier":"protected","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"void","_name":"autoGenGetViewByIdTargetMethod"},{"_annotation":[{"_name":"Keep"}],"_modifier":"protected","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"void","_name":"testKeep"},{"_annotation":[{"_name":"RunInMainThread"}],"codeBlock":{"statement":[{}]},"_modifier":"protected","_static":false,"_final":false,"_abstract":false,"_synchronized":false,"_type":"void","_name":"testRunInMainThread"}]}]}]}
        
//@@@#AUTO-GEN-END#
}
