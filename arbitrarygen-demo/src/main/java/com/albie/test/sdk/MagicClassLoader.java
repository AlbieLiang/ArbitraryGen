package com.albie.test.sdk;

import com.albie.test.sdk.annotation.MagicTest;

/**
 * 
 * @author AlbieLiang
 *
 */
public class MagicClassLoader extends ClassLoader {

	@Override
	protected Class<?> findClass(String className)
			throws ClassNotFoundException {
		Class<?> clazz = super.findClass(className);
		if (clazz != null) {
			MagicTest test = clazz.getAnnotation(MagicTest.class);
			if (test != null) {
				
			}
		}
		return clazz;
	}

	@Override
	public Class<?> loadClass(String className) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return super.loadClass(className);
	}

	@Override
	protected Class<?> loadClass(String className, boolean resolve)
			throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return super.loadClass(className, resolve);
	}

}
