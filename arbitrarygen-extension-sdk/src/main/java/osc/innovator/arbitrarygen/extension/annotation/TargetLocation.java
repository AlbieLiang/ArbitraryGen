package osc.innovator.arbitrarygen.extension.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import osc.innovator.arbitrarygen.extension.model.Command;


/**
 * 
 * @author AlbieLiang
 * @since 1.0
 * 
 */
@Retention(RetentionPolicy.SOURCE)
public @interface TargetLocation {

	/**
	 * 
	 * @return
	 */
	int command() default 0;
	
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
	
	/**
	 * {@link Command#Location}
	 * 
	 * @return
	 */
	long location() default 0;
}
