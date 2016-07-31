package cc.suitalk.arbitrarygen.statement;

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
		mArgs = new HashMap<String, Value>();
	}
	
	public AnnotationStatement(TypeName name) {
		mName = name;
		mArgs = new HashMap<String, Value>();
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "@"));
		builder.append(mName);
		if (mArgs.size() > 0) {
			builder.append(Util.getLeftBlacket(this));
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
			builder.append(Util.getRightBlacket(this));
		} else if (mValue != null) {
			builder.append(Util.getLeftBlacket(this));
			builder.append(mValue.getValueStr());
			builder.append(Util.getRightBlacket(this));
		}
		return builder.toString();
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
