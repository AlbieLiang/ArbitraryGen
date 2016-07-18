package osc.innovator.arbitrarygen.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import osc.innovator.arbitrarygen.extension.model.Command;
import osc.innovator.arbitrarygen.base.BaseDefineCodeBlock;
import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.base.JavaFileObject;
import osc.innovator.arbitrarygen.block.FieldCodeBlock;
import osc.innovator.arbitrarygen.block.TypeDefineCodeBlock;
import osc.innovator.arbitrarygen.expression.ReferenceExpression;
import osc.innovator.arbitrarygen.extension.BaseAGTaskWorker;
import osc.innovator.arbitrarygen.model.ArbitraryGenTaskInfo;
import osc.innovator.arbitrarygen.model.AutoGenFindViewHelper.FindViewIdsTask;
import osc.innovator.arbitrarygen.statement.AnnotationStatement;
import osc.innovator.arbitrarygen.statement.ImportStatement;
import osc.innovator.arbitrarygen.statement.NormalStatement;
import osc.innovator.arbitrarygen.utils.Log;
import osc.innovator.arbitrarygen.utils.SignatureCreator;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class AutoGenFindViewTaskWorker extends BaseAGTaskWorker {

	private static final String TAG = "GenCode.AutoGenFindViewTaskWorker";

	public AutoGenFindViewTaskWorker() {
		super(Command.Type.COMMAND_EXTRACT_VIEW_ID);
	}
	
	@Override
	public boolean doTask(ArbitraryGenTaskInfo task, JavaFileObject fileObject, Map<String, ArbitraryGenTaskInfo> srcGenTasks, Map<String, ArbitraryGenTaskInfo> targetTasks) {

		AnnotationStatement stm = task.getMatchAnnotations().get(Constants.NEED_TO_HANDLE_SOURCE_LOCATION_ANNOTATION);
		if (stm == null) {
			return false;
		}
		String key = SignatureCreator.create(SignatureCreator.TYPE_ANNOTATION, Constants.NEED_TO_HANDLE_SOURCE_LOCATION_ANNOTATION, stm.getArg("command").toString());
		ArbitraryGenTaskInfo targetTask = targetTasks.get(key);
		if (targetTask == null) {
			return false;
		}
		// obtain view id
		String fileName = AutoGenFindViewHelper.getLayoutFileName(task.getCodeBlock());
		Log.d(TAG, "layout file name : " + fileName);

		Set<String> needImportSet = new HashSet<String>();
		
		List<FindViewIdsTask> fvidTasks = AutoGenFindViewHelper.getIdsFromLayoutFile(fileName);
		if (fvidTasks != null && fvidTasks.size() != 0) {
			BaseDefineCodeBlock cb = targetTask.getCodeBlock();
			BaseStatement outerStm = cb.getOuterStatement();
			TypeDefineCodeBlock outerCodeBlock = null;
			if (outerStm instanceof TypeDefineCodeBlock) {
				outerCodeBlock = (TypeDefineCodeBlock) outerStm;
			}
			for (FindViewIdsTask fvidTask : fvidTasks) {
				// TODO
				Log.d(TAG, "extracted view : " + fvidTask);
				if (outerCodeBlock != null) {
					FieldCodeBlock field = new FieldCodeBlock();
					field.setModifier("private");
					field.setType(Util.createSimpleTypeName(fvidTask.getType()));
					field.setName(Util.createSimpleTypeName(fvidTask.getId()));
					outerCodeBlock.addField(field);
					// Replace the default EnvironmentArgs
					field.attachEnvironmentArgs(Util.obtainEnvArgs(field));
				}
				String Import = AutoFindFindViewConstants.getMappingImport(fvidTask.getType());
				if (!Util.isNullOrNil(Import)) {
					needImportSet.add(Import);
				} else {
					// TODO
					needImportSet.add(fvidTask.getType()); 
				}
				NormalStatement nstm = new NormalStatement(fvidTask.getId() + " = (" + fvidTask.getType() + ") findViewById(R.id." + fvidTask.getId() + ")");
				cb.addStatement(nstm);
				nstm.attachEnvironmentArgs(Util.obtainEnvArgs(nstm));
			}
			for (String _import : needImportSet) {
				// Import android.view
				ImportStatement importStm = new ImportStatement(new ReferenceExpression(_import));
				fileObject.addImport(importStm);
				importStm.attachEnvironmentArgs(Util.obtainEnvArgs(importStm));
			}
			return true;
		}
		return false;
	}
}
