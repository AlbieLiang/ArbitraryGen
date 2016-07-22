package osc.innovator.arbitrarygen.model;

import java.util.Map;

import osc.innovator.arbitrarygen.core.ConfigInfo;
import osc.innovator.arbitrarygen.extension.model.Command;
import osc.innovator.arbitrarygen.base.JavaFileObject;
import osc.innovator.arbitrarygen.extension.BaseAGTaskWorker;
import osc.innovator.arbitrarygen.model.ArbitraryGenTaskInfo;

/**
 * 
 * @author AlbieLiang
 *
 */
public class RunInWorkerThreadTaskWorker extends BaseAGTaskWorker {

	public RunInWorkerThreadTaskWorker() {
		super(Command.Type.COMMAND_KEEP);
	}

	@Override
	public boolean doTask(ConfigInfo configInfo, ArbitraryGenTaskInfo task, JavaFileObject fileObject, Map<String, ArbitraryGenTaskInfo> srcGenTasks, Map<String, ArbitraryGenTaskInfo> targetTasks) {
		// TODO Auto-generated method stub
		return false;
	}

}
