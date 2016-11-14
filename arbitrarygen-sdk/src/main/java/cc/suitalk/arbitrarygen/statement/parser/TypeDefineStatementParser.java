package cc.suitalk.arbitrarygen.statement.parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseDefineCodeBlock;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.block.FieldCodeBlock;
import cc.suitalk.arbitrarygen.block.MethodCodeBlock;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Value;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.core.Word.WordType;
import cc.suitalk.arbitrarygen.expression.parser.PlainExpressionParser;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.model.KeyValuePair;
import cc.suitalk.arbitrarygen.model.TypeName;
import cc.suitalk.arbitrarygen.statement.AnnotationStatement;
import cc.suitalk.arbitrarygen.statement.PlainStatement;
import cc.suitalk.arbitrarygen.statement.StaticStatement;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TypeDefineStatementParser extends BaseStatementParser {

	private static final String TAG = "AG.TypeDefineStatementParser";
	
	private Map<String, String> mModifierKeywords = new HashMap<String, String>();
	
	public TypeDefineStatementParser() {
		super("");
		mModifierKeywords.put("private", "private");
		mModifierKeywords.put("public", "public");
		mModifierKeywords.put("protected", "protected");
		mModifierKeywords.put("static", "static");
		mModifierKeywords.put("final", "final");
		mModifierKeywords.put("abstract", "abstract");
		mModifierKeywords.put("synchronized", "synchronized");
	}

	@Override
	public TypeDefineCodeBlock parse(IReader reader, Lexer lexer, Word curWord) {
		setLastWord(curWord);
		if (curWord == null) {
			return null;
		}
		try {
			TypeDefineCodeBlock typeDefine = new TypeDefineCodeBlock();
			Map<String, Word> keywords = new HashMap<String, Word>();
			// Parse Annotation
			Word word = curWord;
			List<AnnotationStatement> annoStms = Util.parseAndAddAnnotation(reader, lexer, word, this);
			word = getLastWord();
			if (annoStms != null && annoStms.size() > 0) {
				typeDefine.addAnnotations(annoStms);
			}
			boolean error = true;
			// Parse modifier
			while (!"{".equals(word.value) && word.type != WordType.DOC_END) {
				if (word.type != WordType.STRING) {
					throw new RuntimeException("Error has occurred when parse type define code block before '{' appear.");
				}
				keywords.put(word.value, word);
				if ("class".equals(word.value) || "interface".equals(word.value) || "enum".equals(word.value) || "@interface".equals(word.value)) {
//					typeDefine.addModifierWord(word);
					error = false;
					break;
				}
				typeDefine.addModifierWord(word);
				word = nextWord(reader, lexer);
			}
			if (error) {
				throw new RuntimeException("type define keyword is missing.");
			}
			attachModifiers(typeDefine, keywords);
			if (keywords.size() > 0) {
				throw new RuntimeException("Has some undefine keywords in type define code block.");
			}
			TypeName.Parser parser = ParserFactory.getTypeNameParser();
			word = nextWord(reader, lexer);
			if (word.type != WordType.STRING) {
				throw new RuntimeException("Type name is error.");
			}
			typeDefine.setName(parser.parse(reader, lexer, word));
			word = parser.getLastWord();
//			ReferenceExpressionParser parser = ParserFactory.getRefExpressionParser(true);
			if ("extends".equals(word.value)) {
				typeDefine.setWordExtends(word);
				TypeName tn = parser.parse(reader, lexer, null);
				if (tn == null) {
					throw new RuntimeException("extends class has error.");
				}
				typeDefine.setParent(tn);
				word = parser.getLastWord();
			}
			if ("implements".equals(word.value)) {
				typeDefine.setWordImplements(word);
				do {
					TypeName tn = parser.parse(reader, lexer, null);
					if (tn == null) {
						throw new RuntimeException("implements interface has error.");
					}
					typeDefine.addInterface(tn);
					word = parser.getLastWord();
				} while (word.value.equals(","));
			}
			if (!"{".equals(word.value)) {
				throw new RuntimeException("miss the '{' sign.");
			}
			//
			parseTypeCodeBlock(reader, lexer, word, typeDefine);
			return typeDefine;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void attachModifiers(BaseDefineCodeBlock typeDefine, Map<String, Word> keywords) {
		typeDefine.setIsFinal(keywords.remove("final") != null);
		typeDefine.setIsStatic(keywords.remove("static") != null);
		typeDefine.setIsAbstract(keywords.remove("abstract") != null);
		typeDefine.setIsSynchronized(keywords.remove("synchronized") != null);
		// modifier
		Word modifierWord = keywords.remove("public");
		if (modifierWord == null) {
			modifierWord = keywords.remove("protected");
		}
		if (modifierWord == null) {
			modifierWord = keywords.remove("private");
		}
		typeDefine.setModifier(modifierWord != null ? modifierWord.value : "");
		// type
		Word typeWord = keywords.remove("class");
		if (typeWord == null) {
			typeWord = keywords.remove("interface");
		}
		if (typeWord == null) {
			typeWord = keywords.remove("enum");
		}
		if (typeWord == null) {
			typeWord = keywords.remove("@interface");
		}
		typeDefine.setType(typeWord != null ? Util.createSimpleTypeName(typeWord) : null);
	}

	private void parseTypeDefineCodeBlock(IReader reader, Lexer lexer, Word curWord, TypeDefineCodeBlock typeDefine, Map<String, Word> keywords) throws IOException {
		Word word = curWord;
		attachModifiers(typeDefine, keywords);
		if (keywords.size() > 0) {
			throw new RuntimeException("Has some undefine keywords in type define code block.");
		}
		TypeName.Parser parser = ParserFactory.getTypeNameParser();
		word = nextWord(reader, lexer);
		if (word.type != WordType.STRING) {
			throw new RuntimeException("Type name is error.");
		}
		typeDefine.setName(parser.parse(reader, lexer, word));
		word = parser.getLastWord();
		if ("extends".equals(word.value)) {
			typeDefine.setWordExtends(word);
			TypeName parentName = parser.parse(reader, lexer, null);
			if (parentName == null) {
				throw new RuntimeException("extends class has error.");
			}
			typeDefine.setParent(parentName);
			word = parser.getLastWord();
		}
		if ("implements".equals(word.value)) {
			typeDefine.setWordImplements(word);
			do {
				TypeName interfaceName = parser.parse(reader, lexer, null);
				if (interfaceName == null) {
					throw new RuntimeException("implements interface has error.");
				}
				typeDefine.addInterface(interfaceName);
				word = parser.getLastWord();
			} while (",".equals(word.value));
		}
		if (!"{".equals(word.value)) {
			throw new RuntimeException("miss the '{' sign.");
		}
		//
		parseTypeCodeBlock(reader, lexer, word, typeDefine);
	}
	
	private void parseTypeCodeBlock(IReader reader, Lexer lexer, Word curWord, TypeDefineCodeBlock typeDefine) throws IOException {
		if (curWord == null || !"{".equals(curWord.value)) {
			throw new RuntimeException("parseTypeCodeBlock failed, curWord is (" + curWord + ")");
		}
		PlainCodeBlock codeblock = typeDefine.getCodeBlock();
		codeblock.setLeftBrack(curWord);
		Word word = nextWord(reader, lexer);
		while (word != null && word.type != WordType.DOC_END && !"}".equals(word.value)) {
			if ("{".equals(word.value)) {
				PlainStatementParser p = ParserFactory.getPlainStatementParser();
				PlainStatement pstm = p.parse(reader, lexer, word);
				setLastWord(p.getLastWord());
				if (pstm == null) {
					throw new RuntimeException("parsePlainCodeBlock failed.");
				}
				typeDefine.addStatement(pstm);
			} else {
				parseAndAddStatement(reader, lexer, word, typeDefine);
			}
			word = getLastWord();
		}
		codeblock.setRightBrack(word);
		nextWord(reader, lexer);
	}

	private void parseAndAddStatement(IReader reader, Lexer lexer, Word curWord, TypeDefineCodeBlock typeDefine) throws IOException {
		LinkedList<String> keywords = new LinkedList<String>();
		Map<String, Word> keywordMap = new HashMap<String, Word>();
		// Parse Annotation
		Word word = curWord;
		List<AnnotationStatement> annoStms = Util.parseAndAddAnnotation(reader, lexer, curWord, this);
		word = getLastWord();
		// Empty statement
		if (";".equals(word.value) || "}".equals(word.value)) {
			Log.i(TAG, "empty statement or the end of the PlainStatement.(current word :" + word.value + ")");
//			nextWord(reader, lexer);
			return;
		}
		do {
//			Log.i(TAG, "while , current word (" + word.value + ").");
			if (mModifierKeywords.containsKey(word.value)) {
				keywords.add(word.value);
				keywordMap.put(word.value, word);
				if ("static".equals(word.value)) {
					Word tempWord = word;
					word = nextWord(reader, lexer);
					// static code block
					if ("{".equals(word.value)) {
						PlainStatementParser psp = ParserFactory.getPlainStatementParser();
						PlainStatement pStm = psp.parse(reader, lexer, word);
						setLastWord(word = psp.getLastWord());
						if (pStm == null) {
							throw new RuntimeException("parse Static CodeBlock failed.");
						}
						StaticStatement s = new StaticStatement();
						s.setPrefixWord(tempWord);
						s.setCodeBlock(pStm.getCodeBlock());
						typeDefine.addStatement(s);
						return;
					} else if (mModifierKeywords.containsKey(word.value)) {
						keywords.add(word.value);
						keywordMap.put(word.value, word);
						continue;
					}
				} else {
					continue;
				}
			}
			if ("class".equals(word.value) || "interface".equals(word.value) || "enum".equals(word.value) || "@interface".equals(word.value)) {
				keywordMap.put(word.value, word);
//				keywords.add(word.value);
				TypeDefineCodeBlock tdCodeBlock = new TypeDefineCodeBlock();
				attachModifierKeywords(tdCodeBlock, keywords, keywordMap);
				parseTypeDefineCodeBlock(reader, lexer, word, tdCodeBlock, keywordMap);
				typeDefine.addStatement(tdCodeBlock);
				return;
			}
			break;
		} while ((word = nextWord(reader, lexer)) != null && word.type != WordType.DOC_END);
		word = getLastWord();
		TypeName.Parser parser = ParserFactory.getTypeNameParser();
		TypeName typeExpression = parser.parse(reader, lexer, word);
		
		setLastWord(parser.getLastWord());
		Word nameWord = word = parser.getLastWord();
		String typeStr = typeExpression.getName();
		String nameStr = nameWord.value;
		// constructor
		if (typeDefine.getName().getName().equals(typeStr) && "(".equals(nameStr)) {
			nameStr = typeStr;
			typeStr = "";
		} else {
			word = nextWord(reader, lexer);
		}
		// method
		if ("(".equals(word.value)) {
			MethodCodeBlock mcb = new MethodCodeBlock();
			mcb.setWordLeftBracket(word);
			mcb.addAnnotations(annoStms);
			attachModifierKeywords(mcb, keywords, keywordMap);
			attachModifiers(mcb, keywordMap);
			if (keywordMap.size() > 0) {
				throw new RuntimeException("Has some undefine keywords in type define code block.");
			}
			mcb.setType(Util.isNullOrNil(typeStr) ? null : typeExpression);
			mcb.setName(Util.isNullOrNil(typeStr) ? typeExpression : Util.createSimpleTypeName(nameWord));
			word = nextWord(reader, lexer);
			// TODO albieliang, resolve annotation case
			if (word.type == WordType.STRING/* || word.type == WordType.ANNOTATION*/) {
				while (true) {
					TypeName re = parser.parse(reader, lexer, word);
					if (re == null) {
						throw new RuntimeException("method params type error.");
					}
					Word name = parser.getLastWord();
					if (name.type != WordType.STRING) {
						throw new RuntimeException("method params error.(current word :" + name.value + ")");
					}
					mcb.addArg(new KeyValuePair<Word, TypeName>(name, re));
					word = nextWord(reader, lexer);
					if (",".equals(word.value)) {
						word = nextWord(reader, lexer);
						continue;
					} else if (")".equals(word.value)) {
						break;
					} else {
						throw new RuntimeException("parse method params error.");
					}
				}	
			} else if (!")".equals(word.value)) {
				throw new RuntimeException("missing ')' sign.");
			}
			mcb.setWordRightBracket(word);
			word = nextWord(reader, lexer);
			typeDefine.addMethod(mcb);
			if ("throws".equals(word.value)) {
				mcb.setWordThrows(word);
				word = nextWord(reader, lexer);
				do {
					TypeName tn = parser.parse(reader, lexer, word);
					mcb.addThrows(tn);
					setLastWord(parser.getLastWord());
					word = getLastWord();
				} while (",".equals(word.value));
			}
			if (";".equals(word.value)) {
				nextWord(reader, lexer);
			} else if ("{".equals(word.value)) {
				PlainStatementParser p = ParserFactory.getPlainStatementParser();
				PlainStatement pstm = p.parse(reader, lexer, word);
				setLastWord(p.getLastWord());
				if (pstm == null) {
					throw new RuntimeException("parsePlainCodeBlock failed.(current word : " + getLastWord().value + ").");
				} 
				mcb.setCodeBlock(pstm.getCodeBlock());
			} else {
				throw new RuntimeException("parse method failed, do not have a ';' or method body.");
			}
			return;
		}
		FieldCodeBlock fcb = new FieldCodeBlock();
		attachModifierKeywords(fcb, keywords, keywordMap);
		attachModifiers(fcb, keywordMap);
		fcb.addAnnotations(annoStms);
		fcb.setType(typeExpression);
		fcb.setName(Util.createSimpleTypeName(nameWord));
		typeDefine.addField(fcb);
		if (";".equals(word.value)) {
		} else if ("=".equals(word.value)) {
			fcb.setWordAssignment(word);
			word = nextWord(reader, lexer);
			Value value = Util.convertTo(word);
			if (value != null) {
				fcb.setDefault(value.toString());
				word = nextWord(reader, lexer);
				if (!";".equals(word.value)) {
					throw new RuntimeException("missing ';' sign.");
				}
			} else {
				PlainExpressionParser peParser = ParserFactory.getPlainExpressionParser();
				Expression e = peParser.parse(reader, lexer, word);
				if (e == null) {
					throw new RuntimeException("An error occurred when Parse field.");
				}
				fcb.setDefault(e.genCode(""));
				word = peParser.getLastWord();
			}
		} else {
			throw new RuntimeException("An error occurred when Parse field.");
		}
		fcb.setSuffixWord(word);
		nextWord(reader, lexer);
	}
	
	private void attachModifierKeywords(BaseDefineCodeBlock tdCodeBlock, List<String> keywords, Map<String, Word> keywordMap) {
		for (int i = 0; i < keywords.size(); i++) {
			Word w = keywordMap.get(keywords.get(i));
			if (w != null) {
				tdCodeBlock.addModifierWord(w);
			}
		}
	}
}
