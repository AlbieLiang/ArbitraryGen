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

package cc.suitalk.arbitrarygen.core;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cc.suitalk.arbitrarygen.extension.AGContext;
import cc.suitalk.arbitrarygen.extension.AGContextExtension;
import cc.suitalk.arbitrarygen.utils.Log;

/**
 * Created by albieliang on 2017/12/17.
 */

public class AGContextExtensionManager {

    private static final String TAG = "AG.AGContextExtensionManager";

    private static volatile AGContextExtensionManager sImpl;

    private Map<Class<?>, AGContextExtension> mExtension = new HashMap<>();

    public static AGContextExtensionManager getImpl() {
        if (sImpl == null) {
            synchronized (AGContextExtensionManager.class) {
                if (sImpl == null) {
                    sImpl = new AGContextExtensionManager();
                }
            }
        }
        return sImpl;
    }

    public boolean addExtension(Class<AGContextExtension> extension) {
        if (extension == null || mExtension.containsKey(extension)) {
            return false;
        }
        AGContextExtension o = null;
        try {
            o = extension.newInstance();
            mExtension.put(extension, o);
        } catch (InstantiationException e) {
            Log.w(TAG, "newInstance error : %s", e);
        } catch (IllegalAccessException e) {
            Log.w(TAG, "newInstance error : %s", e);
        }
        return true;
    }

    public boolean removeExtension(Class<AGContextExtension> extension) {
        if (extension == null) {
            return false;
        }
        return mExtension.remove(extension) != null;
    }

    protected void onPreInitialize(AGContext context, JSONObject initArgs) {
        if (context == null) {
            return;
        }
        for (AGContextExtension extension : mExtension.values()) {
            extension.onPreInitialize(context, initArgs);
        }
    }
}
