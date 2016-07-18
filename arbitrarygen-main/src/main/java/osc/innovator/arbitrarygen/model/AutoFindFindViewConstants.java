package osc.innovator.arbitrarygen.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author AlbieLiang
 *
 */
public final class AutoFindFindViewConstants {
	
	private static Map<String, String> ViewImportMap;
	
	private AutoFindFindViewConstants() {
	}
	
	public static Map<String, String> getViewImportMap() {
		if (ViewImportMap == null) {
			ViewImportMap = new HashMap<String, String>();
			ViewImportMap.put("View", "android.view.View");
			ViewImportMap.put("TextView", "android.widget.TextView");
			ViewImportMap.put("ImageView", "android.widget.ImageView");
			ViewImportMap.put("Button", "android.widget.Button");
			ViewImportMap.put("LinearLayout", "android.widget.LinearLayout");
			ViewImportMap.put("RelativeLayout", "android.widget.RelativeLayout");
			ViewImportMap.put("FrameLayout", "android.widget.FrameLayout");
			ViewImportMap.put("AbsoluteLayout", "android.widget.AbsoluteLayout");
			ViewImportMap.put("TableLayout", "android.widget.TableLayout");
			ViewImportMap.put("GridLayout", "android.widget.GridLayout");
			
			ViewImportMap.put("ListView", "android.widget.ListView");
			ViewImportMap.put("ScrollView", "android.widget.ScrollView");
			// Add more here...
		}
		return ViewImportMap;
	}
	
	public static String getMappingImport(String view) {
		return getViewImportMap().get(view);
	}
}