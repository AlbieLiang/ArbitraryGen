package cc.suitalk.tools.arbitrarygen.demo.processor;

import net.sf.json.JSONObject;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.expression.ReferenceExpression;
import cc.suitalk.arbitrarygen.extension.AGAnnotationProcessor;
import cc.suitalk.arbitrarygen.extension.CustomizeGenerator;
import cc.suitalk.arbitrarygen.extension.annotation.ArbitraryGenTask;
import cc.suitalk.arbitrarygen.extension.annotation.Keep;
import cc.suitalk.arbitrarygen.extension.annotation.RunInMainThread;
import cc.suitalk.arbitrarygen.extension.annotation.RunInWorkerThread;
import cc.suitalk.arbitrarygen.gencode.CodeGenerator;
import cc.suitalk.arbitrarygen.gencode.GenCodeTaskInfo;
import cc.suitalk.arbitrarygen.model.TypeName;
import cc.suitalk.arbitrarygen.protocol.EnvArgsConstants;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 16/11/12.
 */
public class TestAGAnnotationProcessor implements AGAnnotationProcessor {

	private static final String TAG = "AG.TestAGAnnotationProcessor";

	private Set<String> mSupportedAnnotationTypes;

		@Override
		public Set<String> getSupportedAnnotationTypes() {
			if (mSupportedAnnotationTypes == null) {
				mSupportedAnnotationTypes = new HashSet<>();
				mSupportedAnnotationTypes.add(RunInMainThread.class.getSimpleName());
				mSupportedAnnotationTypes.add(RunInWorkerThread.class.getSimpleName());
				mSupportedAnnotationTypes.add(Keep.class.getSimpleName());
			}
			for (String type : mSupportedAnnotationTypes) {
				Log.v(TAG, "support annotation type : %s", type);
			}
			return mSupportedAnnotationTypes;
		}

		@Override
		public boolean process(JSONObject env, JavaFileObject fileObject, TypeDefineCodeBlock typeDefineCodeBlock,
							   Set<? extends BaseStatement> containsSpecialAnnotationStatements) {
			if (typeDefineCodeBlock.getAnnotation(ArbitraryGenTask.class.getSimpleName()) == null) {
				return false;
			}
			final String outputDir = env.optString(EnvArgsConstants.KEY_INPUT_DIR);
			final String pkg = env.optString(EnvArgsConstants.KEY_PACKAGE);
			String path = env.optString(EnvArgsConstants.KEY_FILE_PATH);
			final String parentDir = (new File(path)).getParent();

			if (Util.isNullOrNil(outputDir) || Util.isNullOrNil(pkg)) {
				Log.w(TAG, "process failed, outputDir or package is null or nil.");
				return false;
			}
			if (Util.isNullOrNil(parentDir)) {
				Log.w(TAG, "process failed, parent dir is null or nil.");
				return false;
			}
			// Do rename class
			renameTypeDefine(typeDefineCodeBlock);
			for (BaseStatement statement : containsSpecialAnnotationStatements) {
				Log.i(TAG, "process statement(hashCode : %s)", statement.hashCode());
			}

			// Do gen code
			JavaFileObject fObject = new JavaFileObject();
			fObject.addTypeDefineCodeBlock(typeDefineCodeBlock);
			fObject.setPackageStatement(fileObject.getPackageStatement());
			fObject.copyImports(fileObject);
			fObject.attachEnvironmentArgs(fileObject.getEnvironmentArgs());

			GenCodeTaskInfo taskInfo = new GenCodeTaskInfo();
			taskInfo.FileName = typeDefineCodeBlock.getName().getName();
			taskInfo.RootDir = parentDir;
			taskInfo.javaFileObject = fObject;
			// GenCode
			CustomizeGenerator generator = new CodeGenerator(fObject);
			Log.i(TAG, "genCode rootDir : %s, fileName : %s, suffix : %s", taskInfo.RootDir, taskInfo.FileName, taskInfo.Suffix);
			FileOperation.saveToFile(taskInfo, generator.genCode());
			return true;
		}

		private boolean renameTypeDefine(TypeDefineCodeBlock codeBlock) {
			if (codeBlock == null) {
				return false;
			}
			TypeName typeName = codeBlock.getName();
			if (typeName == null) {
				return false;
			}
			String name = typeName.getName();
			if (Util.isNullOrNil(name)) {
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