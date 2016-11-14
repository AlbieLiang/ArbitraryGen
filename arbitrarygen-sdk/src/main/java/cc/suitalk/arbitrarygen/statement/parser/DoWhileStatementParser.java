package cc.suitalk.arbitrarygen.statement.parser;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.statement.DoWhileStatement;
import cc.suitalk.arbitrarygen.statement.PlainStatement;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DoWhileStatementParser extends BaseStatementParser {

	public DoWhileStatementParser() {
		super("do");
	}

	@Override
	public DoWhileStatement parse(IReader reader, Lexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "do".equals(curWord.value)) {
				PlainStatementParser parser = ParserFactory.getPlainStatementParser();
				Word word = curWord;
				PlainStatement pstm = parser.parse(reader, lexer, word);
				if (pstm != null) {
					DoWhileStatement stm = new DoWhileStatement();
					stm.setWordDo(curWord);
//					s.setCommendBlock(getCommendStr());
					stm.setCodeBlock(pstm.getCodeBlock());
					word = parser.getLastWord();
					if (!"while".equals(word.value)) {
						throw new RuntimeException("missed 'while' when parse do-while statement.");
					}
					stm.setWordWhile(word);
					word = nextWord(reader, lexer);
					Expression e = Util.extractExpressionFromBlacket(reader, lexer, word, this);
					if (e == null) {
						throw new RuntimeException("extract Expression from blacket failed.");
					}
					stm.setConditionExpression(e);
					word = nextWord(reader, lexer);
					if (!";".equals(word.value)) {
						throw new RuntimeException("missing a ';' sign when parse do-while statement.");
					}
					return stm;
				} else {
					throw new RuntimeException("Parse do-while statement failed.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
