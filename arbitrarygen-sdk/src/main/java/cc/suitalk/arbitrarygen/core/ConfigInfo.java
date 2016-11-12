package cc.suitalk.arbitrarygen.core;

import java.io.File;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ConfigInfo {
//	private String mRootDir;
//	private String mDestDir;
//	private String mDestFolder;
//	private String mSrcFolder;
//	private String mSrcDir;

	
	private String mRootPath;
	private String mDestPath;
	private String mSrcPath;
	private File mFile;

	public ConfigInfo() {
		mRootPath = System.getProperty("user.dir");
		setDestPath(mRootPath + "/gen");
		setSrcPath(mRootPath + "/autogen");
	}

//	public void setDestFolder(String destFolder) {
//		if (!Util.isNullOrNil(destFolder)) {
//			mDestFolder = destFolder;
//			mDestDir = mRootDir + File.separator + mDestFolder;
//		}
//	}
//
//	public void setSrcFolder(String srcFolder) {
//		if (!Util.isNullOrNil(srcFolder)) {
//			mSrcFolder = srcFolder;
//			mSrcDir = mRootDir + File.separator + mSrcFolder;
//		}
//	}

//	public String getDestDir() {
//		return mDestDir;
//	}
//
//	public String getSrcDir() {
//		return mSrcDir;
//	}
	
	public String getDestPath() {
		return mDestPath;
	}

	public void setDestPath(String destPath) {
		mDestPath = destPath;
	}

	public String getSrcPath() {
		return mSrcPath;
	}
	
	public void setSrcPath(String srcPath) {
		mSrcPath = srcPath;
	}
	
	public String getRootPath() {
		return mRootPath;
	}

	public File getFile() {
		return mFile;
	}

	public void setFile(File file) {
		mFile = file;
	}
}
