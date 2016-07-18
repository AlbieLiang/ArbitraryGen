package osc.innovator.arbitrarygen.base;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.extension.ILexer;

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

	public BaseStatement parse(IReader reader, ILexer lexer, Word curWord) throws IOException {
		doPreParse(reader, lexer, curWord);
		return null;
	}
	
	protected void doPreParse(IReader reader, ILexer lexer, Word curWord) throws IOException {
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
