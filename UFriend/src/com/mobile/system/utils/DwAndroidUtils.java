package com.mobile.system.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author DaeWook Choi 2012.03.21
 */

public class DwAndroidUtils {

    static public Map<String, Object> map;

    static public List<Map<String, Object>> jsonArrayToList(JSONArray jsonArray)
            throws JSONException {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Map<String, Object> map = new HashMap<String, Object>();

            for (int j = 0; j < jsonObject.names().length(); j++) {
                String strKey = (String) jsonObject.names().get(j);
                map.put(strKey, jsonObject.get(strKey));
            }

            list.add(map);
        }
        return list;
    }

    static public Map<String, Object> jsonObjectToMap(JSONObject jsonObject)
            throws JSONException {

        Map<String, Object> resultdata = new HashMap<String, Object>();

        for (int i = 0; i < jsonObject.length(); ++i) {
            String strKey = (String) jsonObject.names().get(
                    i);
            String strValue = (String) jsonObject
                    .get(strKey);
            resultdata.put(strKey, strValue);
        }

        return resultdata;

    }


}
