package cc.suitalk.gradle.plugin

import groovy.json.JsonBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskOutputs

//import org.gradle.internal.impldep.com.google.gson.JsonObject

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
    def GeneralArgs generalArgs;

//    @Input
    def LoggerArgs loggerArgs;

//    @Input
    def ScriptEngineArgs scriptEngineArgs;

//    @Input
    def JavaCodeEngineArgs javaCodeEngineArgs;

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
            arg(value: "enable:true")
            //':' is a separator
            arg(value: "dest:${outputDir.absolutePath}")
            // Dest Folder , "dest" is a prefix.
            arg(value: "src:${inputDir.absolutePath}")
            // source template Folder, "src" is a prefix

            arg(value: "format:${generalArgs.format}")
            arg(value: "arbitrary-enable:${generalArgs.enable}")
//            arg(value: "arbitrary-rule:${generalArgs.rule}")

            // parser template source file format
            arg(value: "print-args:${loggerArgs.printArgs}")

            arg(value: "log-debug:${loggerArgs.debug}")
            arg(value: "log-to-file:${loggerArgs.logToFile}")
            arg(value: "log-file:${loggerArgs.path}")
            arg(value: "print-separator:${loggerArgs.printSeparator}")
            // level: v-1, d-2, i-3, w-4, e-5, a-6
            arg(value: "log-level:${loggerArgs.level}")
            arg(value: "log-printtag:${loggerArgs.printTag}")
            arg(value: "log-printlevel:${loggerArgs.printLevel}")

            // add parsers
            for (String parser : generalArgs.parser) {
                arg(value: "parser:${parser}")
                println("add external parser : ${parser}")
            }
            // For script template engine
            arg(value: "coreLibs:${libsDir.absolutePath}/core-libs")
            arg(value: "templateLibs:${libsDir.absolutePath}/template-libs")
            arg(value: "script-engine-format:${scriptEngineArgs.format}")

//            // TODO albieliang, build json
            if (generalArgs.src == null || generalArgs.src.length() == 0) {
                generalArgs.src inputDir.absolutePath
            }
            if (generalArgs.dest == null || generalArgs.dest.length() == 0) {
                generalArgs.dest outputDir.absolutePath
            }
            if (scriptEngineArgs.src == null || scriptEngineArgs.src.length() == 0) {
                scriptEngineArgs.src inputDir.absolutePath
            }
            if (scriptEngineArgs.dest == null || scriptEngineArgs.dest.length() == 0) {
                scriptEngineArgs.dest outputDir.absolutePath
            }
            if (scriptEngineArgs.coreLibs == null || scriptEngineArgs.coreLibs.length() == 0) {
                scriptEngineArgs.coreLibs "${libsDir.absolutePath}/core-libs"
            }
            if (scriptEngineArgs.templateLibs == null || scriptEngineArgs.templateLibs.length() == 0) {
                scriptEngineArgs.templateLibs "${libsDir.absolutePath}/template-libs"
            }
            if (javaCodeEngineArgs.src == null || javaCodeEngineArgs.src.length() == 0) {
                javaCodeEngineArgs.src inputDir.absolutePath
            }
            if (javaCodeEngineArgs.dest == null || javaCodeEngineArgs.dest.length() == 0) {
                javaCodeEngineArgs.dest outputDir.absolutePath
            }

//            JsonObject rootJson = new JsonObject()
//            rootJson.addProperty("src", "${inputDir.absolutePath}")
//            rootJson.addProperty("dest", "${outputDir.absolutePath}")
//            rootJson.addProperty("enable", true)
//            rootJson.addProperty(loggerArgs.getName(), loggerArgs.toJson())
//            rootJson.addProperty(generalArgs.getName(), generalArgs.toJson())
//            rootJson.addProperty(scriptEngineArgs.getName(), scriptEngineArgs.toJson())
//            rootJson.addProperty(javaCodeEngineArgs.getName(), javaCodeEngineArgs.toJson())
//
//            arg(value: "argJson:${rootJson.toString()}")
//
//            JsonBuilder builder = new JsonBuilder()
//            builder {
//                src "${inputDir.absolutePath}"
//                dest "${outputDir.absolutePath}"
//                enable true
//                "${loggerArgs.getName()}" loggerArgs.toJsonBuilder()
//                "${generalArgs.getName()}" generalArgs.toJsonBuilder()
//                "${scriptEngineArgs.getName()}" scriptEngineArgs.toJsonBuilder()
//                "${javaCodeEngineArgs.getName()}" javaCodeEngineArgs.toJsonBuilder()
//            }

            JsonBuilder builder = new JsonBuilder()
            builder {
                src "${inputDir.absolutePath}"
                dest "${outputDir.absolutePath}"
                enable true
                logger {
                    debug loggerArgs.debug
                    printArgs loggerArgs.printArgs
                    logToFile loggerArgs.logToFile
                    printSeparator loggerArgs.printSeparator
                    printTag loggerArgs.printTag
                    printLevel loggerArgs.printLevel
                    path loggerArgs.path
                }
                general {
                    enable generalArgs.enable
                    format generalArgs.format
                    src generalArgs.src
                    dest generalArgs.dest
                    parser generalArgs.parser
                    extension generalArgs.extension
                }
                scriptEngine {
                    enable scriptEngineArgs.enable
                    format scriptEngineArgs.format
                    src scriptEngineArgs.src
                    dest scriptEngineArgs.dest
                    coreLibs scriptEngineArgs.coreLibs
                    templateLibs scriptEngineArgs.templateLibs
                }
                javaCodeEngine {
                    enable javaCodeEngineArgs.enable
                    src javaCodeEngineArgs.src
                    dest javaCodeEngineArgs.dest
                    rule javaCodeEngineArgs.rule
                }
//                logger "${loggerArgs.toJsonBuilder().toString()}"
//                general "${generalArgs.toJsonBuilder().toString()}"
//                scriptEngine "${scriptEngineArgs.toJsonBuilder().toString()}"
//                javaCodeEngine "${javaCodeEngineArgs.toJsonBuilder().toString()}"
            }
            arg(value: "argJson:${builder.toString()}")
            println("${builder.toPrettyString()}")
        }
    }

    def getRootPath() {
        return project.rootDir.getAbsolutePath()
    }
}
