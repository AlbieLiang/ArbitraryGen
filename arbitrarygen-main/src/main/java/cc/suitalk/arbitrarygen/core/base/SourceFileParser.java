package cc.suitalk.arbitrarygen.core.base;

import java.io.File;

/**
 * Created by albieliang on 16/10/28.
 */
public interface SourceFileParser<ResultType> {

    boolean match(String suffix);

    ResultType parse(File file);
}
