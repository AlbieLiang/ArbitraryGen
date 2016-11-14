package cc.suitalk.arbitrarygen.statement.parser;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.statement.SyncStatement;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class SyncStatementParser extends BaseStatementParser {

	public SyncStatementParser() {
		super("synchronized");
	}

	@Override
	public SyncStatement parse(IReader reader, Lexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "synchronized".equals(curWord.value)) {
				SyncStatement syncStm = new SyncStatement();
				syncStm.setPrefixWord(curWord);
				Word word = nextWord(reader, lexer);
//				syncStm.setCommendBlock(getCommendStr());
				Expression condition = Util.extractExpressionFromBlacket(reader, lexer, word, this);
				if (condition == null) {
					throw new RuntimeException("extract expression from blacket failed.");
				}
				syncStm.setConditionExpression(condition);
				Util.getAndAttachCodeBlock(reader, lexer, word, syncStm, this);
				word = getLastWord();
				return syncStm;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
