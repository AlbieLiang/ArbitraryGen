# ArbitraryGen

[![license](http://img.shields.io/badge/license-Apache2.0-brightgreen.svg?style=flat)](https://github.com/AlbieLiang/ArbitraryGen/blob/master/LICENSE)
[![Release Version](https://img.shields.io/badge/release-2.1.0-red.svg)](https://github.com/AlbieLiang/ArbitraryGen/releases)
[![wiki](https://img.shields.io/badge/wiki-2.1.0-red.svg)](https://github.com/AlbieLiang/ArbitraryGen/wiki) 
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/AlbieLiang/ArbitraryGen/pulls)


ArbitraryGen（简称AG）是一个用于快速生成代码的工具。AG提供了丰富的扩展接口，可以满足各种场景的代码生成需求。
AG目前对Java代码生成支持比较完善，AG目前可以在Android Studio开发（使用Gradle编译）的项目中使用，同时也支持在Eclipse项目（使用ant编译）中使用。

目前AG已经更新到v2.1.0版本，项目仍在开发中，欢迎一起完善这个项目。

## 示例
```java
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
@ParseJavaRule(name = "JsApiList", rule = "${project.projectDir}/src/main/java/cc/suitalk/arbitrarygen/demo/jsapi/*")
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
```
上面示例中演示的是在源码文件中插入用于生成代码的JavaScript脚本代码。（示例代码都可以在项目中的arbitrarygen-demo中找到）

更多示例，请移步[wiki](https://github.com/AlbieLiang/ArbitraryGen/wiki#%E4%BD%BF%E7%94%A8)

## 配置与使用

##### Android Studio（或Gradle）中引入ArbitraryGen

在项目build.gradle文件中配置，引入ArbitraryGen Gradle插件
```gradle
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.2.2'
        classpath 'cc.suitalk.tools:arbitrarygen-plugin:2.1.0'
    }
}
allprojects {
    repositories {
        jcenter()
    }
}
```

在项目中Gradle编译中引入了ArbitraryGen之后，下面我们就可以直接使用AG来生成代码了

首先，我们需要在module工程的build.gradle文件中配置AG相关参数（可参考`ArbitraryGen/usage-chatter/gradle-script.gradle`文件或demo工程）
```gralde
apply plugin: 'arbitrarygen'
android {
    sourceSets {
        main {
            java {
                srcDirs = ['src/main/java/', 'build/generated/source/ag-gen']
            }
        }
    }
}
arbitraryGen {
    templateDir "${project.rootDir.getAbsolutePath()}/ArbitraryGen/template-libs"
    srcDir "${project.projectDir.absolutePath}/ag-datasrc"
    destDir "$buildDir/generated/source/ag-gen"

    logger {
        debug true
        toFile true
        printTag true
        path "$buildDir/outputs/logs/ag.log"
    }
}
```
上述的配置指定了模板库的路径（templateDir），数据源的路径（srcDir）和生成代码的目标路径（destDir）。

下面我将演示一个比较有趣的代码生成功能（该示例代码在`arbitrarygen-demo`工程中）

首先在数据源目录（srcDir所配置的路径）新建文件`psychic-test-annotation.psychic-rule`，并在文件中配置扫描文件的规则
```
@Author AlbieLiang
@Date current
@Root ${project.rootDir}
@Project ${project.name}
@Src src/main/java
cc/suitalk/arbitrarygen/demo/psychic/*

@Project-begin ${project.name}
# cc/suitalk/arbitrarygen/demo/MainActivity.java
@Project-end
```
上面的*.psychic-rule扫描规则文件中配置了扫描当前project中的src/main/java/cc/suitalk/arbitrarygen/demo/psychic目录下的所有文件

该目录下有三个java文件：`AGPsychicAnnotationTestCase.java`、`AGPsychicTestCase.java`和`JsApiFrameworkCore.java`。
这里最终扫面出来的文件列表中只有`AGPsychicAnnotationTestCase.java`一个文件，因为*.psychic-rule只会扫出被@PsychicTask注解标识的的java文件。

接下来我们看下在`AGPsychicAnnotationTestCase.java`文件里面我们做些什么事情
```java
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
@ParseJavaRule(name = "JsApiList", rule = "${project.projectDir}/src/main/java/cc/suitalk/arbitrarygen/demo/jsapi/*")
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
        #SCRIPT-END#@@@*/
    }
}
```
很明显看到，上述代码通过`@ParseJavaRule`注解配置了这个java文件中的数据源，AG将根据规则`${project.projectDir}/src/main/java/cc/suitalk/arbitrarygen/demo/jsapi/JsApiFunc_*`扫描得到一个java文件列表，该列表名为JsApiList。

有了数据源之后，我们看下生成代码的模板结构，仔细观察，可以看出模板代码被包在注释符`/*@@@#SCRIPT-BEGIN#`和`#SCRIPT-END#@@@*/`之间，而模板中的JavaScript脚本代码被`<% %>`符号所包裹，就是这样便可以完美的让生成代码的模板嵌入到了源代码中了。
上面这段脚本代码被执行完之后可以得到本文起初的那个示例代码。

更多用法，请移步[wiki](https://github.com/AlbieLiang/ArbitraryGen/wiki#%E4%BD%BF%E7%94%A8)

##### Eclipse中引入ArbitraryGen

ArbitraryGen终究是一个可运行的jar文件，在Eclipse环境下，需要通过配置ant脚本实现调用。
首先，需要到[https://bintray.com/albieliang/maven/arbitrarygen](https://bintray.com/albieliang/maven/arbitrarygen)上下载最新版本的arbitrarygen jar包，并将jar包放到Eclipse工程的某个目录（自主选择目录）下；
接下来我们需要给Eclipse工程配置一下ant builder，（配置路径：`Eclipse Properties->Builders`）
在配置ant builder前，我们需要编写ant编译脚本，我们可以到ArbitraryGen项目中拷贝`ArbitraryGen/usage-chatter/ant-script.xml`到Eclipse项目当中，根据具体情况配置ant脚本
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project name="ArbitraryGenTask" default="ag" >
    
    <target name="ag" >

        <java jar="./ag-libs/ArbitraryGen.jar" fork="true">
            <arg value="enable:true" />
            <arg value="argJsonPath:ag-libs/build-args.json" />
            <arg value="envArgJsonPath:ag-libs/build-env-args.json" />
        </java>
    </target>
</project>
```
脚本中的envArgJsonPath和argJsonPath分别用于配置ArbitraryGen的运行的必要环境变量和运行参数

详细配置请参考[arbitrarygen-demo-antbuild](https://github.com/AlbieLiang/ArbitraryGen/tree/master/arbitrarygen-demo-antbuild)

