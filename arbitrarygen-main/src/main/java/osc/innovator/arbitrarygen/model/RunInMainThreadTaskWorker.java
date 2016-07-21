package osc.innovator.arbitrarygen.model;

import java.util.Map;

import osc.innovator.arbitrarygen.core.ConfigInfo;
import osc.innovator.arbitrarygen.extension.model.Command;
import osc.innovator.arbitrarygen.base.BaseDefineCodeBlock;
import osc.innovator.arbitrarygen.base.JavaFileObject;
import osc.innovator.arbitrarygen.base.PlainCodeBlock;
import osc.innovator.arbitrarygen.block.MethodCodeBlock;
import osc.innovator.arbitrarygen.extension.BaseAGTaskWorker;
import osc.innovator.arbitrarygen.model.ArbitraryGenTaskInfo;
import osc.innovator.arbitrarygen.statement.AnnotationStatement;
import osc.innovator.arbitrarygen.statement.NormalStatement;

/**
 * 
 * @author AlbieLiang
 *
 */
public class RunInMainThreadTaskWorker extends BaseAGTaskWorker {

	public RunInMainThreadTaskWorker() {
		super(Command.Type.COMMAND_KEEP);
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
