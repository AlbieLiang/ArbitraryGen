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

package cc.suitalk.arbitrarygen.utils;

/**
 * Created by AlbieLiang on 16/11/16.
 */
public class TemplateUtils {

    public static final String LINEFEED_CODE = "\\x0a";
    public static final String CARRIAGE_RETURN_CODE = "\\x0d";

    public static String escape(String str) {
//		return str.replaceAll("(\r\n)+", "\\x0a").replaceAll("\"", "\\\\\"").replaceAll("\'", "\\x29");
//        return str
//                .replaceAll("\r", CARRIAGE_RETURN_CODE)
//                .replaceAll("\n", LINEFEED_CODE)
//                .replaceAll("\"", "\\\\\"")
//                .replaceAll("\'", "\\x29");
        return HybridsTemplateUtils.escape(str);
    }

    public static String unescape(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
//		return str.replaceAll("(x0a[ ]*)+", "\r\n").replace("x29", "'");
//        return str.replaceAll("x0d", "\r").replaceAll("x0a", "\n").replace("x29", "'");
        return HybridsTemplateUtils.unescape(str);
    }

    public static String format(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        String indent = "";
        String vary = "    ";
        StringBuilder sb = new StringBuilder();
        String[] sps = str.split("\r\n");

        for (int i = 0, len = sps.length; i < len; i++) {
            String sp = sps[i].trim();
            if (sp.startsWith("}}")) {
                indent = indent.replaceFirst(vary, "");
                indent = indent.replaceFirst(vary, "");
            } else if (sp.startsWith("}")) {
                indent = indent.replaceFirst(vary, "");
            }
            sb.append(indent);
            sb.append(sp);
            if (sp.endsWith("{")) {
                indent += vary;
            }
            sb.append("\r\n");
        }
        return sb.toString();
    }
}
