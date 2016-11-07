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
