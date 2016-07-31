package cc.suitalk.arbitrarygen.statement.parser;

import java.util.HashMap;
import java.util.Map;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.extension.ILexer;

/**
 * 
 * @author AlbieLiang
 *
 */
public class EnumDefineStatementParser extends BaseStatementParser {

	public EnumDefineStatementParser() {
		super("import");
	}

	@Override
	public TypeDefineCodeBlock parse(IReader reader, ILexer lexer, Word curWord) {
		setLastWord(curWord);
		if (curWord == null) {
			return null;
		}
		TypeDefineCodeBlock typeDefine = new TypeDefineCodeBlock();
		Map<String, Word> keywords = new HashMap<String, Word>();
		Word word = curWord;
		while (!word.value.equals("{")) {
			if (word.type != WordType.STRING) {
				throw new RuntimeException("Error has occurred when parse type define code block before '{' appear.");
			}

			if (word.value.equals("class")) {
				;
			} else if (word.value.equals("interface")) {
				;
			} else if (word.value.equals("enum")) {
				;
			} else if (word.value.equals("@interface")) {
				// TODO
			}
			keywords.put(word.value, word);
		}
		return null;
	}
}