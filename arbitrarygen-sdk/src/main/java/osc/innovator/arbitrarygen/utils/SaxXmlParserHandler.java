package osc.innovator.arbitrarygen.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import osc.innovator.arbitrarygen.model.KeyValuePair;
import osc.innovator.arbitrarygen.template.RawTemplate;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * A SAX XML parser handler.
 * 
 * @author AlbieLiang
 * 
 */
public class SaxXmlParserHandler extends DefaultHandler {

	public static final int MODE_NORMAL = 0;
	public static final int MODE_EXTRACT_ATTR = 1;
	private StringBuilder mBuilder;
	private List<RawTemplate> mXmlNotes;
	private List<RawTemplate> mUnCloseNoteQueue;
	private Map<String, List<RawTemplate>> mDetailAttrNoteSet;
	private Set<String> mAttrSet;
	private int mMode;

	public SaxXmlParserHandler() {
		mXmlNotes = new LinkedList<RawTemplate>();
		mBuilder = new StringBuilder();
		mUnCloseNoteQueue = new LinkedList<RawTemplate>();
		mDetailAttrNoteSet = new HashMap<String, List<RawTemplate>>();
		mAttrSet = new HashSet<String>();
	}

	@Override
	public void startDocument() throws SAXException {
		mBuilder.delete(0, mBuilder.length());
		mXmlNotes.clear();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		RawTemplate t = new RawTemplate();
		t.setName(qName);
		if (attributes != null) {
			String attrName = null;
			int i = 0;
			for (; i < attributes.getLength(); i++) {
				attrName = attributes.getQName(i);
				t.addAttribute(new KeyValuePair<String, String>(attrName, attributes.getValue(attrName)));
			}
			if (mMode == MODE_EXTRACT_ATTR && i > 0) {
				for (String attr : mAttrSet) {
					if (t.getAttributes().containsKey(attr)) {
						List<RawTemplate> list = mDetailAttrNoteSet.get(attr);
						if (list == null) {
							list = new LinkedList<RawTemplate>();
							mDetailAttrNoteSet.put(attr, list);
						}
						list.add(t);
					}
				}
			}
		}
		if (mUnCloseNoteQueue.size() > 0) {
			RawTemplate parent = mUnCloseNoteQueue.get(mUnCloseNoteQueue.size() - 1);
			if (parent != null) {
				parent.addElement(t);
			}
		}
		mUnCloseNoteQueue.add(t);
		mBuilder.setLength(0);
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		mBuilder.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (mUnCloseNoteQueue.size() > 0) {
			RawTemplate t = mUnCloseNoteQueue.remove(mUnCloseNoteQueue.size() - 1);
			if (t != null) {
				t.setContent(mBuilder.toString());
			}
			if (mUnCloseNoteQueue.size() == 0) {
				mXmlNotes.add(t);
			}
		}
	}

	/**
	 * Xml format file only have a root note, but we want to extend the format
	 * so use {@link List} to store it.
	 * 
	 * @return
	 */
	public List<RawTemplate> getXmlNotes() {
		return mXmlNotes;
	}

	/**
	 * {@link #MODE_NORMAL} or {@link #MODE_EXTRACT_ATTR}
	 * 
	 * @param mode
	 */
	public void setMode(int mode) {
		mMode = mode;
	}
	
	public void addExtractAttr(String attr) {
		if (!Util.isNullOrNil(attr)) {
			mAttrSet.add(attr);
		}
	}
	
	public boolean removeExtractAttr(String attr) {
		return mAttrSet.remove(attr);
	}
	
	public List<RawTemplate> getXmlNotesByAttr(String attr) {
		return mDetailAttrNoteSet.get(attr);
	}
	
}