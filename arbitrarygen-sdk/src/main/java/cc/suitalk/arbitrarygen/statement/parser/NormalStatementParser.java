package cc.suitalk.arbitrarygen.statement.parser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.expression.parser.PlainExpressionParser;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.statement.NormalStatement;
import cc.suitalk.arbitrarygen.statement.PlainStatement;

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
	public BaseStatement parse(IReader reader, Lexer lexer, Word curWord) throws IOException {
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
