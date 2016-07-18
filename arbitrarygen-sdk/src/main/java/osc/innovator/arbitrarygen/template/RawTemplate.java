package osc.innovator.arbitrarygen.template;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import osc.innovator.arbitrarygen.base.Session;
import osc.innovator.arbitrarygen.model.KeyValuePair;

/**
 * 
 * @author AlbieLiang
 *
 */
public class RawTemplate extends Session {

	private String mName;
	private String mContent;
	private int mType;
	private Map<String, String> mAttributes;
	private List<RawTemplate> mElements;

	public RawTemplate() {
		mAttributes = new HashMap<String, String>();
		mElements = new LinkedList<RawTemplate>();
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String content) {
		this.mContent = content;
	}

	public int getType() {
		return mType;
	}

	public void setType(int type) {
		this.mType = type;
	}

	public Map<String, String> getAttributes() {
		return mAttributes;
	}

	public List<RawTemplate> getElements() {
		return mElements;
	}

	public void addAttribute(KeyValuePair<String, String> attr) {
		if (attr != null) {
			mAttributes.put(attr.getKey(), attr.getValue());
		}
	}

	public void addElement(RawTemplate e) {
		mElements.add(e);
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
