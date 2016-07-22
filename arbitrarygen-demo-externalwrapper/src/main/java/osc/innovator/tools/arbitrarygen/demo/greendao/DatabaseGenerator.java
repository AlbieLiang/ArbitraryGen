package osc.innovator.tools.arbitrarygen.demo.greendao;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import osc.innovator.arbitrarygen.block.DefaultMethodCodeBlock;
import osc.innovator.arbitrarygen.block.MethodCodeBlock;
import osc.innovator.arbitrarygen.block.TypeDefineCodeBlock;
import osc.innovator.arbitrarygen.core.ContextInfo;
import osc.innovator.arbitrarygen.core.KeyWords;
import osc.innovator.arbitrarygen.statement.NormalStatement;
import osc.innovator.arbitrarygen.template.RawTemplate;
import osc.innovator.arbitrarygen.utils.Log;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class DatabaseGenerator {

    private static final String TAG = "GreenDao.DatabaseGenerator";

    public static TypeDefineCodeBlock doGen(ContextInfo contextInfo, RawTemplate template) {

        Map<String, String> contextAttrs = contextInfo.getAttributes();
        String pkg = contextAttrs.get("package");
        int version = Integer.parseInt(contextAttrs.get("version"));
        String entitySuperClass = contextAttrs.get("super");
        String targetDir = contextAttrs.get("targetDir");

        Log.d(TAG, "pkg : %s, targetDir : %s", pkg, targetDir);

        Schema schema = new Schema(version, pkg);

        template.getAttributes().put("package", pkg);

        Map<String, String> attributes = template.getAttributes();
        String table = attributes.get("name");
        Entity entity = schema.addEntity(table);
        entity.setSuperclass("Base" + table);

        TypeDefineCodeBlock tdcb = new TypeDefineCodeBlock();
        tdcb.setModifier(KeyWords.V_JAVA_KEYWORDS_PUBLIC);
        tdcb.setType(Util.createSimpleTypeName(KeyWords.V_JAVA_KEYWORDS_CLASS));
        tdcb.setName(Util.createSimpleTypeName("Base" + table));
        tdcb.setParent(Util.createSimpleTypeName(entitySuperClass));


        MethodCodeBlock getNameMethod = new DefaultMethodCodeBlock("getTableName", "String");
        getNameMethod.setIsStatic(true);
        getNameMethod.addStatement(new NormalStatement(String.format("return \"%s\"", table)));

        tdcb.addMethod(getNameMethod);

        MethodCodeBlock methodCodeBlock = new DefaultMethodCodeBlock("getColumnInfoMap", "java.util.Map<String, String>");
        methodCodeBlock.setIsStatic(true);

        tdcb.addMethod(methodCodeBlock);

        methodCodeBlock.addStatement(new NormalStatement("java.util.Map<String, String> map = new java.util.HashMap<>()"));

        for (RawTemplate rt : template.getElements()) {
            if ("field".equalsIgnoreCase(rt.getName())) {
                Map<String, String> map = rt.getAttributes();
                genField(entity, map);
                methodCodeBlock.addStatement(new NormalStatement(
                        String.format("map.put(\"%s\", \"%s\")", map.get("name"), map.get("type"))));
            }
        }
        methodCodeBlock.addStatement(new NormalStatement("return map"));

        Util.mkdirsIfNeed(new File(targetDir));

        DaoGenerator daogen;
		try {
			daogen = new DaoGenerator();
			daogen.generateAll(schema, targetDir);
		} catch (IOException e) {
            Log.e(TAG, "do gen green dao exception : %s", e);
        } catch (Exception e) {
            Log.e(TAG, "do gen green dao exception : %s", e);
        }
        return tdcb;
    }

    private static void genField(Entity entity, Map<String, String> attributes) {
        String name = attributes.get("name");
        String type = attributes.get("type");
        String def = attributes.get("default");
        String comment = attributes.get("comment");
        String index = attributes.get("index");
        String primaryKey = attributes.get("primaryKey");
        String notNull = attributes.get("notNull");
        String autoincrement = attributes.get("autoInc");

        Property.PropertyBuilder builder = null;
        if (type.equals("String")) {
            builder = entity.addStringProperty(name);
        } else if (type.equals("int")) {
            builder = entity.addIntProperty(name);
        } else if (type.equals("long")) {
            builder = entity.addLongProperty(name);
        } else if (type.equals("boolean")) {
            builder = entity.addBooleanProperty(name);
        } else if (type.equals("byte[]")) {
            builder = entity.addByteArrayProperty(name);
        } else if (type.equals("byte")) {
            builder = entity.addByteProperty(name);
        } else if (type.equals("float")) {
            builder = entity.addFloatProperty(name);
        } else if (type.equals("double")) {
            builder = entity.addDoubleProperty(name);
        }
        if ("true".equals(notNull)) {
            builder.notNull();
        }
        if ("true".equals(primaryKey)) {
            builder.primaryKey();
        }
        if ("true".equals(autoincrement)) {
            builder.autoincrement();
        }
    }
}
