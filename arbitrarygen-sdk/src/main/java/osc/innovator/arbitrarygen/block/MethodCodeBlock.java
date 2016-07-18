package osc.innovator.arbitrarygen.block;

import java.util.LinkedList;
import java.util.List;

import osc.innovator.arbitrarygen.base.BaseDefineCodeBlock;
import osc.innovator.arbitrarygen.base.PlainCodeBlock;
import osc.innovator.arbitrarygen.core.KeyWords;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.model.KeyValuePair;
import osc.innovator.arbitrarygen.model.TypeName;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class MethodCodeBlock extends BaseDefineCodeBlock {
	protected List<KeyValuePair<Word, TypeName>> mArgs;
	private List<TypeName> mThrows;
	private Word mWordThrows;
	
	public MethodCodeBlock() {
		mArgs = new LinkedList<KeyValuePair<Word, TypeName>>();
		mThrows = new LinkedList<TypeName>();
	}

	public boolean addArg(KeyValuePair<Word, TypeName> kvPair) {
		return mArgs.add(kvPair);
	}

	@Override
	public String genCode(String linefeed) {
		MethodCodeBlock mt = (MethodCodeBlock) this;
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);
//		builder.append(genCommendBlock(linefeed));
//		builder.append(genAnnotationBlock(linefeed));
		builder.append(genDefCode(this, linefeed));
		// Insert args of the method
		builder.append(Util.getLeftBlacket(this));
		//
		if (mt.mArgs != null && mt.mArgs.size() > 0) {
			KeyValuePair<Word, TypeName> kv = mt.mArgs.get(0);
			if (kv != null) {
				builder.append(kv.getValue());
				builder.append(blank);
				builder.append(kv.getKey());
			}
			for (int i = 1; i < mt.mArgs.size(); i++) {
				kv = mt.mArgs.get(i);
				if (kv != null) {
					builder.append(KeyWords.V_JAVA_KEYWORDS_SIGN_COMMA);
					builder.append(blank);
					builder.append(kv.getValue());
					builder.append(blank);
					builder.append(kv.getKey());
				}
			}
		}
		builder.append(Util.getRightBlacket(this));
		if (mThrows.size() > 0) {
			builder.append(blank);
			builder.append(mWordThrows != null ? mWordThrows : "throws");
			builder.append(blank);
			builder.append(mThrows.get(0).genCode(""));
			for (int i = 1; i < mThrows.size(); i++) {
				builder.append(", ");
				builder.append(mThrows.get(i).genCode(""));
			}
		}
		PlainCodeBlock codeblock = getCodeBlock();
		if (codeblock != null) {
			builder.append(blank);
			builder.append(codeblock.genCode(linefeed));
		} else {
			builder.append(";");
		}
		return builder.toString();
	}

	public List<TypeName> getThrows() {
		return mThrows;
	}

	public void addThrows(TypeName throws1) {
		mThrows.add(throws1);
	}

	public Word getWordThrows() {
		return mWordThrows;
	}

	public void setWordThrows(Word wordThrows) {
		this.mWordThrows = wordThrows;
	}
}