package cc.suitalk.arbitrarygen.extension;

import java.io.File;

/**
 * Created by AlbieLiang on 16/10/28.
 */
public interface SourceFileParser<ResultType> {

    boolean match(String suffix);

    ResultType parse(File file);
}
