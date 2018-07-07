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

import java.util.HashMap;
import java.util.List;
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
        putAll(helper.mEnvArgs, "", envArgs);
        String keyList = StringUtils.join(helper.mEnvArgs.keySet(), "|");
        Log.d(TAG, "keyList : %s", keyList);
        try {
            helper.mPattern = Pattern.compile("\\$\\{(" + escape(keyList) + ")\\}");
        } catch (Exception e) {
            Log.i(TAG, "pattern : \\$\\{(" + keyList + ")\\}\nexception : " + e);
        }
        Log.d(TAG, "pattern string : %s", helper.mPattern.toString());
        for (Map.Entry<String, Object> entry : helper.mEnvArgs.entrySet()) {
            Log.d(TAG, "env info, key : %s --> value : %s", entry.getKey(), entry.getValue());
        }
    }


    public static String replace(String rawContent) {
        if (Util.isNullOrNil(rawContent)) {
            return rawContent;
        }
        try {
            Matcher matcher = getImpl().mPattern.matcher(rawContent);
            StringBuffer sb = new StringBuffer();
            Map<String, Object> map = getImpl().mEnvArgs;
            while (matcher.find()) {
                Object o = map.get(matcher.group(1));
                if (o == null) {
                    continue;
                }
                matcher.appendReplacement(sb, o.toString());
            }
            matcher.appendTail(sb);
            return sb.toString();
        } catch (Exception e) {
            Log.e(TAG, "error : %s", e);
            return "";
        }
    }

    public static String replace(String rawContent, Map<String, Object> extMap) {
        if (Util.isNullOrNil(rawContent)) {
            return rawContent;
        }
        try {
            Map<String, Object> map = new HashMap<>();
            map.putAll(getImpl().mEnvArgs);
            Pattern pattern = getImpl().mPattern;
            if (extMap != null) {
                put(map, "", extMap);
                pattern = Pattern.compile("\\$\\{(" + escape(StringUtils.join(map.keySet(), "|")) + ")\\}");
            }
            Matcher matcher = pattern.matcher(rawContent);
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                Object o = map.get(matcher.group(1));
                if (o == null) {
                    continue;
                }
                matcher.appendReplacement(sb, o.toString());
            }
            matcher.appendTail(sb);
            return sb.toString();
        } catch (Exception e) {
            Log.e(TAG, "error : %s", e);
            return "";
        }
    }

    public static Object get(String key) {
        return getImpl().mEnvArgs.get(key);
    }

    public static void set(String key, Object value) {
        put(getImpl().mEnvArgs, key, value);
    }
//
//    private void putAll(String prefixKey, JSONObject jsonObject) {
//        Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
//        for (Map.Entry<String, Object> entry : entrySet) {
//            String key = entry.getKey();
//            Object value = entry.getValue();
//            if (value instanceof JSONObject) {
//                Log.v(TAG, "putAll(key : %s, value : %s)", key, value);
//                putAll(genKey(prefixKey, key), (JSONObject) value);
//            } else {
//                Log.v(TAG, "put(key : %s, value : %s)", key, value);
//                mEnvArgs.put(genKey(prefixKey, key), value);
//            }
//        }
//    }

    private static String escape(String rawStr) {
        return rawStr.replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]");
    }

    private static void put(Map<String, Object> toMap, String key, Object value) {
        if (value instanceof Map) {
            Log.v(TAG, "putAll(key : %s, value : %s)", key, value);
            putAll(toMap, key, (Map<String, Object>) value);
        } else if (value instanceof List) {
            putAll(toMap, key, (List<Object>) value);
        } else {
            Log.v(TAG, "put(key : %s, value : %s)", key, value);
            toMap.put(key, value);
        }
    }

    private static void putAll(Map<String, Object> toMap, String prefixKey, Map<String, Object> fromMap) {
        Set<Map.Entry<String, Object>> entrySet = fromMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            String key = entry.getKey();
            Object value = entry.getValue();
//            if (value instanceof Map) {
//                Log.v(TAG, "putAll(key : %s, value : %s)", key, value);
//                putAll(toMap, genKey(prefixKey, key), (Map<String, Object>) value);
//            } else if (value instanceof List) {
//                putAll(toMap, genKey(prefixKey, key), (List<Object>) value);
//            } else {
//                Log.v(TAG, "put(key : %s, value : %s)", key, value);
//                toMap.put(genKey(prefixKey, key), value);
//            }
            put(toMap, genKey(prefixKey, key), value);
        }
    }

    private static void putAll(Map<String, Object> toMap, String prefixKey, List<Object> list) {
        int i = 0;
        for (Object value : list) {
            String key = prefixKey + "[" + (i++) + "]";
//            if (value instanceof Map) {
//                putAll(toMap, key, (Map<String, Object>) value);
//            } else if (value instanceof List) {
//                putAll(toMap, key, (List<Object>) value);
//            } else {
//                toMap.put(key, value);
//            }
            put(toMap, key, value);
        }
    }

    private static String genKey(String prefixKey, String key) {
        if (prefixKey == null || prefixKey.length() == 0) {
            return key;
        }
        return prefixKey + "." + key;
    }

//    private static String join(Map<String, Object> map, String prefix, String separator) {
//        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
//        if (iterator == null || !iterator.hasNext()) {
//            return null;
//        }
//        Map.Entry<String, Object> entry = iterator.next();
//        StringBuilder builder = new StringBuilder();
//        builder.append(prefix + getKey(entry.getKey(), entry.getValue(), separator));
//        while (iterator.hasNext()) {
//            builder.append(separator);
//            entry = iterator.next();
//            String key = getKey(entry.getKey(), entry.getValue(), separator);
//            if (!Util.isNullOrNil(key)) {
//                builder.append(prefix + key);
//            }
//        }
//        Log.i(TAG, "keyList : %s", builder.toString());
//        return builder.toString();
//    }
//
//    private static String join(String key, List<Object> list , String separator) {
//        Iterator<Object> iterator = list.iterator();
//        if (iterator == null || !iterator.hasNext()) {
//            return null;
//        }
//        Object entry = iterator.next();
//        StringBuilder builder = new StringBuilder();
//        builder.append(getKey(key + "[0]", entry, separator));
//        int i = 1;
//        while (iterator.hasNext()) {
//            builder.append(separator);
//            builder.append(getKey(key + "[" + (i++) + "]", iterator.next(), separator));
//        }
//        return builder.toString();
//    }
//
//    private static String getKey(String key, Object value, String separator) {
//        if (value instanceof Map) {
//            return join((Map<String, Object>) value, key + ".", separator);
//        }
//        if (value instanceof List) {
//            return join(key, (List<Object>) value, separator);
//        }
//        return key;
//    }

}
