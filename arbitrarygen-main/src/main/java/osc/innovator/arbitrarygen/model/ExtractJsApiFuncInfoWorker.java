package osc.innovator.arbitrarygen.model;

import java.util.Map;

import osc.innovator.arbitrarygen.base.JavaFileObject;
import osc.innovator.arbitrarygen.base.PlainCodeBlock;
import osc.innovator.arbitrarygen.block.MethodCodeBlock;
import osc.innovator.arbitrarygen.block.TypeDefineCodeBlock;
import osc.innovator.arbitrarygen.core.CodeGenerator;
import osc.innovator.arbitrarygen.core.ConfigInfo;
import osc.innovator.arbitrarygen.core.GenCodeTaskInfo;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.extension.IAGTaskWorker;
import osc.innovator.arbitrarygen.statement.AnnotationStatement;
import osc.innovator.arbitrarygen.statement.NormalStatement;
import osc.innovator.arbitrarygen.utils.FileOperation;
import osc.innovator.arbitrarygen.utils.Log;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ExtractJsApiFuncInfoWorker implements IAGTaskWorker {

	private static final String TAG = "CodeGen.ExtractJsApiFuncInfoWorker";
	private static final String ANNOTATION_NAME = "JsApiFuncMark";
	
	@Override
	public boolean doTask(ConfigInfo configInfo, ArbitraryGenTaskInfo task, JavaFileObject fileObject, Map<String, ArbitraryGenTaskInfo> srcGenTasks,
			Map<String, ArbitraryGenTaskInfo> targetTasks) {
		if (fileObject.getCountOfTypeDefCodeBlock() == 0) {
			Log.i(TAG, "JavaFileObject type def count is 0.");
			return false;
		}
		TypeDefineCodeBlock typeDef = fileObject.getTheFileCodeBlock(true);
		String className = typeDef.getName().getName();
		AnnotationStatement as = typeDef.getAnnotation(ANNOTATION_NAME);
		if (as == null) {
			Log.i(TAG, "annotation is null.");
			return false;
		}
		JavaFileObject fo = new JavaFileObject();
		fo.setPackage("com.tian.webview.test");
		// Import
		fo.addImport("com.tian.webview.base.BaseJsApiFunc");
		fo.addImport("com.tian.webview.base.BaseJsApiFunc.ICallbackProxy");
		fo.addImport(fileObject.getPackage() + "." + className);
		
		TypeDefineCodeBlock cb = new TypeDefineCodeBlock();
		
		cb.setName(Util.createSimpleTypeName("TestGenCode"));
		cb.setModifier("public");
		cb.setType(Util.createSimpleTypeName("class"));
		
		MethodCodeBlock mcb = new MethodCodeBlock();
		mcb.setCodeBlock(new PlainCodeBlock());
		
		mcb.setName(Util.createSimpleTypeName("addJsApiFunc"));
		mcb.addModifierWord(Util.createKeyWord("public"));
		mcb.setType(Util.createSimpleTypeName("void"));
		mcb.addArg(new KeyValuePair<Word, TypeName>(Util.createKeyWord("callbackProxy"), Util.createSimpleTypeName("ICallbackProxy")));
		
		NormalStatement ns_1 = new NormalStatement("BaseJsApiFunc func = null");
		mcb.addStatement(ns_1);
		
		NormalStatement ns = new NormalStatement("func = new " + className + "(callbackProxy)");
		mcb.addStatement(ns);
		
		ns = new NormalStatement("func.setName(" + as.getArg("name").toString() + ")");
		mcb.addStatement(ns);
		
		ns = new NormalStatement("func.setId(" + as.getArg("id").toString() + ")");
		mcb.addStatement(ns);
		
		cb.addMethod(mcb);
		
		fo.addTypeDefineCodeBlock(cb);
		
		//
		GenCodeTaskInfo taskInfo = new GenCodeTaskInfo();
		taskInfo.FileName = fo.getFileName();
		taskInfo.RootDir = configInfo.getDestPath() + Util.getPackageDir(fo);
		taskInfo.javaFileObject = fo;
		
		// GenCode
		JavaFileObject javaFileObject = taskInfo.javaFileObject;
		CodeGenerator generator = new CodeGenerator(javaFileObject);
		FileOperation.saveToFile(taskInfo, generator.genCode());
		
		Log.i(TAG, "gen successfully.");
		return true;
	}
}
