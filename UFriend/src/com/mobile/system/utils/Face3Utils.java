/*
 *
 * <P>
 * Copyright 2008 짤 General Motors   All Rights Reserved.
 * </P>
 *
 * @author <Gitae Kim>
 * @version 2.0
 * @dateCreated 2010. 9. 27
 *
 * Change Log
 * ===================================================================
 * Date             By         Version    /methods        Description
 * ----------   -----------    --------   ------------   ------------
 * 2010. 9. 27      < Gitae Kim >       1.0                    <Create Face3Utils>
 * ===================================================================
 */
package com.mobile.system.utils;

import com.mobile.system.resource.UFriendVariable;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Gitae Kim
 */
public class Face3Utils {

    static public JSONArray getListFromJson(String strJson, String rootStr)
            throws JSONException {
        // JSON json = JSONSerializer.toJSON(strJson);

        JSONArray jsonArray = null;
        try {
            JSONObject jsonObj = new JSONObject(strJson);
            jsonArray = (JSONArray) jsonObj.get(rootStr);
        } catch (Exception e) {
        }

        return jsonArray;

    }

    static public JSONObject getObjectFromJson(String strJson)
            throws JSONException {
        // JSON json = JSONSerializer.toJSON(strJson);
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(strJson);
        } catch (Exception e) {
        }

        return jsonObj;

    }

    static public JSONObject getUrlToJsonObject(String url,
                                                Map<String, Object> param) throws ClientProtocolException,
            IOException, JSONException {

        String line = Face3Utils.getUrlToString(url, param,
                UFriendVariable.SOCK_TIMEOUT);

        return Face3Utils.getObjectFromJson(line);

    }

    static public Map<String, Object> getUrlLongToJsonObject(String url,
                                                             Map<String, Object> param) throws ClientProtocolException,
            IOException, JSONException {

        String line = Face3Utils.getUrlToString(url, param,
                UFriendVariable.LONG_SOCK_TIMEOUT);

        JSONObject object = Face3Utils.getObjectFromJson(line);

        Map<String, Object> resultData = DwAndroidUtils.jsonObjectToMap(object);

        return resultData;

    }

    static public List<Map<String, Object>> getUrlLongFromList(String url,
                                                               Map<String, Object> param) throws ClientProtocolException,
            IOException, JSONException {

        String line = Face3Utils.getUrlToString(url, param,
                UFriendVariable.LONG_SOCK_TIMEOUT);

        JSONArray listObject = Face3Utils.getListFromJson(line, "list");

        List<Map<String, Object>> resultData = DwAndroidUtils.jsonArrayToList(listObject);

        return resultData;

    }

    static public JSONArray getUrlFromList(String url, Map<String, Object> param)
            throws ClientProtocolException, IOException, JSONException {

        String line = Face3Utils.getUrlToString(url, param,
                UFriendVariable.SOCK_TIMEOUT);

        return Face3Utils.getListFromJson(line, "list");

    }

    static public String getUrlToString(String url, Map<String, Object> param) {
        return getUrlToString(url, param, UFriendVariable.SOCK_TIMEOUT);
    }

    static public String getUrlToString(String url, Map<String, Object> param,
                                        int timeout) {

        String line = "";

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpParams params = httpClient.getParams();

        HttpConnectionParams.setConnectionTimeout(params, timeout);
        HttpConnectionParams.setSoTimeout(params, timeout);
        HttpPost httpPost = new HttpPost(url);

        if (param == null) {
            param = new HashMap<String, Object>();
        }

        List<NameValuePair> nameValuePairs;
        nameValuePairs = new ArrayList<NameValuePair>(2);

        for (Object obj : param.keySet().toArray()) {
            String key = obj.toString();

            String paramValue = String.valueOf(param.get(key));
            nameValuePairs.add(new BasicNameValuePair(key, paramValue));
        }

        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Face3Utils.class.getName()).log(Level.SEVERE,
                    null, ex);
            return null;
        }

        httpPost.setEntity(entity);

        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException ex) {
            Logger.getLogger(Face3Utils.class.getName()).log(Level.SEVERE,
                    null, ex);
            return null;
        }

        try {
            HttpEntity httpEntity = httpResponse.getEntity();
            line = EntityUtils.toString(httpEntity);
        } catch (IOException ex) {
            Logger.getLogger(Face3Utils.class.getName()).log(Level.SEVERE,
                    null, ex);
            // Toast.makeText(UFriendSingleton.getInstance().getCurrentActivity(),
            // "통신 접속 Error", Toast.LENGTH_SHORT).show();
            return null;

        } catch (ParseException ex) {
            Logger.getLogger(Face3Utils.class.getName()).log(Level.SEVERE,
                    null, ex);
            return null;
        }

        return line;

    }

    static public byte[] getUrlToByte(String url, Map<String, String> param)
            throws ClientProtocolException, IOException {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params,
                UFriendVariable.SOCK_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, UFriendVariable.SOCK_TIMEOUT);

        HttpPost httpPost = new HttpPost(url);

        if (param == null) {
            param = new HashMap<String, String>();
        }

        if (param != null) {
            List<NameValuePair> nameValuePairs;
            nameValuePairs = new ArrayList<NameValuePair>(2);
            for (Object obj : param.keySet().toArray()) {
                String key = obj.toString();

                String paramValue = (String) param.get(key);
                nameValuePairs.add(new BasicNameValuePair(key, paramValue));
            }

            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Face3Utils.class.getName()).log(Level.SEVERE,
                        null, ex);
            }

            httpPost.setEntity(entity);

        }

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();

        byte[] bytes = EntityUtils.toByteArray(httpEntity);

        return bytes;

    }

    static public int doCommand(String url, Map<String, Object> param)
            throws ClientProtocolException, IOException, JSONException {

        HttpPost httppost;
        HttpClient httpclient;

        // List with arameters and their values
        List<NameValuePair> nameValuePairs;

        int serverStatusCode = 0;

        httppost = new HttpPost(url);
        httpclient = new DefaultHttpClient();
        HttpParams params = httpclient.getParams();
        HttpConnectionParams.setConnectionTimeout(params,
                UFriendVariable.SOCK_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, UFriendVariable.SOCK_TIMEOUT);
        nameValuePairs = new ArrayList<NameValuePair>(2);

        if (param == null) {
            param = new HashMap<String, Object>();
        }

        for (Object obj : param.keySet().toArray()) {
            String key = obj.toString();

            String paramValue = String.valueOf(param.get(key));

            // Adding parameters to send to the HTTP server.

            nameValuePairs.add(new BasicNameValuePair(key, paramValue));
        }

        // Send POST message with given parameters to the HTTP server.
        try {
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Face3Utils.class.getName()).log(Level.SEVERE,
                        null, ex);
            }

            httppost.setEntity(entity);

            HttpResponse response = httpclient.execute(httppost);

            InputStream is = response.getEntity().getContent();
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayBuffer baf = new ByteArrayBuffer(20);

            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

            serverStatusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            // Exception handling
        }

        return serverStatusCode;

    }
}
