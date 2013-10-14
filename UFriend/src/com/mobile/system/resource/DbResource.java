package com.mobile.system.resource;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.mobile.system.db.abatis.AbatisService;
import com.mobile.system.utils.Face3Utils;
import com.mobile.system.utils.GtAndroidUtils;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: 11. 9. 19 To change this
 * template use File | Settings | File Templates.
 */
public class DbResource {

    private AbatisService abatisService;
    private SQLiteDatabase db;

    public DbResource() {
    }

    public void updateBlobData(String tableName, String columnName, String where, String whereArgs, byte[] data) {
        /*
         * ContentValues cv = new ContentValues(2); cv.put(columnName, data);
         * db.update(tableName, data, where, whereArgs); db.insert(tableName,
         * null, cv);
         */
    }

    public void initDbResource(android.content.Context context) throws ClientProtocolException, IOException, JSONException {

        abatisService = AbatisService.getInstance(context, "smartcampus");
        db = abatisService.getWritableDatabase();

    }

    public void initDbTable() throws ClientProtocolException, IOException, JSONException {

        JSONObject object = Face3Utils.getUrlToJsonObject(UFriendVariable.getServerHttpUrl("/commondata/getVersion.do"), null);
        String strVersion = (String) object.get("version");

        Map<String, Object> map = abatisService.executeForMap("init.select_version", null);
        String strLocVersion = (String) map.get("version");

        if (Double.parseDouble(strVersion) > Double.parseDouble(strLocVersion)) {
            JSONArray array = Face3Utils.getUrlFromList(UFriendVariable.getServerHttpUrl("/commondata/getTableList.do"), null);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                map.put("table_name", obj.get("table_name"));
                map.put("table_ddl", obj.get("ddl"));
                abatisService.execute("init.drop_table", map);
                abatisService.execute("init.init_table", map);
            }

        }
    }

    public void synchronizeDbFromRemoteDb(String tableNm) throws ClientProtocolException, IOException, JSONException {

        try {
            HashMap<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("student_no", UFriendSingleton.getInstance().getUserInfo().getStudent_no());
            paramMap.put("user_id", UFriendSingleton.getInstance().getUserInfo().getUser_id());
            paramMap.put("TableName", tableNm);
            JSONArray array = Face3Utils.getUrlFromList(UFriendVariable.getServerHttpUrl("/commondata/getQueryListTableByTableName.do?"), paramMap);

            UFriendSingleton.getInstance().getDbResource().getAbatisService().execute("delete_" + tableNm);
            GtAndroidUtils.jsonToSqlite(array, "insert_" + tableNm);

            if ("timetable".equals(tableNm)) {
            } else if ("student".equals(tableNm)) {
            } else if ("class_chat".equals(tableNm)) {
            } else if ("club_student".equals(tableNm)) {
            } else if ("club".equals(tableNm)) {
            } else if ("class_student".equals(tableNm)) {
            } else if ("score".equals(tableNm)) {
            } else if ("board".equals(tableNm)){
            }
        } catch (Exception e) {
        }


    }

    public AbatisService getAbatisService() {
        return abatisService;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
