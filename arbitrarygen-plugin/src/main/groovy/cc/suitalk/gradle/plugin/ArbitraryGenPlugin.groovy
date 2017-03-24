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

import cc.suitalk.gradle.plugin.util.AGUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

/**
 *
 * @Author AlbieLiang
 *
 */
class ArbitraryGenPlugin implements Plugin<Project> {

    Project project;

    ArbitraryGenPluginExtension extension;

    ArbitraryGenTask arbitraryGenTask;

    @Override
    void apply(Project project) {
        println("project ${project.name} apply ArbitraryGenPlugin")
        this.project = project
        this.extension = project.extensions.create("arbitraryGen", ArbitraryGenPluginExtension)

        this.arbitraryGenTask = this.project.tasks.create("arbitraryGen", ArbitraryGenTask)

        this.project.tasks.whenTaskAdded { task ->
            println("when task(${task.name}) added in project(${project.name}).")
            if (task.name.startsWith('generate') && task.name.endsWith('Sources') || task.name.equals("compileJava")) {
                println("add task(${task.name}) project : ${this.project}, name : $name.")
                task.dependsOn arbitraryGenTask
            }
        }

        Task task = this.project.tasks.findByName("compileJava")
        if (task != null) {
            task.dependsOn arbitraryGenTask
        }
        project.afterEvaluate {
            println "arbitrarygen(${this.project.name}) templateDir : '${this.extension.templateDir}'"
            println "arbitrarygen(${this.project.name}) srcDir : '${this.extension.srcDir}'"
            println "arbitrarygen(${this.project.name}) destDir : '${this.extension.destDir}'"

            arbitraryGenTask.inputDir = this.project.file(this.extension.srcDir)
            arbitraryGenTask.outputDir = this.project.file(this.extension.destDir)
            arbitraryGenTask.libsDir = this.project.file(this.extension.templateDir)

            arbitraryGenTask.extension = this.extension
//            prepare()
            if (arbitraryGenTask.inputDir == null || !arbitraryGenTask.inputDir.exists()) {
                println("project: ${this.project} do not exists arbitrarygen dir.")
                return
            }
        }
    }

    void prepare() {
        String agGenDir = arbitraryGenTask.outputDir.absolutePath;
        Object sourceSets = getSourceSets();
        if (AGUtils.isAndroidProject(project)) {
            sourceSets['main'].getJava().srcDir(agGenDir);
            println("add source directory : [${agGenDir}] for android project.")
        } else {
            sourceSets['main'].getJava().srcDir(agGenDir);
            println("add source directory : [${agGenDir}] for java project.")
        }
    }

    /**
     * Returns the sourceSets container of a Java or an Android project.
     */
    private Object getSourceSets() {
        if (AGUtils.isAndroidProject(project)) {
            return project.android.sourceSets
        } else {
            return project.sourceSets
        }
    }

}

