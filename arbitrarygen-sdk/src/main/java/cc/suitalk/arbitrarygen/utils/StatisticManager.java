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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by AlbieLiang on 2017/2/5.
 */
public class StatisticManager {

    public static final String CHARSET = "UTF-8";

    private static String path;
    private static FileOutputStream fos;

    public static void mark(String session, Object... args) {
        writeLog(session, args);
    }

    public static boolean prepare() {
        if (Util.isNullOrNil(path)) {
            return false;
        }
        File file = new File(path);
        Util.createFileIfNeed(file);
        try {
            fos = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void close() {
        if (fos != null) {
            try {
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fos = null;
        }
    }

    public static final void setPath(String path) {
        StatisticManager.path = path;
    }

    private static void writeLog(String session, Object... args) {
        if (fos == null || Util.isNullOrNil(session) || args == null || args.length == 0) {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("[").append(session).append("]");
        sb.append(args[0]);
        for (int i = 1; i < args.length; i++) {
            sb.append(",");
            sb.append(args[i]);
        }
        sb.append("\n");
        byte[] bytes = null;
        try {
            bytes = sb.toString().getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (bytes != null && bytes.length > 0) {
            try {
                fos.write(bytes);
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
