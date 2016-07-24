package osc.innovator.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 *
 * @Author AlbieLiang
 *
 */
class ArbitraryGenPlugin implements Plugin<Project> {

    Project project;

    ArbitraryGenPluginExtension extension;

    LoggerArgs loggerExtension;
    GeneralArgs argsExtension;
    ScriptEngineArgs scriptEngineExtension;

    ArbitraryGenTask arbitraryGenTask;

    @Override
    void apply(Project project) {
        this.project = project
        this.extension = project.extensions.create("arbitraryGen", ArbitraryGenPluginExtension)
        this.loggerExtension = this.extension.extensions.create("logger", LoggerArgs)
        this.argsExtension = this.extension.extensions.create("general", GeneralArgs)
        this.scriptEngineExtension = this.extension.extensions.create("scriptEngine", ScriptEngineArgs)

        this.arbitraryGenTask = this.project.tasks.create("arbitraryGen", ArbitraryGenTask)

        this.project.tasks.whenTaskAdded { task ->
            this.project.getLogger().debug("when task(${task.name}) added in project(${project.name}).")
            if (task.name.startsWith('generate') && task.name.endsWith('Sources')) {
                println("add task(${task.name}) project : ${this.project}, name : $name.")
                task.dependsOn arbitraryGenTask
            }
        }

        project.afterEvaluate {
            println "arbitrarygen(${this.project.name}) libsDir : '${this.extension.libsDir}'"
            println "arbitrarygen(${this.project.name}) inputDir : '${this.extension.inputDir}'"
            println "arbitrarygen(${this.project.name}) outputDir : '${this.extension.outputDir}'"

            arbitraryGenTask.inputDir = this.project.file(this.extension.inputDir)
            arbitraryGenTask.outputDir = this.project.file(this.extension.outputDir)
            arbitraryGenTask.libsDir = this.project.file(this.extension.libsDir)

            arbitraryGenTask.loggerArgs = this.loggerExtension
            arbitraryGenTask.generalArgs = this.argsExtension
            arbitraryGenTask.scriptEngineArgs = this.scriptEngineExtension

            if (arbitraryGenTask.inputDir == null || !arbitraryGenTask.inputDir.exists()) {
                println("project: ${this.project} do not exists arbitrarygen dir.")
                return
            }
        }
    }
}

