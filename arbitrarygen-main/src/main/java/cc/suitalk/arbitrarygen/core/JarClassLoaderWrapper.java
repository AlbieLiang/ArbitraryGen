package cc.suitalk.arbitrarygen.core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author AlbieLiang
 *
 */
public class JarClassLoaderWrapper {

	private List<File> mJarFiles;
	private URLClassLoader mLoader;

	public JarClassLoaderWrapper() {
		mJarFiles = new LinkedList<>();
	}

	public Class<?> loadClass(String className) throws ClassNotFoundException,
			MalformedURLException {
		URLClassLoader loader = getClassLoader();
		if (loader != null) {
			return loader.loadClass(className);
		}
		return null;
	}

	private URLClassLoader getClassLoader() throws MalformedURLException {
		if (mLoader == null) {
			URL[] urls = new URL[mJarFiles.size()];
			for (int i = 0; i < urls.length; i++) {
				urls[i] = mJarFiles.get(i).toURI().toURL();
			}
			mLoader = new URLClassLoader(urls);
		}
		return mLoader;
	}

	/**
	 * The method will work only before the method
	 * {@link #loadClass(String className)} was invoked.
	 * 
	 * @param file
	 */
	public boolean addJar(File file) {
		if (file != null && file.exists() && file.isFile() && !contains(file)) {
			mJarFiles.add(file);
			return true;
		}
		return false;
	}

	/**
	 * The method will work only before the method
	 * {@link #loadClass(String className)} was invoked.
	 * 
	 * @param file
	 */
	public void removeJar(File file) {
		mJarFiles.remove(file);
	}

	public boolean contains(File file) {
		if (file != null) {
			for (int i = 0; i < mJarFiles.size(); i++) {
				File f = mJarFiles.get(i);
				if (f == file || f.getAbsolutePath().equals(file.getAbsolutePath())) {
					return true;
				}
			}
		}
		return false;
	}
}
