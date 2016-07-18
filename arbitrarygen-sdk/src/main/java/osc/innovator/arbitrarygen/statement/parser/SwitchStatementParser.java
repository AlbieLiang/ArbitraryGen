package osc.innovator.arbitrarygen.statement.parser;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.base.BaseStatementParser;
import osc.innovator.arbitrarygen.base.Expression;
import osc.innovator.arbitrarygen.core.ParserFactory;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.core.Word.WordType;
import osc.innovator.arbitrarygen.extension.ILexer;
import osc.innovator.arbitrarygen.statement.CaseStatement;
import osc.innovator.arbitrarygen.statement.DefaultStatement;
import osc.innovator.arbitrarygen.statement.SwitchStatement;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class SwitchStatementParser extends BaseStatementParser {

	private static final String TAG = "CodeGen.SwitchStatementParser";

	public SwitchStatementParser() {
		super("switch");
	}

	@Override
	public SwitchStatement parse(IReader reader, ILexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "switch".equals(curWord.value)) {
				SwitchStatement switchStm = new SwitchStatement();
				switchStm.setPrefixWord(curWord);
				Word word = nextWord(reader, lexer);
				switchStm.setWordLeftBracket(word);
				Expression condition = Util.extractExpressionFromBlacket(reader, lexer, word, this);
				if (condition == null) {
					throw new RuntimeException("extract expression from blacket failed.");
				}
				switchStm.setConditionExpression(condition);
//				switchStm.setCommendBlock(getCommendStr());
				word = getLastWord();
				if (!"{".equals(word.value)) {
					throw new RuntimeException("missing '{' when parse switch statement.");
				}
				switchStm.setLeftBrack(word);
				word = nextWord(reader, lexer);
				while ("case".equals(word.value)) {
					Word tempWord = word;
					Expression expression = new Expression();
					expression.setVariable(Util.extractExpressionWithEndSign(reader, lexer, this, ":"));
					CaseStatement caseStm = new CaseStatement(expression);
					caseStm.setPrefixWord(tempWord);
					caseStm.setWordColon(getLastWord());
					switchStm.addCaseStatment(caseStm);
					word = nextWord(reader, lexer);
					while (!"}".equals(word.value) && word.type != WordType.DOC_END) {
						if ("default".equals(word.value) || "case".equals(word.value)) {
							break;
						}
						NormalStatementParser parser = ParserFactory.getNormalStatementParser();
						BaseStatement nStm = parser.parse(reader, lexer, word);
						caseStm.addStatement(nStm);
						word = parser.getLastWord();
					}
				}
				if ("default".equals(word.value)) {
					DefaultStatement defaultStm = new DefaultStatement();
					switchStm.setDefaultStatement(defaultStm);
					defaultStm.setPrefixWord(word);
					word = nextWord(reader, lexer);
					if (!":".equals(word.value)) {
						throw new RuntimeException("missing ':' sign.");
					}
					defaultStm.setWordColon(word);
					word = nextWord(reader, lexer);
					while (!"}".equals(word.value) && word.type != WordType.DOC_END) {
						NormalStatementParser parser = ParserFactory.getNormalStatementParser();
						BaseStatement nStm = parser.parse(reader, lexer, word);
						defaultStm.addStatement(nStm);
						word = parser.getLastWord();
					}
				}
				if (!"}".equals(word.value)) {
					throw new RuntimeException("missing '}' sign when parse switch statement.");
				}
				switchStm.setRightBrack(word);
				nextWord(reader, lexer);
				return switchStm;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
