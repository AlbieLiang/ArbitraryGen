package cc.suitalk.arbitrarygen.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.MethodCodeBlock;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ConfigInfo;
import cc.suitalk.arbitrarygen.extension.IAGTaskWorker;
import cc.suitalk.arbitrarygen.extension.ITypeDefineWrapper;
import cc.suitalk.arbitrarygen.model.ArbitraryGenTaskInfo;
import cc.suitalk.arbitrarygen.model.AutoGenFindViewTaskWorker;
import cc.suitalk.arbitrarygen.model.Constants;
import cc.suitalk.arbitrarygen.model.ExtractJsApiFuncInfoWorker;
import cc.suitalk.arbitrarygen.model.KeepTaskWorker;
import cc.suitalk.arbitrarygen.model.RunInMainThreadTaskWorker;
import cc.suitalk.arbitrarygen.model.RunInWorkerThreadTaskWorker;
import cc.suitalk.arbitrarygen.model.TypeName;
import cc.suitalk.arbitrarygen.statement.AnnotationStatement;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.SignatureCreator;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DefaultTypeDefineWrapper implements ITypeDefineWrapper {

	private static final String TAG = "AG.DefaultTypeDefineWrapper";

	private Set<IAGTaskWorker> mWorkers;
	
	public DefaultTypeDefineWrapper() {
		mWorkers = new HashSet<IAGTaskWorker>();
		addIAGTaskWorker(new AutoGenFindViewTaskWorker());
		addIAGTaskWorker(new KeepTaskWorker());
		addIAGTaskWorker(new RunInMainThreadTaskWorker());
		addIAGTaskWorker(new RunInWorkerThreadTaskWorker());
		addIAGTaskWorker(new ExtractJsApiFuncInfoWorker());
	}
	
	@Override
	public boolean doWrap(ConfigInfo configInfo, JavaFileObject fileObject) {
		if (fileObject != null) {
			Log.d(TAG, "doWrap, fileName : " + fileObject.getFileName());
			List<TypeDefineCodeBlock> typeDefineCodeBlocks = new LinkedList<TypeDefineCodeBlock>();
			// find need to handle TypeDefineCodeBlock
			for (int i = 0; i < fileObject.getCountOfTypeDefCodeBlock(); i++) {
				TypeDefineCodeBlock typeDef = fileObject.getTypeDefineCodeBlock(i);
				if (typeDef == null) {
					continue;
				}
				for (int j = 0; j < typeDef.countOfAnnotations(); j++) {
					AnnotationStatement stm = typeDef.getAnnotation(j);
					if (stm == null) {
						continue;
					}
					TypeName name = stm.getName();
					if (name == null) {
						continue;
					}
					String nameStr = name.getName();
					Log.d(TAG, "annotation : " + nameStr);
					if (!Constants.NEED_TO_HANDLE_TASK_ANNOTATION.equals(nameStr)) {
						continue;
					}
					typeDefineCodeBlocks.add(typeDef);
				}
			}
			if (typeDefineCodeBlocks.size() > 0) {
				Map<String, ArbitraryGenTaskInfo> srcGenTasks = new HashMap<String, ArbitraryGenTaskInfo>();
				Map<String, ArbitraryGenTaskInfo> targetTasks = new HashMap<String, ArbitraryGenTaskInfo>();
				for (int i = 0; i < typeDefineCodeBlocks.size(); i++) {
					TypeDefineCodeBlock typeDef = typeDefineCodeBlocks.get(i);
					for (int j = 0; j < typeDef.countOfMethods(); j++) {
						MethodCodeBlock m = typeDef.getMethod(j);
						if (m == null) {
							continue;
						}
						for (int k = 0; k < m.countOfAnnotations(); k++) {
							AnnotationStatement stm = m.getAnnotation(k);
							if (stm == null) {
								continue;
							}
							TypeName name = stm.getName();
							if (name == null) {
								continue;
							}
							ArbitraryGenTaskInfo task = new ArbitraryGenTaskInfo();
							task.setCodeBlock(m);
							String nameStr = name.getName();
							Log.d(TAG, "method annotation : " + nameStr);
							if (Constants.NEED_TO_HANDLE_SOURCE_LOCATION_ANNOTATION.equals(nameStr)) {
								// TODO
								task.getMatchAnnotations().put(nameStr, stm);
								String key = SignatureCreator.create(SignatureCreator.TYPE_ANNOTATION, Constants.NEED_TO_HANDLE_SOURCE_LOCATION_ANNOTATION, stm.getArg("command").toString());
								task.setKey(key);
								srcGenTasks.put(key, task);
							} else if (Constants.NEED_TO_HANDLE_TARGET_LOCATION_ANNOTATION.equals(nameStr)) {
								// TODO
								task.getMatchAnnotations().put(nameStr, stm);
								String key = SignatureCreator.create(SignatureCreator.TYPE_ANNOTATION, Constants.NEED_TO_HANDLE_SOURCE_LOCATION_ANNOTATION, stm.getArg("command").toString());
								task.setKey(key);
								targetTasks.put(key, task);
							} else {
								task.getMatchAnnotations().put(nameStr, stm);
								String key = SignatureCreator.create(SignatureCreator.TYPE_ANNOTATION, nameStr, m.getName().getName());
								task.setKey(key);
								srcGenTasks.put(key, task);
							}
						}
					}
				}
				boolean hasTask = false;
				Log.d(TAG, "srcGenTasks size : " + srcGenTasks.size() + ", targetTasks size : " + targetTasks.size());
				if (srcGenTasks.size() > 0) {
					hasTask = true;
					for (ArbitraryGenTaskInfo task : srcGenTasks.values()) {
						for (IAGTaskWorker worker : mWorkers) {
							worker.doTask(configInfo, task, fileObject, srcGenTasks, targetTasks);
						}
					}
				}
				return hasTask;
			}
		}
		return false;
	}

	@Override
	public boolean doWrap(ConfigInfo configInfo, TypeDefineCodeBlock codeblock) {
		if (codeblock != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean addIAGTaskWorker(IAGTaskWorker worker) {
		if (worker != null) {
			return mWorkers.add(worker);
		}
		return false;
	}
	
	@Override
	public boolean removeIAGTaskWorker(IAGTaskWorker worker) {
		return mWorkers.remove(worker);
	}
}
