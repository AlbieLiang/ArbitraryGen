package cc.suitalk.arbitrarygen.processor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.suitalk.arbitrarygen.core.ArbitraryGenCore;
import cc.suitalk.arbitrarygen.core.SourceFileInfo;
import cc.suitalk.arbitrarygen.core.base.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.Log;

/**
 * Created by albieliang on 16/10/27.
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
    public void initialize(ArbitraryGenCore core, JSONObject args) {
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }

    @Override
    public JSONObject exec(ArbitraryGenCore core, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        String srcDir = args.getString(KEY_SRC_DIR);
        JSONArray jsonArray = args.getJSONArray(KEY_SUFFIX_LIST);
        int scanMode = args.getInt(KEY_SCAN_MODE);

        List<String> suffixList = new LinkedList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.size(); i++) {
                suffixList.add(jsonArray.getString(i));
            }
        }
        List<SourceFileInfo> list = FileOperation.scan(srcDir, suffixList);
        JSONObject resultJson = new JSONObject();
        resultJson.put(KEY_SCAN_MODE, scanMode);
        if (list != null) {
            switch (scanMode) {
                case SCAN_MODE_CLASSIFY:
                    for (SourceFileInfo fileInfo : list) {
                        JSONArray array = resultJson.getJSONArray(fileInfo.suffix);
                        if (array == null) {
                            array = new JSONArray();
                            resultJson.put(fileInfo.suffix, array);
                        }
                        array.add(fileInfo.file.getAbsolutePath());
                    }
                    break;
                case SCAN_MODE_NORMAL:
                    JSONArray array = new JSONArray();
                    for (SourceFileInfo fileInfo : list) {
                        array.add(fileInfo.file.getAbsolutePath());
                    }
                    resultJson.put(KEY_RESULT_FILE_PATH, array);
                    break;
            }
        }
        return resultJson;
    }

    @Override
    public void onError(int errorCode, String message) {
        Log.e(TAG, "do scan error, code is '%d', message is '%s'", errorCode, message);
    }
}
