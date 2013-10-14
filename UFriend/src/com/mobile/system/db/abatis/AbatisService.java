/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobile.system.db.abatis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 */
public class AbatisService extends SQLiteOpenHelper {

	/**
     */
	private static final String TAG = "aBatis";
	/**
     *
     */
	private static final String INIT_CREATE_SQL = "initialize";
	/**
	 * Default DB file name
	 */
	private static final String DB_FILE_NAME = "database.db";
	/**
     */
	private static AbatisService instance = null;
	/**
	 * SQLiteDatabase object
	 */
	private SQLiteDatabase dbObj;
	/**
	 * Context object
	 */
	private Context context;

	private AbatisService(Context context) {
		super(context, DB_FILE_NAME, null, 1);
		this.context = context;
	}

	private AbatisService(Context context, String dbName) {
		super(context, dbName.concat(".db"), null, 1);
		this.context = context;
	}

	public static AbatisService getInstance(Context context) {
		if (instance == null) {
			instance = new AbatisService(context);
		}
		return instance;
	}

	public static AbatisService getInstance(Context context, String dbName) {
		if (instance == null) {
			instance = new AbatisService(context, dbName);
		}
		return instance;
	}

	/**
	 * DB connector
	 * 
	 * @param db
	 *            SQLiteDatabase object
	 * 
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		int pointer = context.getResources().getIdentifier(INIT_CREATE_SQL,
				"string", context.getPackageName());
		if (pointer == 0) {
			Log.e(TAG, "undefined sql id - initialize");
		} else {
			String createTabelSql = context.getResources().getString(pointer);
			db.execSQL(createTabelSql);
			Log.d(TAG, "Run sql : " + createTabelSql);
		}
	}

	/**
	 * 
	 * @param db
	 *            SQLiteDatabase object
	 * @param oldVersion
	 *            old version value
	 * @param newVersion
	 *            new version value
	 * 
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// no poc
	}

	/**
	 * 
	 * @param sqlId
	 *            SQLID
	 * @param bindParams
	 *            sql parameter
	 * 
	 * @return Map<String, Object> result
	 */
	public Map<String, Object> executeForMap(String sqlId,
			Map<String, Object> bindParams) {
		getDbObject();
		int pointer = context.getResources().getIdentifier(sqlId, "string",
				context.getPackageName());
		if (pointer == 0) {
			Log.e(TAG, "undefined sql id : " + sqlId);
			return null;
		}
		String sql = context.getResources().getString(pointer);
		if (bindParams != null) {
			Iterator<String> mapIterator = bindParams.keySet().iterator();
			while (mapIterator.hasNext()) {
				String key = mapIterator.next();
				Object value = bindParams.get(key);
				if (value != null && key != null) {
					sql = getFinalSql(sql, key.toLowerCase(), value.toString());
				}

			}
		}
		if (sql.indexOf('#') != -1) {
			Log.e(TAG, "undefined parameter sql : " + sql);
			return null;
		}
		Cursor cursor = dbObj.rawQuery(sql, null);
		Log.d(TAG, "Run sql : " + sql);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		if (cursor == null) {
			return null;
		}
		String[] columnNames = cursor.getColumnNames();
		while (cursor.moveToNext()) {
			Map<String, Object> map = new HashMap<String, Object>();
			int i = 0;
			for (String columnName : columnNames) {
				map.put(columnName, cursor.getString(i));
				i++;
			}
			mapList.add(map);
		}
		if (mapList.size() <= 0) {
			return null;
		}
		cursor.close();
		dbObj.close();
		return mapList.get(0);
	}

	public boolean insert(String tableName, Map<String, Object> bindParams) {

		getDbObject();

		ContentValues initialValues = new ContentValues();

		if (bindParams != null) {
			Iterator<String> mapIterator = bindParams.keySet().iterator();
			while (mapIterator.hasNext()) {
				String key = mapIterator.next();
				Object value = bindParams.get(key);

				if (value instanceof byte[]) {
					initialValues.put(key, (byte[]) value);
				} else {
					initialValues.put(key, value.toString());
				}
			}
		}

		int nInsertCnt = (int) dbObj.insert(tableName, null, initialValues);

		dbObj.close();

		if (nInsertCnt <= 0) {
			return false;
		}

		return true;

	}

	public boolean update(String tableName, String sqlId,
			Map<String, Object> bindParams, String whereClause,
			String[] whereArgs) {

		getDbObject();
		int pointer = context.getResources().getIdentifier(sqlId, "string",
				context.getPackageName());
		if (pointer == 0) {
			Log.e(TAG, "undefined sql id : " + sqlId);
			return false;
		}
		String sql = context.getResources().getString(pointer);
		ContentValues initialValues = new ContentValues();

		if (bindParams != null) {
			Iterator<String> mapIterator = bindParams.keySet().iterator();
			while (mapIterator.hasNext()) {
				String key = mapIterator.next();
				Object value = bindParams.get(key);

				if (value instanceof byte[]) {
					initialValues.put(key, (byte[]) value);
				} else {
					initialValues.put(key, value.toString());
				}
			}
		}
		if (sql.indexOf('#') != -1) {
			Log.e(TAG, "undefined parameter sql : " + sql);
			return false;
		}

		int nInsertCnt = (int) dbObj.update(tableName, initialValues,
				whereClause, whereArgs);

		dbObj.close();

		if (nInsertCnt <= 0) {
			return false;
		}

		return true;

	}

	/**
	 * 
	 * @param sqlId
	 *            SQLID
	 * @param bindParams
	 *            sql parameter
	 * 
	 * @return List<Map<String, Object>> result
	 * @throws ParseException
	 */
	public List<Map<String, Object>> executeForMapList(String sqlId,
			Map<String, Object> bindParams) {
		getDbObject();
		int pointer = context.getResources().getIdentifier(sqlId, "string",
				context.getPackageName());
		if (pointer == 0) {
			Log.e(TAG, "undefined sql id : " + sqlId);
			return null;
		}
		String sql = context.getResources().getString(pointer);
		if (bindParams != null) {
			Iterator<String> mapIterator = bindParams.keySet().iterator();
			while (mapIterator.hasNext()) {
				String key = mapIterator.next();
				Object value = bindParams.get(key);
				if (value != null && key != null) {
					sql = getFinalSql(sql, key.toLowerCase(), value.toString());
				}
			}
		}
		if (sql.indexOf('#') != -1) {
			Log.e(TAG, "undefined parameter sql : " + sql);
			return null;
		}
		Cursor cursor = dbObj.rawQuery(sql, null);
		Log.d(TAG, "Run sql : " + sql);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		if (cursor == null) {
			return null;
		}
		String[] columnNames = cursor.getColumnNames();
		while (cursor.moveToNext()) {
			Map<String, Object> map = new HashMap<String, Object>();
			int i = 0;
			for (String columnName : columnNames) {
				map.put(columnName, cursor.getString(i));
				i++;
			}
			mapList.add(map);
		}
		cursor.close();
		dbObj.close();
		return mapList;
	}

	/**
	 * 
	 * 
	 * @param sqlId
	 *            SQLID
	 * @param bindParams
	 *            sql parameter
	 * @param bean
	 *            bean class of result
	 * 
	 * @return List<Map<String, Object>> result
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T executeForBean(String sqlId, Map<String, Object> bindParams,
			Class bean) throws SecurityException, IllegalAccessException,
			InstantiationException, NoSuchMethodException {
		getDbObject();
		int pointer = context.getResources().getIdentifier(sqlId, "string",
				context.getPackageName());
		if (pointer == 0) {
			Log.e(TAG, "undefined sql id : " + sqlId);
			return null;
		}
		String sql = context.getResources().getString(pointer);
		if (bindParams != null) {
			Iterator<String> mapIterator = bindParams.keySet().iterator();
			while (mapIterator.hasNext()) {
				String key = mapIterator.next();
				Object value = bindParams.get(key);
				sql = getFinalSql(sql, key.toLowerCase(), value.toString());
			}
		}
		if (sql.indexOf('#') != -1) {
			Log.e(TAG, "undefined parameter sql : " + sql);
			return null;
		}
		Cursor cursor = dbObj.rawQuery(sql, null);
		Log.d(TAG, "Run sql : " + sql);
		List<T> objectList = new ArrayList<T>();
		if (cursor == null) {
			return null;
		}

		T beanObj = null;
		// get bean class package
		Package beanPackage = bean.getPackage();
		while (cursor.moveToNext()) {
			int i = 0;

			beanObj = (T) parseToObject(bean, cursor);
			// objectList.add(beanObj) cursor.getString(i));
			i++;

			/*
			 * JSONObject json = new JSONObject(map); try { beanObj =
			 * (T)parse(json.toString(), bean, beanPackage.getName()); } catch
			 * (Exception e) { Log.d(TAG, e.toString()); return null; }
			 */
			objectList.add(beanObj);
		}
		if (objectList.size() <= 0) {
			return null;
		}
		cursor.close();
		dbObj.close();
		return objectList.get(0);
	}

	public Object parseToObject(Class beanClass, Cursor cur)
			throws IllegalAccessException, InstantiationException,
			SecurityException, NoSuchMethodException {
		Object obj = null;
		Field[] props = beanClass.getDeclaredFields();
		if (props == null || props.length == 0) {
			Log.d(TAG, "Class" + beanClass.getName() + " has no fields");
			return null;
		}
		// Create instance of this Bean class
		obj = beanClass.newInstance();
		// Set value of each member variable of this object
		for (int i = 0; i < props.length; i++) {
			String fieldName = props[i].getName();
			if (props[i].getModifiers() == (Modifier.PUBLIC | Modifier.STATIC)) {
				continue;
			}

			Class type = props[i].getType();
			String typeName = type.getName();
			// Check for Custom type

			Class[] parms = { type };
			Method m = beanClass.getDeclaredMethod(
					getBeanMethodName(fieldName, 1), parms);
			m.setAccessible(true);
			// Set value
			try {
				int curIdx = cur.getColumnIndex(fieldName);
				if (curIdx != -1) {
					int nDotIdx = typeName.lastIndexOf(".");
					if (nDotIdx >= 0) {
						typeName = typeName.substring(nDotIdx);
					}

					if (typeName.equals("int")) {
						m.invoke(obj, cur.getInt(curIdx));
					} else if (typeName.equals("double")) {
						m.invoke(obj, cur.getDouble(curIdx));
					} else if (typeName.equals("String")) {
						m.invoke(obj, cur.getString(curIdx));
					} else if (typeName.equals("long")) {
						m.invoke(obj, cur.getLong(curIdx));
					} else if (typeName.equals("float")) {
						m.invoke(obj, cur.getFloat(curIdx));
					} else if (typeName.equals("Date")) {
						m.invoke(obj, cur.getString(curIdx));
					} else if (typeName.equals("byte[]")
							|| typeName.equals("[B")) {
						m.invoke(obj, cur.getBlob(curIdx));
					} else {
						m.invoke(obj, cur.getString(curIdx));
					}
				}

			} catch (Exception ex) {
				Log.d(TAG, ex.getMessage());
			}
		}
		return obj;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> executeForBeanList(String sqlId,
			Map<String, Object> bindParams, Class bean)
			throws SecurityException, IllegalAccessException,
			InstantiationException, NoSuchMethodException {
		getDbObject();
		int pointer = context.getResources().getIdentifier(sqlId, "string",
				context.getPackageName());
		if (pointer == 0) {
			Log.e(TAG, "undefined sql id : " + sqlId);
			return null;
		}
		String sql = context.getResources().getString(pointer);
		if (bindParams != null) {
			Iterator<String> mapIterator = bindParams.keySet().iterator();
			while (mapIterator.hasNext()) {
				String key = mapIterator.next();
				Object value = bindParams.get(key);
				sql = getFinalSql(sql, key.toLowerCase(), value.toString());
			}
		}
		if (sql.indexOf('#') != -1) {
			Log.e(TAG, "undefined parameter sql : " + sql);
			return null;
		}
		Cursor cursor = dbObj.rawQuery(sql, null);
		Log.d(TAG, "Run sql : " + sql);
		List<T> objectList = new ArrayList<T>();
		if (cursor == null) {
			return null;
		}
		String[] columnNames = cursor.getColumnNames();
		List<String> dataNames = new ArrayList<String>();
		for (String columnName : columnNames) {
			dataNames.add(chgDataName(columnName));
		}
		T beanObj = null;
		// get bean class package

		while (cursor.moveToNext()) {
			beanObj = (T) parseToObject(bean, cursor);
			objectList.add(beanObj);
		}
		cursor.close();
		dbObj.close();
		return objectList;
	}

	/**
     *
     */
	public int execute(String sqlId, JSONObject json) {

		Map<String, Object> map = null;
		if (json != null) {
			map = new HashMap<String, Object>();

			for (int j = 0; j < json.names().length(); j++) {
				String strKey = null;
				try {
					strKey = (String) json.names().get(j);
					map.put(strKey, json.get(strKey));
				} catch (JSONException ex) {
					Logger.getLogger(AbatisService.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}

		return execute(sqlId, map);
	}

	public int execute(String sqlId) {
		HashMap<String, Object> map = null;
		return execute(sqlId, map);
	}

	public int execute(String sqlId, Map<String, Object> bindParams) {
		getDbObject();
		int row = 0;
		int pointer = context.getResources().getIdentifier(sqlId, "string",
				context.getPackageName());
		if (pointer == 0) {
			Log.e(TAG, "undefined sql id : " + sqlId);
			return row;
		}
		String sql = context.getResources().getString(pointer);

		if (bindParams != null) {
			Iterator<String> mapIterator = bindParams.keySet().iterator();
			while (mapIterator.hasNext()) {
				String key = mapIterator.next();
				Object value = bindParams.get(key);
				if (value != null) {
					sql = getFinalSql(sql, key.toLowerCase(), value.toString());
				} else {
					sql = getFinalSql(sql, key.toLowerCase(), " ");
				}

			}
		}
		if (sql.indexOf('#') != -1) {
			Log.e(TAG, "undefined parameter sql : " + sql);
			return row;
		}
		try {
			System.out.println(sql);
			dbObj.execSQL(sql);
			Log.d(TAG, "Run sql : " + sql);
			dbObj.close();
			row += 1;
		} catch (SQLException e) {
			System.out.println("Error sql : " + sql);
			return row;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	private String getFinalSql(String query, final String key,
			final String value) {
		query = query.replaceAll("\\$" + key + "\\$", value);
		query = query.replaceAll("#" + key + "#", "'" + value + "'");

		return query;

	}

	/**
	 * 
	 * @return SQLiteDatabase SQLiteDatabase Object
	 */
	private SQLiteDatabase getDbObject() {
		if (dbObj == null || !dbObj.isOpen()) {
			dbObj = getWritableDatabase();
		}
		return dbObj;
	}

	/**
	 * 
	 * @param jsonStr
	 *            JSON String
	 * @param beanClass
	 *            Bean class
	 * @param basePackage
	 *            Base package name which includes all Bean classes
	 * @return Object Bean
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object parse(String jsonStr, Class beanClass, String basePackage)
			throws Exception {
		Object obj = null;
		JSONObject jsonObj = new JSONObject(jsonStr);
		// Check bean object
		if (beanClass == null) {
			Log.d(TAG, "Bean class is null");
			return null;
		}
		// Read Class member fields
		Field[] props = beanClass.getDeclaredFields();
		if (props == null || props.length == 0) {
			Log.d(TAG, "Class" + beanClass.getName() + " has no fields");
			return null;
		}
		// Create instance of this Bean class
		obj = beanClass.newInstance();
		// Set value of each member variable of this object
		for (int i = 0; i < props.length; i++) {
			String fieldName = props[i].getName();
			// Skip public and static fields
			if (props[i].getModifiers() == (Modifier.PUBLIC | Modifier.STATIC)) {
				continue;
			}
			// Date Type of Field
			Class type = props[i].getType();
			String typeName = type.getName();
			// Check for Custom type
			if (typeName.equals("int")) {
				Class[] parms = { type };
				Method m = beanClass.getDeclaredMethod(
						getBeanMethodName(fieldName, 1), parms);
				m.setAccessible(true);
				// Set value
				try {
					m.invoke(obj, jsonObj.getInt(fieldName));
				} catch (Exception ex) {
					Log.d(TAG, ex.getMessage());
				}
			} else if (typeName.equals("long")) {
				Class[] parms = { type };
				Method m = beanClass.getDeclaredMethod(
						getBeanMethodName(fieldName, 1), parms);
				m.setAccessible(true);
				// Set value
				try {
					m.invoke(obj, jsonObj.getLong(fieldName));
				} catch (Exception ex) {
					Log.d(TAG, ex.getMessage());
				}
			} else if (typeName.equals("java.lang.String")) {
				Class[] parms = { type };
				Method m = beanClass.getDeclaredMethod(
						getBeanMethodName(fieldName, 1), parms);
				m.setAccessible(true);
				// Set value
				try {
					m.invoke(obj, jsonObj.getString(fieldName));
				} catch (Exception ex) {
					Log.d(TAG, ex.getMessage());
				}
			} else if (typeName.equals("double")) {
				Class[] parms = { type };
				Method m = beanClass.getDeclaredMethod(
						getBeanMethodName(fieldName, 1), parms);
				m.setAccessible(true);
				// Set value
				try {
					m.invoke(obj, jsonObj.getDouble(fieldName));
				} catch (Exception ex) {
					Log.d(TAG, ex.getMessage());
				}
			} else if (typeName.equals("java.util.Date")) { // modify

				Class[] parms = { type };
				Method m = beanClass.getDeclaredMethod(
						getBeanMethodName(fieldName, 1), parms);
				m.setAccessible(true);

				String dateString = jsonObj.getString(fieldName);
				dateString = dateString.replace(" KST", "");
				SimpleDateFormat genderFormat = new SimpleDateFormat(
						"EEE MMM dd HH:mm:ss yyyy", Locale.KOREA);
				// Set value
				try {
					Date afterDate = genderFormat.parse(dateString);
					m.invoke(obj, afterDate);
				} catch (Exception e) {
					Log.d(TAG, e.getMessage());
				}
			} else if (type.getName().equals(List.class.getName())
					|| type.getName().equals(ArrayList.class.getName())) {
				// Find out the Generic
				String generic = props[i].getGenericType().toString();
				if (generic.indexOf("<") != -1) {
					String genericType = generic.substring(
							generic.lastIndexOf("<") + 1,
							generic.lastIndexOf(">"));
					if (genericType != null) {
						JSONArray array = null;
						try {
							array = jsonObj.getJSONArray(fieldName);
						} catch (Exception ex) {
							Log.d(TAG, ex.getMessage());
							array = null;
						}
						if (array == null) {
							continue;
						}
						ArrayList arrayList = new ArrayList();
						for (int j = 0; j < array.length(); j++) {
							arrayList.add(parse(array.getJSONObject(j)
									.toString(), Class.forName(genericType),
									basePackage));
						}
						// Set value
						Class[] parms = { type };
						Method m = beanClass.getDeclaredMethod(
								getBeanMethodName(fieldName, 1), parms);
						m.setAccessible(true);
						m.invoke(obj, arrayList);
					}
				} else {
					// No generic defined
					generic = null;
				}
			} else if (typeName.startsWith(basePackage)) {
				Class[] parms = { type };
				Method m = beanClass.getDeclaredMethod(
						getBeanMethodName(fieldName, 1), parms);
				m.setAccessible(true);
				// Set value
				try {
					JSONObject customObj = jsonObj.getJSONObject(fieldName);
					if (customObj != null) {
						m.invoke(obj,
								parse(customObj.toString(), type, basePackage));
					}
				} catch (JSONException ex) {
					Log.d(TAG, ex.getMessage());
				}
			} else {
				// Skip
				Log.d(TAG, "Field " + fieldName + "#" + typeName + " is skip");
			}
		}
		return obj;
	}

	/**
	 * BeanClass fields?????ethod???????????
	 * 
	 * @param fieldName
	 * @param type
	 * @return String MethodName
	 */
	private String getBeanMethodName(String fieldName, int type) {
		if (fieldName == null || fieldName == "") {
			return "";
		}
		String methodName = "";
		if (type == 0) {
			methodName = "get";
		} else {
			methodName = "set";
		}
		methodName += fieldName.substring(0, 1).toUpperCase();
		if (fieldName.length() == 1) {
			return methodName;
		}
		methodName += fieldName.substring(1);
		return methodName;
	}

	/**
     *
     */
	private String chgDataName(String targetStr) {
		Pattern p = Pattern.compile("_([a-z])");
		Matcher m = p.matcher(targetStr.toLowerCase());

		StringBuffer sb = new StringBuffer(targetStr.length());
		while (m.find()) {
			m.appendReplacement(sb, m.group(1).toUpperCase());
		}
		m.appendTail(sb);
		return sb.toString();
	}
}
