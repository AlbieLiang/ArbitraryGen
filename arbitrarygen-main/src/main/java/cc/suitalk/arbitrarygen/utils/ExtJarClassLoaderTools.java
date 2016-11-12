package cc.suitalk.arbitrarygen.utils;

import net.sf.json.JSONArray;

import java.io.File;
import java.net.MalformedURLException;

import cc.suitalk.arbitrarygen.core.JarClassLoaderWrapper;

/**
 * Created by AlbieLiang on 16/11/12.
 */
public class ExtJarClassLoaderTools {

    private static final String TAG = "AG.ExtJarClassLoaderTools";

    public static void loadJar(JarClassLoaderWrapper loader, JSONArray jarArray) {
        if (loader == null) {
            Log.w(TAG, "loadJar failed, loader is null.");
            return;
        }
        if (jarArray != null && !jarArray.isEmpty()) {
            for (int i = 0; i < jarArray.size(); i++) {
                String jar = jarArray.optString(i);
                if (Util.isNullOrNil(jar)) {
                    continue;
                }
                File file = new File(jar);
                if (!loader.contains(file) && loader.addJar(file)) {
                    Log.i(TAG, "Loaded Jar(%s) into ClassLoader.", jar);
                }
            }
        }
    }

    public static void loadClass(JarClassLoaderWrapper loader, JSONArray classArray, OnLoadedClass onLoadedClass) {
        if (loader == null || onLoadedClass == null) {
            Log.w(TAG, "loadJar failed, loader or onLoadedClass callback is null.");
            return;
        }
        if (classArray != null && !classArray.isEmpty()) {
            for (int i = 0; i < classArray.size(); i++) {
                String tClass = classArray.optString(i);
                if (Util.isNullOrNil(tClass)) {
                    continue;
                }
                try {
                    Class<?> clazz = loader.loadClass(tClass);
                    Object o = clazz.newInstance();
                    onLoadedClass.onLoadedClass(o);
                } catch (MalformedURLException e) {
                    Log.e(TAG, "load class error : %s", e);
                } catch (ClassNotFoundException e) {
                    Log.e(TAG, "load class error : %s", e);
                } catch (InstantiationException e) {
                    Log.e(TAG, "load class error : %s", e);
                } catch (IllegalAccessException e) {
                    Log.e(TAG, "load class error : %s", e);
                }
            }
        }
    }

    public interface OnLoadedClass {
        void onLoadedClass(Object o);
    }
}
