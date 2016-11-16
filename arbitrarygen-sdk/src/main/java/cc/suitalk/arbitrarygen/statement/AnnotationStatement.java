package cc.suitalk.arbitrarygen.statement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.core.Value;
import cc.suitalk.arbitrarygen.model.TypeName;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class AnnotationStatement extends BaseStatement {

	private TypeName mName;
	private Value mValue;
	private Map<String, Value> mArgs;

	public AnnotationStatement(String name) {
		mName = Util.createSimpleTypeName(name);
		mArgs = new HashMap<>();
	}
	
	public AnnotationStatement(TypeName name) {
		mName = name;
		mArgs = new HashMap<>();
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "@"));
		builder.append(mName);
		if (mArgs.size() > 0) {
			builder.append(Util.getLeftBracket(this));
			Iterator<String> keys = mArgs.keySet().iterator();
			String key = null;
			if (keys.hasNext()) {
				key = keys.next();
				appendArg(builder, key, mArgs.get(key));
			}
			while (keys.hasNext()) {
				key = keys.next();
				builder.append(",");
				appendArg(builder, key, mArgs.get(key));
			}
			builder.append(Util.getRightBracket(this));
		} else if (mValue != null) {
			builder.append(Util.getLeftBracket(this));
			builder.append(mValue.getValueStr());
			builder.append(Util.getRightBracket(this));
		}
		return builder.toString();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		if (!mArgs.isEmpty()) {
			for (String key : mArgs.keySet()) {
				jsonObject.put(key, mArgs.get(key).getValue());
			}
		} else if (mValue != null) {
			jsonObject.put("_value", mValue.getValue());
		}
		return jsonObject;
	}

	private void appendArg(StringBuilder builder, String key, Value value) {
		if (key != null && !key.equals("") && value != null) {
			builder.append(key);
			builder.append("=");
			builder.append(value);
		}
	}

	public TypeName getName() {
		return mName;
	}

	public void setName(TypeName name) {
		this.mName = name;
	}

	public Value getArg(String key) {
		return mArgs.get(key);
	}

	public void setArg(String key, Value value) {
		this.mArgs.put(key, value);
	}

	public Value getValue() {
		return mValue;
	}

	public void setValue(Value value) {
		this.mValue = value;
	}
}
