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

package cc.suitalk.gradle.plugin;

class ArbitraryGenPluginExtension {

    String templateDir;
    String srcDir;
    String destDir;

    boolean enable;

    Closure logger
    Closure statistic
    Closure general
    Closure scriptEngine
    Closure javaCodeEngine
    Closure engine
    Closure processor
    // For extension closure
    Closure ext

    ArbitraryGenPluginExtension() {
        enable = true
    }

    @Override
    String toString() {
        StringBuilder builder = new StringBuilder()
        builder.append("templateDir=").append(templateDir)
        builder.append("\nsrcDir=").append(srcDir)
        builder.append("\ndestDir=").append(destDir)
        builder.append("\nenable=").append(enable)
        builder.append("\nlogger=").append(logger)
        builder.append("\nstatistic=").append(statistic)
        builder.append("\ngeneral=").append(general)
        builder.append("\nscriptEngine=").append(scriptEngine)
        builder.append("\njavaCodeEngine=").append(javaCodeEngine)
        builder.append("\nengine=").append(engine)
        builder.append("\nprocessor=").append(processor)
        builder.append("\next=").append(ext)
        return builder.toString()
    }
}
