package cc.suitalk.arbitrarygen.statement.parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

	private static final String TAG = "AG.NormalStatementParser";
	
	private Map<String, BaseStatementParser> mParserMap;

	public NormalStatementParser() {
		super("");
		mParserMap = new HashMap<>();
		addParser(new IfElseStatementParser());
		addParser(new SwitchStatementParser());
		addParser(new SyncStatementParser());
		addParser(new ForStatementParser());
		addParser(new TryStatementParser());
		addParser(new ThrowStatementParser());
		addParser(new WhileStatementParser());
		addParser(new DoWhileStatementParser());
		addParser(new ReturnStatementParser());
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
				setLastWord(parser.getLastWord());
				return statement;
			} else {
				throw new RuntimeException("Parse statement failed.(current word : " + word.value + ")");
			}
		} else if(word.type == WordType.STRING) {
			BaseStatementParser parser = mParserMap.get(word.value);
			if (parser != null) {
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
		nstm.setStatementStr(e.genCode(""));
		nstm.setSuffixWord(peParser.getLastWord());
		nextWord(reader, lexer);
		return nstm;
	}

	private void addParser(BaseStatementParser parser) {
		mParserMap.put(parser.getPrefix(), parser);
	}
}
