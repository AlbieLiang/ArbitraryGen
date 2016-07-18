package osc.innovator.arbitrarygen.extension.annotation;

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

	/**
	 * 
	 * @return
	 */
	int command() default 0;
	
	/**
	 * 
	 * @return
	 */
	int type() default 0;
	
	/**
	 * 
	 * @return
	 */
	boolean finished() default false;
	
	/**
	 * 
	 * @return
	 */
	long modifyTime() default 0;
}
