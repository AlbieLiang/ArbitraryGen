package osc.innovator.arbitrarygen.extension;

import java.util.Map;

import osc.innovator.arbitrarygen.base.JavaFileObject;
import osc.innovator.arbitrarygen.model.ArbitraryGenTaskInfo;


/**
 * 
 * @author AlbieLiang
 *
 */
public interface IAGTaskWorker {

	boolean doTask(ArbitraryGenTaskInfo task, JavaFileObject fileObject, Map<String, ArbitraryGenTaskInfo> srcGenTasks, Map<String, ArbitraryGenTaskInfo> targetTasks);
}
