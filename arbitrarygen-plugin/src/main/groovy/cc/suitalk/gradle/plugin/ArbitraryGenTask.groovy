package cc.suitalk.gradle.plugin

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
        ant.java(jar: "${libsDir.absolutePath}/ArbitraryGen.jar", fork: true) {
            arg(value: "enable:${extension.enable}")
            //':' is a separator
            arg(value: "dest:${outputDir.absolutePath}")
            // Dest Folder , "dest" is a prefix.
            arg(value: "src:${inputDir.absolutePath}")
            arg(value: "libsDir:${libsDir.absolutePath}")

            JsonBuilder builder = new JsonBuilder()
            builder {
                src "${inputDir.absolutePath}"
                dest "${outputDir.absolutePath}"
                libsDir "${libsDir.absolutePath}"
                enable extension.enable
                logger (extension.logger == null ? {} : extension.logger)
                general (extension.general == null ? {} : extension.general)
                scriptEngine (extension.scriptEngine == null ? {} : extension.scriptEngine)
                javaCodeEngine (extension.javaCodeEngine == null ? {} : extension.javaCodeEngine)
                engine (extension.engine == null ? {} : extension.engine)
            }
            arg(value: "argJson:${builder.toString()}")
            println("${builder.toPrettyString()}")
        }
    }

    def getRootPath() {
        return project.rootDir.getAbsolutePath()
    }
}
