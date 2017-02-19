/*
 *  Copyright (C) 2016-present Albie Liang. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
