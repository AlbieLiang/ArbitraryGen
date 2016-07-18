package osc.innovator.arbitrarygen.statement.parser;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.base.BaseStatementParser;
import osc.innovator.arbitrarygen.base.Expression;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.extension.ILexer;
import osc.innovator.arbitrarygen.statement.WhileStatement;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class WhileStatementParser extends BaseStatementParser {

	public WhileStatementParser() {
		super("while");
	}

	@Override
	public WhileStatement parse(IReader reader, ILexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "while".equals(curWord.value)) {
				WhileStatement whileStm = new WhileStatement();
				whileStm.setPrefixWord(curWord);
				Word word = nextWord(reader, lexer);
				whileStm.setWordLeftBracket(word);
//				whileStm.setCommendBlock(getCommendStr());
				Expression condition = Util.extractExpressionFromBlacket(reader, lexer, word, this);
				if (condition == null) {
					throw new RuntimeException("extract expression from blacket failed.");
				}
//				whileStm.setWordRightBracket(word);
				whileStm.setConditionExpression(condition);
				word = getLastWord();
				Util.getAndAttachCodeBlock(reader, lexer, word, whileStm, this);
				word = getLastWord();
				return whileStm;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
