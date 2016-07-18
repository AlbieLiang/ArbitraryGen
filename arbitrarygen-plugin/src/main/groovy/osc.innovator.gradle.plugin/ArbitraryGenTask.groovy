package osc.innovator.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
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

    @OutputDirectory
    def File outputDir

    def GeneralArgs generalArgs;

    def LoggerArgs loggerArgs;

    @TaskAction
    void exec() {
        if (!inputDir.exists()) {
            println "skipped task" + this.getName()
            return
        }
        outputDir.deleteDir()
        runCommand()
    }

    def runCommand() {
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
            arg(value: "arbitrary-rule:${generalArgs.rule}")

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

            // ../tools-repository/AutoGen/
            /**
             arg(value: "parser:../ArbitraryGen/ext.jar,com.external.director.ExternalTemplateDirector")
             arg(value: "parser:../ArbitraryGen/wrapper.jar,com.external.wrapper.EventTemplateWrapper")
             arg(value: "parser:../ArbitraryGen/wrapper.jar,com.external.wrapper.ExternalTemplateWrapper")
             */
            // For script template engine
            arg(value: "core-libs:${libsDir.absolutePath}/core-libs")
            arg(value: "template-libs:${libsDir.absolutePath}/template-libs")
        }
    }

    def getRootPath() {
        return project.rootDir.getAbsolutePath()
    }
}
