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

package cc.suitalk.gradle.plugin

import cc.suitalk.arbitrarygen.ArbitraryGenEntrance
import groovy.json.JsonBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction

/**
 *
 * @Author AlbieLiang
 *
 */
class ArbitraryGenTask extends DefaultTask {

    @InputDirectory
    def File inputDir

    @InputDirectory
    def File libsDir

//    @OutputDirectory
    def File outputDir

//    @Input
    def ArbitraryGenPluginExtension extension

    @TaskAction
    void exec() {
        println "execute ArbitraryGen Task(project : ${getProject().getName()})"
        if (!inputDir.exists()) {
            println "skipped task" + this.getName()
            return
        }
        outputDir.deleteDir()
        runCommand()
    }

    def runCommand() {
//        TaskOutputs.upToDateWhen {}
        println("run command(task : ${name}, input : ${inputDir.absolutePath})")
//        ant.java(jar: "${libsDir.absolutePath}/ArbitraryGen.jar", fork: true) {
//            arg(value: "enable:${extension.enable}")
//            //':' is a separator
//            arg(value: "dest:${outputDir.absolutePath}")
//            // Dest Folder , "dest" is a prefix.
//            arg(value: "src:${inputDir.absolutePath}")
//            arg(value: "libsDir:${libsDir.absolutePath}")
//
//            JsonBuilder builder = new JsonBuilder()
//            builder {
//                src "${inputDir.absolutePath}"
//                dest "${outputDir.absolutePath}"
//                libsDir "${libsDir.absolutePath}"
//                enable extension.enable
//                logger (extension.logger == null ? {} : extension.logger)
//                statistic (extension.statistic == null ? {} : extension.statistic)
//                general (extension.general == null ? {} : extension.general)
//                scriptEngine (extension.scriptEngine == null ? {} : extension.scriptEngine)
//                javaCodeEngine (extension.javaCodeEngine == null ? {} : extension.javaCodeEngine)
//                engine (extension.engine == null ? {} : extension.engine)
//                processor (extension.processor == null ? {} : extension.processor)
//            }
//            arg(value: "argJson:${builder.toString()}")
//            println("${builder.toPrettyString()}")
//        }

        JsonBuilder builder = new JsonBuilder()
        builder {
            src "${inputDir.absolutePath}"
            dest "${outputDir.absolutePath}"
            libsDir "${libsDir.absolutePath}"
            enable extension.enable
            logger (extension.logger == null ? {} : extension.logger)
            statistic (extension.statistic == null ? {} : extension.statistic)
            general (extension.general == null ? {} : extension.general)
            scriptEngine (extension.scriptEngine == null ? {} : extension.scriptEngine)
            javaCodeEngine (extension.javaCodeEngine == null ? {} : extension.javaCodeEngine)
            engine (extension.engine == null ? {} : extension.engine)
            processor (extension.processor == null ? {} : extension.processor)
        }
        JsonBuilder envBuilder = new JsonBuilder()
        envBuilder {
            buildDir "${project.buildDir.getAbsolutePath()}"
            project {
                name "${project.name}"
                projectDir "${project.projectDir.getAbsolutePath()}"
                rootDir "${project.rootDir.getAbsolutePath()}"
            }
        }
        String[] args = new String[6]
        args[0] = "enable:${extension.enable}";
        args[1] = "dest:${outputDir.absolutePath}";
        args[2] = "src:${inputDir.absolutePath}"
        args[3] = "libsDir:${libsDir.absolutePath}"
        args[4] ="argJson:${builder.toString()}"
        args[5] ="envArgJson:${envBuilder.toString()}"

        println("${builder.toPrettyString()}")

        ArbitraryGenEntrance.main(args)
    }

    def getRootPath() {
        return project.rootDir.getAbsolutePath()
    }
}
