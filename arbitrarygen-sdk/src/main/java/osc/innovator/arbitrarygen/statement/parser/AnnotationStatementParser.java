package osc.innovator.arbitrarygen.statement.parser;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.base.BaseStatementParser;
import osc.innovator.arbitrarygen.core.ParserFactory;
import osc.innovator.arbitrarygen.core.Value;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.core.Value.ValueType;
import osc.innovator.arbitrarygen.core.Word.WordType;
import osc.innovator.arbitrarygen.expression.ReferenceExpression;
import osc.innovator.arbitrarygen.expression.parser.ReferenceExpressionParser;
import osc.innovator.arbitrarygen.extension.ILexer;
import osc.innovator.arbitrarygen.model.TypeName;
import osc.innovator.arbitrarygen.statement.AnnotationStatement;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class AnnotationStatementParser extends BaseStatementParser {

	public AnnotationStatementParser() {
		super("@");
	}

	@Override
	public AnnotationStatement parse(IReader reader, ILexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord == null || !"@".equals(curWord.value)) {
				return null;
			}
			Word word = nextWord(reader, lexer);
			TypeName.Parser tnp = ParserFactory.getTypeNameParser();
			AnnotationStatement annotation = new AnnotationStatement(tnp.parse(reader, lexer, word));
			annotation.setPrefixWord(curWord);
			setLastWord(tnp.getLastWord());
			word = getLastWord();
//			annotation.setCommendBlock(getCommendStr());
//			word = nextWord(reader, lexer);
			if (word != null && "(".equals(word.value)) {
				annotation.setWordLeftBracket(word);
				if ((word = nextWord(reader, lexer)) == null) {
					doThrow(annotation);
				}
				if (!")".equals(word.value)) {
					if (word.type == WordType.STRING) {
						String name = word.value;
						word = nextWord(reader, lexer);
						if (word.type != WordType.SIGN) {
							doThrow(annotation);
						}
						if (".".equals(word.value)) {
							ReferenceExpressionParser parser = ParserFactory.getRefExpressionParser(true);
							ReferenceExpression e = parser.parse(reader, lexer, null);
							Word lastWord = parser.getLastWord();
							if (")".equals(lastWord.value)) {
								StringBuilder builder = new StringBuilder();
								builder.append(name);
								builder.append(word.value);
								builder.append(e.genCode(""));
								annotation.setValue(new Value(null, ValueType.REFERENCE, builder.toString()));
							} else {
								doThrow(annotation);
							}
						} else if ("=".equals(word.value)) {
							Value value = Util.getValue(reader, lexer, this);
							if (value == null) {
								doThrow(annotation);
							}
							annotation.setArg(name, value);
							word = getLastWord();
							while (",".equals(word.value)) {
								word = nextWord(reader, lexer);
								if (word.type != WordType.STRING) {
									doThrow(annotation);
									break;
								}
								String keyName = word.value;
								word = nextWord(reader, lexer);
								if (!"=".equals(word.value)) {
									doThrow(annotation);
									break;
								}
								value = Util.getValue(reader, lexer, this);
								if (value == null) {
									doThrow(annotation);
									break;
								}
								annotation.setArg(keyName, value);
								word = getLastWord();
								if (")".equals(word.value)) {
									break;
								}
							}
						}
					} else {
						annotation.setValue(Util.convertTo(word));
						word = nextWord(reader, lexer);
						if (!")".equals(word.value)) {
							doThrow(annotation);
						}
					}
				}
				if (!")".equals(word.value)) {
					doThrow(annotation);
				}
				annotation.setWordRightBracket(word);
				nextWord(reader, lexer);
			}
			return annotation;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void doThrow(AnnotationStatement s) {
		throw new RuntimeException("An error has occured when parse Annotation '@" + s.getName() + "'.");
	}
}