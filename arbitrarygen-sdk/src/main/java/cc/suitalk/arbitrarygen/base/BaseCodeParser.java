package cc.suitalk.arbitrarygen.base;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.extension.ILexer;

public abstract class BaseCodeParser {

    private Word mLastWord;

    /**
     * Use {@link #nextWord(IReader, ILexer)} to get next word.
     *
     * @param reader a  implement of {@link IReader}
     * @param lexer  a {@link ILexer} for parse
     * @param curWord current word
     *
     * @return a {@link ICodeGenerator}
     *
     * @throws IOException if parse fail IOException may be throw out
     */
    public abstract ICodeGenerator parse(IReader reader, ILexer lexer, Word curWord) throws IOException;

    public Word getLastWord() {
        return mLastWord;
    }

    public void setLastWord(Word lastWord) {
        mLastWord = lastWord;
    }

    public Word nextWord(IReader reader, ILexer lexer) throws IOException {
        return mLastWord = lexer.getWord(reader);
    }
}
