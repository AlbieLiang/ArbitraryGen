package cc.suitalk.arbitrarygen.model;

import java.util.Map;

import cc.suitalk.arbitrarygen.core.ConfigInfo;
import cc.suitalk.arbitrarygen.extension.model.Command;
import cc.suitalk.arbitrarygen.base.BaseDefineCodeBlock;
import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.block.MethodCodeBlock;
import cc.suitalk.arbitrarygen.extension.BaseAGTaskWorker;
import cc.suitalk.arbitrarygen.statement.AnnotationStatement;
import cc.suitalk.arbitrarygen.statement.NormalStatement;

/**
 * 
 * @author AlbieLiang
 *
 */
public class RunInMainThreadTaskWorker extends BaseAGTaskWorker {

	public RunInMainThreadTaskWorker() {
		super(Command.Type.COMMAND_RUN_IN_MAIN_THREAD);
	}

	@Override
	public boolean doTask(ConfigInfo configInfo, ArbitraryGenTaskInfo task, JavaFileObject fileObject, Map<String, ArbitraryGenTaskInfo> srcGenTasks, Map<String, ArbitraryGenTaskInfo> targetTasks) {
		AnnotationStatement stm = task.getMatchAnnotations().get(Constants.NEED_TO_HANDLE_RUN_IN_MAIN_THREAD_ANNOTATION);
		if (stm == null) {
			return false;
		}
		BaseDefineCodeBlock bdcb = task.getCodeBlock();
		if (!(bdcb instanceof MethodCodeBlock)) {
			return false;
		}
		MethodCodeBlock mcb = (MethodCodeBlock) bdcb;
		PlainCodeBlock pcb = mcb.getCodeBlock();
		if (pcb == null) {
			return false;
		}
		PlainCodeBlock newPCB = new PlainCodeBlock();
		mcb.setCodeBlock(newPCB);
		StringBuilder builder = new StringBuilder();
		builder.append("(new Handler()).post(new Runnable() {");
		builder.append("@Override\n");
		builder.append("public void run()");
		builder.append(pcb.genCode(""));
		builder.append("})");
		
		NormalStatement nstm = new NormalStatement(builder.toString());
		mcb.addStatement(nstm);
		return false;
	}

}
