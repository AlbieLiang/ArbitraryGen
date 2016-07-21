package osc.innovator.tools.arbitrarygen.demo.greendao;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import osc.innovator.arbitrarygen.core.ContextInfo;
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

    public static void doGen(ContextInfo contextInfo, RawTemplate template) {

        Map<String, String> attributes = contextInfo.getAttributes();
        String pkg = attributes.get("package");
        int version = Integer.parseInt(attributes.get("version"));
        String entitySuperClass = attributes.get("super");
        String targetDir = attributes.get("targetDir");

        Log.d(TAG, "pkg : %s, targetDir : %s", pkg, targetDir);

        Schema schema = new Schema(version, pkg);
//
//        List<RawTemplate> elements = template.getElements();
//        for (RawTemplate rt : elements) {
//            if ("table".equalsIgnoreCase(rt.getName())) {
//                rt.getAttributes().put("package", pkg);
//                genTableEntity(schema, rt, entitySuperClass);
//            }
//        }
        template.getAttributes().put("package", pkg);
        genTableEntity(schema, template, entitySuperClass);

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
    }

    private static void genTableEntity(Schema schema, RawTemplate template, String superClass) {
        Map<String, String> attributes = template.getAttributes();
        String table = attributes.get("name");
        Entity entity = schema.addEntity(table);
        entity.setSuperclass(superClass);

        List<RawTemplate> elements = template.getElements();
        for (RawTemplate rt : elements) {
            if ("field".equalsIgnoreCase(rt.getName())) {
                genField(entity, rt);
            }
        }
    }

    private static void genField(Entity entity, RawTemplate template) {
        Map<String, String> attributes = template.getAttributes();
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

    /////////////////////////////////////////////////////////////////////////////

    public static void doGen(String inputDir, String targetDir) {
        XMLSerializer ss = new XMLSerializer();
        ss.setTypeHintsEnabled(false);
        ss.setTypeHintsCompatibility(false);

        JSONObject jsonObj = (JSONObject) ss.read(FileOperation.read(inputDir));

        Log.d(TAG, "jsonObj : " + jsonObj.toString());

        String pkg = jsonObj.optString("@package", "");
        int version = Integer.parseInt(jsonObj.optString("@version", "1"));
        String entitySuperClass = jsonObj.optString("@super", "");

        Log.d(TAG, "pkg : " + pkg);
        Object tbObj = jsonObj.get("table");

        Schema schema = new Schema(version, pkg);

        if (tbObj instanceof JSONArray) {
            JSONArray arr = (JSONArray) tbObj;
            for (int i = 0, len = arr.size(); i < len; i++) {
                JSONObject obj = arr.getJSONObject(i);
                obj.put("@package", pkg);
                genTableEntity(schema, obj, entitySuperClass);
            }
        } else {
            JSONObject json = (JSONObject) tbObj;
            json.put("@package", pkg);
            genTableEntity(schema, json, entitySuperClass);
        }
        DaoGenerator daogen;
        try {
            daogen = new DaoGenerator();
            daogen.generateAll(schema, targetDir);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void genTableEntity(Schema schema, JSONObject jsonObj, String superClass) {
        String table = jsonObj.optString("@name");
        Entity entity = schema.addEntity(table);
        entity.setSuperclass(superClass);

        Object fieldObj = jsonObj.get("field");
        if (fieldObj instanceof JSONArray) {
            JSONArray arr = (JSONArray) fieldObj;
            for (int i = 0, len = arr.size(); i < len; i++) {
                JSONObject obj = arr.getJSONObject(i);
                genField(entity, obj);
            }
        } else {
            JSONObject json = (JSONObject) fieldObj;
            genField(entity, json);
        }
    }

    private static void genField(Entity entity, JSONObject jsonObj) {
        String name = jsonObj.optString("@name");
        String type = jsonObj.optString("@type");
        String def = jsonObj.optString("@default");
        String comment = jsonObj.optString("@comment");
        String index = jsonObj.optString("@index");
        String primaryKey = jsonObj.optString("@primaryKey");
        String notNull = jsonObj.optString("@notNull");
        String autoincrement = jsonObj.optString("@autoInc");

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
