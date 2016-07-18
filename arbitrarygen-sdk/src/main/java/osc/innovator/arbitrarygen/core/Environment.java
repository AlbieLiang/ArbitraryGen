package osc.innovator.arbitrarygen.core;

import osc.innovator.arbitrarygen.utils.Util;


/**
 * 
 * @author AlbieLiang
 *
 */
public class Environment {

	public static class EnvironmentArgs {
		
//		private static final String TAG = "CodeGen.EnvironmentArgs";
		public static final int SOURCE_TYPE_NORMAL_TEMPLATE = 0;
		public static final int SOURCE_TYPE_JAVA = 1;
		public static final int MODE_NORMAL = 0;
		public static final int MODE_FORCE_LINE_FEED = 0x1;
		private int mSourceFileType;
		/**
		 * A bit set
		 */
		private int mFlag;

		public int getSourceFileType() {
			return mSourceFileType;
		}

		public void setSourceFileType(int sourceFileType) {
			this.mSourceFileType = sourceFileType;
		}
		
		public boolean isJavaFile() {
			return mSourceFileType == SOURCE_TYPE_JAVA;
		}

		public int getFlag() {
			return mFlag;
		}

		public void setFlag(int flag) {
			this.mFlag = flag;
		}
		
		public void addFlag(int flag) {
			mFlag = Util.setBitSetValue(mFlag, flag, true);
		}
		
		public void removeFlag(int flag) {
			mFlag = Util.setBitSetValue(mFlag, flag, false);
		}

		public boolean checkFlag(int flag) {
			return Util.checkBitSetValue(mFlag, flag);
		}
		
		public EnvironmentArgs copy() {
			EnvironmentArgs args = new EnvironmentArgs();
			args.setSourceFileType(this.getSourceFileType());
			args.setFlag(this.getFlag());
			return args;
		}
	}
	
}
