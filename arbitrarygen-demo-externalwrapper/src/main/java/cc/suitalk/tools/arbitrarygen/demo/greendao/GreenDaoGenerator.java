/*
 *  Copyright (C) 2016-present Albie Liang. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package cc.suitalk.tools.arbitrarygen.demo.greendao;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

/**
 * 
 * @author AlbieLiang
 *
 */
public class GreenDaoGenerator {

    public static void doGen(String inputDir, String targetDir) {
        XMLSerializer ss = new XMLSerializer();
        ss.setTypeHintsEnabled(false);
        ss.setTypeHintsCompatibility(false);

        JSONObject jsonObj = (JSONObject) ss.read(FileOperation.read(inputDir));

        System.out.println("jsonObj : " + jsonObj.toString());

        String pkg = jsonObj.optString("@package", "");
        int version = Integer.parseInt(jsonObj.optString("@version", "1"));
        String entitySuperClass = jsonObj.optString("@super", "");
        
        System.out.println("pkg : " + pkg);
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
