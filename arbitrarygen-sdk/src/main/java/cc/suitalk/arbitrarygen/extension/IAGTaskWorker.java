package cc.suitalk.arbitrarygen.extension;

import java.util.Map;

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.core.ConfigInfo;
import cc.suitalk.arbitrarygen.model.ArbitraryGenTaskInfo;


/**
 * 
 * @author AlbieLiang
 *
 */
public interface IAGTaskWorker {

	boolean doTask(ConfigInfo configInfo, ArbitraryGenTaskInfo task, JavaFileObject fileObject, Map<String, ArbitraryGenTaskInfo> srcGenTasks, Map<String, ArbitraryGenTaskInfo> targetTasks);
}
