package cc.suitalk.arbitrarygen.extension;

import net.sf.json.JSONObject;

import java.util.Set;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;

/**
 * Created by AlbieLiang on 16/11/12.
 */
public interface AGAnnotationProcessor {

    Set<String> getSupportedAnnotationTypes();

    boolean process(JSONObject env, JavaFileObject fileObject, TypeDefineCodeBlock typeDefineCodeBlock,
                    Set<? extends BaseStatement> containsSpecialAnnotationStatements);
}