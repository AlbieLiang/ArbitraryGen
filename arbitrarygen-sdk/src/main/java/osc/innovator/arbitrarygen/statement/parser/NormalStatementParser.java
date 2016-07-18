package osc.innovator.arbitrarygen.statement.parser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.base.BaseStatementParser;
import osc.innovator.arbitrarygen.base.Expression;
import osc.innovator.arbitrarygen.core.ParserFactory;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.core.Word.WordType;
import osc.innovator.arbitrarygen.expression.parser.PlainExpressionParser;
import osc.innovator.arbitrarygen.extension.ILexer;
import osc.innovator.arbitrarygen.statement.NormalStatement;
import osc.innovator.arbitrarygen.statement.PlainStatement;

/**
 * 
 * @author AlbieLiang
 *
 */
public class NormalStatementParser extends BaseStatementParser {

	private static final String TAG = "NormalStatementParser";
	
	private List<BaseStatementParser> mParsers; 
	public NormalStatementParser() {
		super("");
		mParsers = new LinkedList<BaseStatementParser>();
		mParsers.add(new IfElseStatementParser());
		mParsers.add(new SwitchStatementParser());
		mParsers.add(new SyncStatementParser());
		mParsers.add(new ForStatementParser());
		mParsers.add(new TryStatementParser());
		mParsers.add(new ThrowStatementParser());
		mParsers.add(new WhileStatementParser());
		mParsers.add(new DoWhileStatementParser());
		mParsers.add(new ReturnStatementParser());
	}

	@Override
	public BaseStatement parse(IReader reader, ILexer lexer, Word curWord) throws IOException {
		super.parse(reader, lexer, curWord);
		Word word = getLastWord();
		if ("{".equals(word.value)) {
			PlainStatementParser parser = ParserFactory.getPlainStatementParser();
			PlainStatement statement = parser.parse(reader, lexer, word);
			word = parser.getLastWord();
			if (statement != null) {
//				statement.setCommendBlock(getCommendStr());
				setLastWord(parser.getLastWord());
				return statement;
			} else {
				// TODO empty statement
				throw new RuntimeException("Parse statement failed.(current word : " + word.value + ")");
			}
		} else if(word.type == WordType.STRING) {
			for (BaseStatementParser parser : mParsers) {
				BaseStatement s = parser.parse(reader, lexer, word);
				if (s != null) {
					setLastWord(parser.getLastWord());
					return s;
				}
			}
		} else if (word.type != WordType.DOC_END && "}".equals(word.value) || ")".equals(word.value)) {
			return null;
		}
		PlainExpressionParser peParser = ParserFactory.getPlainExpressionParser();
		Expression e = peParser.parse(reader, lexer, word);
		if (e == null) {
			throw new RuntimeException("Parse Plain expression failed.");
		}
		NormalStatement nstm = new NormalStatement();
//		nstm.setCommendBlock(getCommendStr());
		nstm.setStatementStr(e.genCode(""));
		nextWord(reader, lexer);
		return nstm;
	}
}
