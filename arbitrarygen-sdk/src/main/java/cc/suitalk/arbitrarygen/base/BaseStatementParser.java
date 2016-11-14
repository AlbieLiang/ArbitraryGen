package cc.suitalk.arbitrarygen.base;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.extension.Lexer;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BaseStatementParser extends BaseCodeParser {

	private String mPrefix;
//	private String mCommendStr;
	
	public BaseStatementParser(String prefix) {
		mPrefix = prefix;
	}

	public BaseStatement parse(IReader reader, Lexer lexer, Word curWord) throws IOException {
		doPreParse(reader, lexer, curWord);
		return null;
	}
	
	protected void doPreParse(IReader reader, Lexer lexer, Word curWord) throws IOException {
//		CommendStatementParser commStmParser = ParserFactory.getCommendStatementParser();
//		NormalStatement nStm = commStmParser.parse(reader, lexer, curWord);
//		setCommendStr(Util.nullAsNil(nStm != null ? nStm.getCommendBlock() : ""));
//		setLastWord(commStmParser.getLastWord());
		setLastWord(curWord);
	}
	
	public String getPrefix() {
		return mPrefix;
	}

	public void setPrefix(String prefix) {
		this.mPrefix = prefix;
	}

//	public String getCommendStr() {
//		return mCommendStr;
//	}
//
//	public void setCommendStr(String mCommendStr) {
//		this.mCommendStr = mCommendStr;
//	}
}
