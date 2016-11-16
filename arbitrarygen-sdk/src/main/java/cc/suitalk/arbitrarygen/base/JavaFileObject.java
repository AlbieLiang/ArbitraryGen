package cc.suitalk.arbitrarygen.base;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.core.Environment.EnvironmentArgs;
import cc.suitalk.arbitrarygen.expression.ReferenceExpression;
import cc.suitalk.arbitrarygen.statement.ImportStatement;
import cc.suitalk.arbitrarygen.statement.PackageStatement;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class JavaFileObject implements ICodeGenerator, JSONConverter {

	private String mPackage;
	private List<String> mImport;
	private PackageStatement mPackageStm;
	private List<ImportStatement> mImportStms;
	private List<TypeDefineCodeBlock> mTypeDefineCodeBlocks;
	private EnvironmentArgs mEnvironmentArgs;

	public JavaFileObject() {
		mImport = new LinkedList<>();
		mImportStms = new LinkedList<>();
		mTypeDefineCodeBlocks = new LinkedList<>();
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String headerStr = genHeader(linefeed);
		if (!Util.isNullOrNil(headerStr)) {
			builder.append(headerStr);
		}
		for (TypeDefineCodeBlock typeDefine : mTypeDefineCodeBlocks) {
			builder.append(getLinefeed(linefeed));
			builder.append(typeDefine.genCode(linefeed));
		}
		return builder.toString();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("_package", getPackage());
		JSONArray importArray = new JSONArray();
		for (int i = 0; i < mImportStms.size(); i++) {
			importArray.add(mImportStms.get(i).getImportRefExpression().getVariable());
		}
		if (!importArray.isEmpty()) {
			jsonObject.put("_import", importArray);
		}
		JSONArray typeDefineArray = new JSONArray();
		for (int i = 0; i < mTypeDefineCodeBlocks.size(); i++) {
			TypeDefineCodeBlock typeDefineCodeBlock = mTypeDefineCodeBlocks.get(i);
			typeDefineArray.add(typeDefineCodeBlock.toJSONObject());
		}
		if (!typeDefineArray.isEmpty()) {
			jsonObject.put("_class", typeDefineArray);
		}
		return jsonObject;
	}

	public void attachEnvironmentArgs(EnvironmentArgs args) {
		mEnvironmentArgs = args;
		if (mPackageStm != null) {
			mPackageStm.attachEnvironmentArgs(args);
		}
		for (int i = 0; i < mImportStms.size(); i++) {
			BaseStatement stm = mImportStms.get(i);
			stm.attachEnvironmentArgs(args);
		}
		for (int i = 0; i < mTypeDefineCodeBlocks.size(); i++) {
			BaseStatement stm = mTypeDefineCodeBlocks.get(i);
			stm.attachEnvironmentArgs(args);
		}
		onAttachEnvironmentArgs(args);
	}

	/**
	 * Override the method when the statement has some sub statement.
	 * 
	 * @param args the java file environment arguments
	 */
	public void onAttachEnvironmentArgs(EnvironmentArgs args) {
	}
	
	public boolean isJavaFile() {
		return mEnvironmentArgs == null ? false : mEnvironmentArgs.isJavaFile();
	}
	
	public String getLinefeed(String linefeed) {
		return isJavaFile() ? "" : linefeed;
	}
	
	public String getWordBlank(String blank) {
		return isJavaFile() ? "" : blank;
	}
	
	public EnvironmentArgs getEnvironmentArgs() {
		return mEnvironmentArgs;
	}
	
	public void addImports(List<String> imports) {
		if (imports != null) {
			for (String Import : imports) {
				addImport(Import);
			}
		}
	}
	
	public void addImport(String Import) {
		if (!this.mImport.contains(Import)) {
			addImport(new ImportStatement(new ReferenceExpression(Import)));
		}
	}

	public void addImport(ImportStatement importStm) {
		String _import = null;
		if (importStm != null && !mImportStms.contains(importStm) && !mImport.contains((_import = importStm.getImport()))) {
			mImportStms.add(importStm);
			mImport.add(_import);
			importStm.attachEnvironmentArgs(getEnvironmentArgs());
		}
	}
//	public void removeImport(String Import) {
//		this.Import.remove(Import);
//		// TODO
//	}

	public void copyImports(JavaFileObject fromFileObject) {
		if (fromFileObject == null) {
			return;
		}
		if (fromFileObject.mImportStms != null) {
			for (ImportStatement statement : fromFileObject.mImportStms) {
				addImport(statement);
			}
		}
	}

	public void setPackage(String pkg) {
		ReferenceExpression expr = new ReferenceExpression(pkg);
		mPackageStm = new PackageStatement(expr);
		mPackage = pkg;
	}
	
	public String getPackage() {
		return Util.isNullOrNil(mPackage) ? (mPackageStm != null ? mPackageStm.getPackage() : "") : mPackage;
	}

	public void setPackageStatement(PackageStatement pkgStm) {
		mPackageStm = pkgStm;
		mPackage = pkgStm != null ? pkgStm.getPackage() : "";
		if (mPackageStm != null) {
			mPackageStm.attachEnvironmentArgs(getEnvironmentArgs());
		}
	}

	public PackageStatement getPackageStatement() {
		return mPackageStm;
	}

	/**
	 * Check the name field of the typeDefine, it can not be null or nil.
	 * 
	 * @param typeDefine a new {@link TypeDefineCodeBlock} of the JavaFileObject
	 */
	public void addTypeDefineCodeBlock(TypeDefineCodeBlock typeDefine) {
		if (typeDefine != null && !mTypeDefineCodeBlocks.contains(typeDefine)) {
			typeDefine.attachEnvironmentArgs(getEnvironmentArgs());
			boolean succ = mTypeDefineCodeBlocks.add(typeDefine);
			if (succ && "public".equals(typeDefine.getModifier())) {
				mMainTypeDefCodeBlock = typeDefine;
			}
		}
	}

	public void removeTypeDefineCodeBlock(TypeDefineCodeBlock typeDefine) {
		boolean succ = mTypeDefineCodeBlocks.remove(typeDefine);
		if (succ && mMainTypeDefCodeBlock == typeDefine) {
			mMainTypeDefCodeBlock = null;
		}
	}

	public TypeDefineCodeBlock getTypeDefineCodeBlock(int index) {
		if (index >= 0 && index < mTypeDefineCodeBlocks.size()) {
			return mTypeDefineCodeBlocks.get(index);
		}
		return null;
	}
	
	public int getCountOfTypeDefCodeBlock() {
		return mTypeDefineCodeBlocks.size();
	}
	
	private String genHeader(String linefeed) {
		StringBuilder builder = new StringBuilder();
		// Insert package
		if (mPackageStm != null) {
			builder.append(mPackageStm.genCode(linefeed));
			builder.append(getLinefeed(linefeed));
		}
		// Insert import
		if (mImport != null && mImport.size() > 0) {
			builder.append(getLinefeed(linefeed));
			for (ImportStatement importStm : mImportStms) {
				if (importStm == null) {
					continue;
				}
				builder.append(importStm.genCode(linefeed));
				builder.append(getLinefeed(linefeed));
			}
		}
		return builder.toString();
	}

	public String getFileName() {
		TypeDefineCodeBlock typeDef = getTheFileCodeBlock(false);
		if (typeDef != null) {
			return typeDef.getName().getName();
		}
		return null;
	}

	private TypeDefineCodeBlock mMainTypeDefCodeBlock;
	public TypeDefineCodeBlock getTheFileCodeBlock(boolean forceCheck) {
		if (mMainTypeDefCodeBlock == null || forceCheck) {
			if (mTypeDefineCodeBlocks.size() == 1) {
				mMainTypeDefCodeBlock = mTypeDefineCodeBlocks.get(0);
			} else {
				for (TypeDefineCodeBlock typeDef : mTypeDefineCodeBlocks) {
					if ("public".equals(typeDef.getModifier())) {
						mMainTypeDefCodeBlock = typeDef;
						break;
					}
				}
			}
		}
		return mMainTypeDefCodeBlock;
	}
}
