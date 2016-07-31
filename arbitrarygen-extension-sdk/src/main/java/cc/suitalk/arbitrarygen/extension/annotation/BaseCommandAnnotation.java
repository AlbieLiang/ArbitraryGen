package cc.suitalk.arbitrarygen.extension.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author AlbieLiang
 * @since 1.0
 * 
 */
@Retention(RetentionPolicy.SOURCE)
public @interface BaseCommandAnnotation {

	int command() default 0;

	int type() default 0;

	boolean finished() default false;

	long modifyTime() default 0;
}
