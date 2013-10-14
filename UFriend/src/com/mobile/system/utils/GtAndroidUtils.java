package com.mobile.system.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: 11. 9. 27 Time: 오후 5:20
 * To change this template use File | Settings | File Templates.
 */
public class GtAndroidUtils {

    public static int getPixelFromDp(View view, float dp) {
        DisplayMetrics metrics = view.getContext().getResources().getDisplayMetrics();

        float fpixels = metrics.density * dp;
        int pixels = (int) (metrics.density * dp + 0.5f);

        return pixels;
    }

    public static int applyNewLineCharacter(TextView textView) {
        Paint paint = textView.getPaint();
        String text = (String) textView.getText();
        int frameWidth = getPixelFromDp(textView, 120f);
        int startIndex = 0;
        int endIndex = paint.breakText(text, true, frameWidth, null);
        String save = text.substring(startIndex, endIndex);
        // Count line of TextView
        int lines = 1;

        while (true) {
            // Set new start index
            startIndex = endIndex;
            // Get substring the remaining of text
            text = text.substring(startIndex);

            if (text.length() == 0) {
                break;
            } else {
                lines++;
            }

            // Calculate end of index that fits
            endIndex = paint.breakText(text, true, frameWidth, null);
            // Append substring that fits into the frame
            save += "\n" + text.substring(0, endIndex);
        }
        // Set text to TextView
        textView.setText(save);

        return lines;
    }

    public static void mapToAndroidLayoutMapper(Class classObj, Map map,
            String prefixStr, View view) {

        for (Object keyObj : map.keySet().toArray()) {
            String keyStr = keyObj.toString();
            Field fieldObj = null;
            try {
                fieldObj = classObj.getField(prefixStr + "_" + keyStr);
            } catch (NoSuchFieldException e) {
                continue;
            }
            Object layoutObj = null;
            try {
                layoutObj = fieldObj.get(fieldObj);
            } catch (IllegalAccessException e) {
                continue;
            }
            Integer layoutIntvalue = (Integer) layoutObj;

            View selectedView = view.findViewById(layoutIntvalue);

            if (selectedView instanceof TextView) {
                TextView textView = (TextView) selectedView;
                textView.setText(String.valueOf(map.get(keyStr)));
            } else if (selectedView instanceof ImageView) {
                ImageView imageView = (ImageView) selectedView;

            }

        }

    }

    public static void mapToAndroidLayoutMapper(Class classObj, Map map,
            String prefixStr, Activity view) {

        if (map == null) {
            return;
        }

        for (Object keyObj : map.keySet().toArray()) {
            String keyStr = keyObj.toString();
            Field fieldObj = null;
            try {
                fieldObj = classObj.getField(prefixStr + "_" + keyStr);
            } catch (NoSuchFieldException e) {
                continue;
            }
            Object layoutObj = null;
            try {
                layoutObj = fieldObj.get(fieldObj);
            } catch (IllegalAccessException e) {
                continue;
            }
            Integer layoutIntvalue = (Integer) layoutObj;

            View selectedView = view.findViewById(layoutIntvalue);

            if (selectedView instanceof TextView) {
                TextView textView = (TextView) selectedView;
                textView.setText((String) map.get(keyStr));
            } else if (selectedView instanceof ImageView) {
                ImageView imageView = (ImageView) selectedView;

            }

        }

    }

    public static void jsonToAndroidLayoutMapper(Class<?> classObj,
            JSONObject json, String prefixStr, Activity view)
            throws JSONException {

        for (int i = 0; i < json.names().length(); i++) {
            String keyStr = (String) json.names().get(i);
            Field fieldObj = null;
            try {
                fieldObj = classObj.getField(prefixStr + "_"
                        + keyStr.toLowerCase());
            } catch (NoSuchFieldException e) {
                continue;
            }
            Object layoutObj = null;
            try {
                layoutObj = fieldObj.get(fieldObj);
            } catch (IllegalAccessException e) {
                continue;
            }
            Integer layoutIntvalue = (Integer) layoutObj;

            View selectedView = view.findViewById(layoutIntvalue);

            if (selectedView instanceof TextView) {
                TextView textView = (TextView) selectedView;
                textView.setText((String) json.get(keyStr));
            } else if (selectedView instanceof ImageView) {
                ImageView imageView = (ImageView) selectedView;

            }
        }

    }

    public static void intentToAndroidLayoutMapper(Class<?> classObj,
            Intent intent, String prefixStr, Activity view) {

        Bundle map = intent.getExtras();

        for (Object keyObj : map.keySet().toArray()) {
            String keyStr = keyObj.toString();
            Field fieldObj = null;
            try {
                fieldObj = classObj.getField(prefixStr + "_" + keyStr);
            } catch (NoSuchFieldException e) {
                continue;
            }
            Object layoutObj = null;
            try {
                layoutObj = fieldObj.get(fieldObj);
            } catch (IllegalAccessException e) {
                continue;
            }
            Integer layoutIntvalue = (Integer) layoutObj;

            View selectedView = view.findViewById(layoutIntvalue);

            if (selectedView instanceof TextView) {
                TextView textView = (TextView) selectedView;
                textView.setText((String) map.get(keyStr));
            } else if (selectedView instanceof ImageView) {
                ImageView imageView = (ImageView) selectedView;

            }

        }

    }

    public static void jsonToSqlite(JSONArray array, String sqlId)
            throws JSONException {
        if( array == null )
        {
            return;
        }
        
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            Map<String, Object> map = new HashMap<String, Object>();

            for (int j = 0; j < jsonObject.names().length(); j++) {
                String strKey = (String) jsonObject.names().get(j);
                map.put(strKey, jsonObject.get(strKey));
            }

            com.mobile.system.resource.UFriendSingleton.getInstance().getDbResource().getAbatisService().execute(sqlId, map);

        }

    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static int getColor(int color) {

        int r = color >> 16;
        color -= (r & 0xFF) << 16;
        int g = color >> 8;
        color -= (g & 0xFF) << 8;
        int b = color;

        return Color.rgb(r, g, b);

    }

    public static Intent makeIntent(Context context, Class<?> classInstance,
            Map<String, Object> param) {
        Intent intent = new Intent(context, classInstance);
        for (Object obj : param.keySet().toArray()) {
            String key = (String) obj;
            intent.putExtra(key, (String) param.get(key));
        }

        return intent;

    }

    public static Intent makeIntent(Context context, Class<?> classInstance,
            Bundle param) {
        Intent intent = new Intent(context, classInstance);
        intent.putExtras(param);

        return intent;

    }
}
