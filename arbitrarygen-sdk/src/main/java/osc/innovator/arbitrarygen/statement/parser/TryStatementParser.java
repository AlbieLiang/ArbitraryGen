package osc.innovator.arbitrarygen.statement.parser;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.base.BaseStatementParser;
import osc.innovator.arbitrarygen.core.ParserFactory;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.extension.ILexer;
import osc.innovator.arbitrarygen.statement.CatchStatement;
import osc.innovator.arbitrarygen.statement.DefinitionStatement;
import osc.innovator.arbitrarygen.statement.FinallyStatement;
import osc.innovator.arbitrarygen.statement.PlainStatement;
import osc.innovator.arbitrarygen.statement.TryStatement;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TryStatementParser extends BaseStatementParser {

	public TryStatementParser() {
		super("try");
	}

	@Override
	public TryStatement parse(IReader reader, ILexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "try".equals(curWord.value)) {
				TryStatement tryStm = new TryStatement();
				tryStm.setPrefixWord(curWord);
				Word word = nextWord(reader, lexer);
//				tryStm.setCommendBlock(getCommendStr());
				Util.getAndAttachCodeBlock(reader, lexer, word, tryStm, this);
				word = getLastWord();
				PlainStatementParser parser = ParserFactory.getPlainStatementParser();
				while ("catch".equals(word.value)) {
					CatchStatement catchStm = new CatchStatement();
					catchStm.setPrefixWord(word);
					word = nextWord(reader, lexer);
					if (!"(".equals(word.value)) {
						throw new RuntimeException("missing '('.");
					}
					catchStm.setWordLeftBracket(word);
					DefinitionStatementParser defStmParser = ParserFactory.getDefinitionStatementParser();
					word = nextWord(reader, lexer);
					DefinitionStatement defStm = defStmParser.parse(reader, lexer, word);
					word = defStmParser.getLastWord();
					if (!")".equals(word.value)) {
						throw new RuntimeException("missing ')'.(current word :" + word.value + ")");
					}
					catchStm.setWordRightBracket(word);
					word = nextWord(reader, lexer);
					defStm.setSemicolon(false);
					catchStm.setDefinitionStatement(defStm);
					PlainStatement pstm = parser.parse(reader, lexer, word);
					setLastWord(parser.getLastWord());
					word = getLastWord();
					catchStm.setCodeBlock(pstm.getCodeBlock());
					tryStm.addCatchStatment(catchStm);
				}
				if ("finally".equals(word.value)) {
					FinallyStatement finallyStm = new FinallyStatement();
					finallyStm.setPrefixWord(word);
					word = nextWord(reader, lexer);
					PlainStatement pStm = parser.parse(reader, lexer, word);
					setLastWord(parser.getLastWord());
					if (pStm == null) {
						throw new RuntimeException("Parse plain statement failed.");
					}
					finallyStm.setCodeBlock(pStm.getCodeBlock());
					tryStm.setFinallyStatement(finallyStm);
				} 
				return tryStm;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
