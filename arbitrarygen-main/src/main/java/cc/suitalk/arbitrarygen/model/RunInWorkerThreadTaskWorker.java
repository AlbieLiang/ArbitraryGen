package cc.suitalk.arbitrarygen.model;

import java.util.Map;

import cc.suitalk.arbitrarygen.core.ConfigInfo;
import cc.suitalk.arbitrarygen.extension.model.Command;
import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.extension.BaseAGTaskWorker;

/**
 * 
 * @author AlbieLiang
 *
 */
public class RunInWorkerThreadTaskWorker extends BaseAGTaskWorker {

	public RunInWorkerThreadTaskWorker() {
		super(Command.Type.COMMAND_RUN_IN_WORKER_THREAD);
	}

	@Override
	public boolean doTask(ConfigInfo configInfo, ArbitraryGenTaskInfo task, JavaFileObject fileObject, Map<String, ArbitraryGenTaskInfo> srcGenTasks, Map<String, ArbitraryGenTaskInfo> targetTasks) {
		// TODO Auto-generated method stub
		return false;
	}

}
