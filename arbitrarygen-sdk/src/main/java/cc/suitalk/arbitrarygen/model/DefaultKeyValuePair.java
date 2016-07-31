package cc.suitalk.arbitrarygen.model;

import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DefaultKeyValuePair extends KeyValuePair<Word, TypeName> {

	public DefaultKeyValuePair(Word name, TypeName type) {
		super(name, type);
	}
	
	public DefaultKeyValuePair(String name, String type) {
		this(Util.createKeyWord(name), Util.createSimpleTypeName(type));
	}
}
