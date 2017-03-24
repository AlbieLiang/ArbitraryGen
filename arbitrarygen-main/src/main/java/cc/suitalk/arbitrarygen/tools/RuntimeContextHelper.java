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

package cc.suitalk.arbitrarygen.tools;

import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by albieliang on 2017/3/9.
 */

public class RuntimeContextHelper {

    private static final String TAG = "AG.RuntimeContextHelper";

    private static RuntimeContextHelper sHelper;

    private Map<String, Object> mEnvArgs;
    private Pattern mPattern;

    public static RuntimeContextHelper getImpl() {
        if (sHelper == null) {
            synchronized (RuntimeContextHelper.class) {
                if (sHelper == null) {
                    sHelper = new RuntimeContextHelper();
                }
            }
        }
        return sHelper;
    }

    private RuntimeContextHelper() {
        mEnvArgs = new HashedMap();
    }

    public static void initialize(JSONObject envArgs) {
        Log.i(TAG, "initialize(%s)", envArgs);
        if (envArgs == null) {
            return;
        }
        RuntimeContextHelper helper = getImpl();
        helper.putAll("", envArgs);
        helper.mPattern = Pattern.compile("\\$\\{(" + StringUtils.join(helper.mEnvArgs.keySet(), "|") + ")\\}");
        Log.d(TAG, "pattern string : %s", helper.mPattern.toString());
        for (Map.Entry<String, Object> entry : helper.mEnvArgs.entrySet()) {
            Log.d(TAG, "env info, key : %s --> value : %s", entry.getKey(), entry.getValue());
        }
    }


    public static String replace(String rawContent) {
        if (Util.isNullOrNil(rawContent)) {
            return rawContent;
        }
        Matcher matcher = getImpl().mPattern.matcher(rawContent);
        StringBuffer sb = new StringBuffer();
        Map<String, Object> map = getImpl().mEnvArgs;
        while(matcher.find()) {
            Object o = map.get(matcher.group(1));
            if (o == null) {
                continue;
            }
            matcher.appendReplacement(sb, o.toString());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static Object get(String key) {
        return getImpl().mEnvArgs.get(key);
    }

    public static void set(String key, Object value) {
        getImpl().mEnvArgs.put(key, value);
    }

    private void putAll(String prefixKey, JSONObject jsonObject) {
        Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof JSONObject) {
                Log.v(TAG, "putAll(key : %s, value : %s)", key, value);
                putAll(genKey(prefixKey, key), (JSONObject) value);
            } else {
                Log.v(TAG, "put(key : %s, value : %s)", key, value);
                mEnvArgs.put(genKey(prefixKey, key), value);
            }
        }
    }

    private String genKey(String prefixKey, String key) {
        if (prefixKey == null || prefixKey.length() == 0) {
            return key;
        }
        return prefixKey + "." + key;
    }
}
