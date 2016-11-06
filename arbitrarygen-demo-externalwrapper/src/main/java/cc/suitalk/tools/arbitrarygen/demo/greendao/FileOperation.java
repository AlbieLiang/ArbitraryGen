package cc.suitalk.tools.arbitrarygen.demo.greendao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * @author AlbieLiang
 */
public class FileOperation {

    private static final String TAG = "AG.FileOperation";

    public static String read(String file) {
        File f = new File(file);
        if (!f.isFile() || !f.exists()) {
            return "";
        }
        FileInputStream fis = null;
        StringBuffer sb = new StringBuffer();
        byte[] buf = new byte[1024];
        int size = 0;
        try {
            fis = new FileInputStream(f);
            while ((size = fis.read(buf)) != -1) {
                sb.append(new String(buf, 0, size));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

	public static void write(String fileName, String code) {
		FileWriter fw = null;
		try {
			File f = new File(fileName);
			Util.mkdirsIfNeed(f.getParentFile());
			fw = new FileWriter(f);
			fw.write(code);
			fw.flush();
			Log.i(TAG, "Gen file : " + fileName + " Successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
