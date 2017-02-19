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

package cc.suitalk.tools.arbitrarygen.demo.greendao;

import net.sf.json.JSONObject;

import java.io.File;

import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.extension.SourceFileParser;
import cc.suitalk.arbitrarygen.utils.Log;

/**
 * Created by AlbieLiang on 16/11/6.
 */
public class GreenDaoParser implements SourceFileParser<JSONObject, JSONObject> {

    private static final String TAG = "AG.GreenDaoParser";

    @Override
    public boolean match(String suffix) {
        return "greendao".equalsIgnoreCase(suffix);
    }

    @Override
    public JSONObject parse(JSONObject args, File file) {
        Log.i(TAG, "parse greendao file(%s)", file);
        String destDir = args.optString(ArgsConstants.EXTERNAL_ARGS_KEY_DEST);
        GreenDaoGenerator.doGen(file.getAbsolutePath(), destDir);
        return null;
    }
}
