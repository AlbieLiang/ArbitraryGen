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

package cc.suitalk.arbitrarygen.processor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.suitalk.arbitrarygen.extension.AGContext;
import cc.suitalk.arbitrarygen.gencode.SourceFileInfo;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.JSONArgsUtils;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/10/27.
 */
public class ScannerAGProcessor implements ArbitraryGenProcessor {

    private static final String TAG = "AG.ScannerAGProcessor";

    public static final int SCAN_MODE_NORMAL = 0;
    public static final int SCAN_MODE_CLASSIFY = 1;
    public static final String KEY_SRC_DIR = "src_dir";
    /**
     * Can be {@link #SCAN_MODE_NORMAL} or {@link #SCAN_MODE_CLASSIFY}
     */
    public static final String KEY_SCAN_MODE = "scan_mode";
    public static final String KEY_SUFFIX_LIST = "suffix_list";
    public static final String KEY_RESULT_FILE_PATH = "result_file_path";

    @Override
    public String getName() {
        return "scanner";
    }

    @Override
    public void initialize(AGContext context, JSONObject args) {
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public JSONObject exec(AGContext context, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        String srcDir = args.getString(KEY_SRC_DIR);
        JSONArray suffixJsonArray = JSONArgsUtils.getJSONArray(args, KEY_SUFFIX_LIST, true);
        int scanMode = args.optInt(KEY_SCAN_MODE);

        Log.v(TAG, "execute args(%s)", args);
        List<String> suffixList = new LinkedList<>();
        if (suffixJsonArray != null) {
            for (int i = 0; i < suffixJsonArray.size(); i++) {
                String suffix = suffixJsonArray.optString(i);
                Log.v(TAG, "add support scan file suffix(%s)", suffix);
                if (Util.isNullOrNil(suffix)) {
                    continue;
                }
                suffixList.add(suffix);
            }
        }
        List<SourceFileInfo> list = FileOperation.scan(srcDir, suffixList);
        JSONObject resultJson = new JSONObject();
        resultJson.put(KEY_SCAN_MODE, scanMode);
        if (list != null) {
            switch (scanMode) {
                case SCAN_MODE_CLASSIFY:
                    for (SourceFileInfo fileInfo : list) {
                        JSONArray array = resultJson.optJSONArray(fileInfo.suffix);
                        Log.v(TAG, "scan result file(%s)", fileInfo.file.getAbsolutePath());
                        if (array == null) {
                            array = new JSONArray();
                        }
                        array.add(fileInfo.file.getAbsolutePath());
                        resultJson.put(fileInfo.suffix, array);
                    }
                    break;
                case SCAN_MODE_NORMAL:
                    JSONArray array = new JSONArray();
                    for (SourceFileInfo fileInfo : list) {
                        Log.v(TAG, "scan result file(%s)", fileInfo.file.getAbsolutePath());
                        array.add(fileInfo.file.getAbsolutePath());
                    }
                    resultJson.put(KEY_RESULT_FILE_PATH, array);
                    break;
            }
        }
        Log.v(TAG, "scan result(%s)", resultJson);
        return resultJson;
    }

    @Override
    public void onError(AGContext context, int errorCode, String message) {
        Log.e(TAG, "do scan error, code is '%d', message is '%s'", errorCode, message);
    }
}
