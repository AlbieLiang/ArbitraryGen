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

package cc.suitalk.arbitrarygen.parser;

import net.sf.json.JSONObject;

import java.io.File;

import cc.suitalk.arbitrarygen.extension.SourceFileParser;
import cc.suitalk.arbitrarygen.template.TemplateConfig;
import cc.suitalk.arbitrarygen.template.base.TemplateProcessor;

/**
 * Created by AlbieLiang on 16/11/2.
 */
public class ScriptTemplateParser implements SourceFileParser<JSONObject, JSONObject> {

    private TemplateProcessor mTemplateProcessor;
    private TemplateConfig mTemplateConfig;

    @Override
    public boolean match(String suffix) {
        return false;
    }

    @Override
    public JSONObject parse(JSONObject args, File file) {
        return null;
    }
}
