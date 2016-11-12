package cc.suitalk.arbitrarygen.model;

import java.util.Map;

import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.ConfigInfo;
import cc.suitalk.arbitrarygen.expression.ReferenceExpression;
import cc.suitalk.arbitrarygen.extension.BaseAGTaskWorker;
import cc.suitalk.arbitrarygen.extension.model.Command;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/10.
 */
public class RenameClassTaskWorker extends BaseAGTaskWorker {

	private static final String TAG = "AG.RenameClassTaskWorker";

	public RenameClassTaskWorker() {
		super(Command.Type.COMMAND_RENAME_CLASS);
	}
	
	@Override
	public boolean doTask(ConfigInfo configInfo, ArbitraryGenTaskInfo task, JavaFileObject fileObject,
						  Map<String, ArbitraryGenTaskInfo> srcGenTasks, Map<String, ArbitraryGenTaskInfo> targetTasks) {
//		AnnotationStatement stm = task.getMatchAnnotations().get(Constants.NEED_TO_HANDLE_TASK_ANNOTATION);
//		if (stm == null) {
//			return false;
//		}
		TypeDefineCodeBlock codeBlock = fileObject.getTheFileCodeBlock(true);
		if (codeBlock == null) {
			return false;
		}
		TypeName typeName = codeBlock.getName();
		if (typeName == null) {
			return false;
		}
		ReferenceExpression expression = typeName.getNameRefExpression();
		if (expression == null) {
			return false;
		}
		expression.appendNode(Util.createKeyWord("$$Pyro"));
		return true;
	}
}
