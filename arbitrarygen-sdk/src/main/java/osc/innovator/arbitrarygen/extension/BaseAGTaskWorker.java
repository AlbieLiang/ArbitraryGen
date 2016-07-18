package osc.innovator.arbitrarygen.extension;

import osc.innovator.arbitrarygen.base.BaseDefineCodeBlock;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class BaseAGTaskWorker implements IAGTaskWorker {

	private int mCommand;
	private BaseDefineCodeBlock mSourceCodeBlock;
	private BaseDefineCodeBlock mTargetCodeBlock;

	public BaseAGTaskWorker(int command) {
		this.mCommand = command;
	}

	public int getCommand() {
		return mCommand;
	}

	public void setCommand(int command) {
		this.mCommand = command;
	}

	public BaseDefineCodeBlock getSourceCodeBlock() {
		return mSourceCodeBlock;
	}

	public void setSourceCodeBlock(BaseDefineCodeBlock sourceCodeBlock) {
		this.mSourceCodeBlock = sourceCodeBlock;
	}

	public BaseDefineCodeBlock getTargetCodeBlock() {
		return mTargetCodeBlock;
	}

	public void setTargetCodeBlock(BaseDefineCodeBlock targetCodeBlock) {
		this.mTargetCodeBlock = targetCodeBlock;
	}
}
