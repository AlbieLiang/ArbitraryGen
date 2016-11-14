package cc.suitalk.arbitrarygen.base;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.extension.Lexer;

public abstract class BaseCodeParser {

    private Word mLastWord;

    /**
     * Use {@link #nextWord(IReader, Lexer)} to get next word.
     *
     * @param reader a  implement of {@link IReader}
     * @param lexer  a {@link Lexer} for parse
     * @param curWord current word
     *
     * @return a {@link ICodeGenerator}
     *
     * @throws IOException if parse fail IOException may be throw out
     */
    public abstract ICodeGenerator parse(IReader reader, Lexer lexer, Word curWord) throws IOException;

    public Word getLastWord() {
        return mLastWord;
    }

    public void setLastWord(Word lastWord) {
        mLastWord = lastWord;
    }

    public Word nextWord(IReader reader, Lexer lexer) throws IOException {
        return mLastWord = lexer.getWord(reader);
    }
}
