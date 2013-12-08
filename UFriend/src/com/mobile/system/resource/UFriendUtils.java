package com.mobile.system.resource;

import android.content.SharedPreferences;
import com.androidquery.AQuery;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: jin_note
 * Date: 13. 12. 6
 * Time: 오후 8:09
 * To change this template use File | Settings | File Templates.
 */
public class UFriendUtils {

    public static SharedPreferences getUserData(AQuery aQuery) {
        SharedPreferences sharedPreferences = aQuery.getContext().getSharedPreferences("userData", aQuery.getContext().MODE_PRIVATE);

        return sharedPreferences;
    }

    public static SharedPreferences.Editor getEditor(AQuery aQuery) {
        SharedPreferences sharedPreferences = aQuery.getContext().getSharedPreferences("userData", aQuery.getContext().MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        return editor;
    }

    public static String md5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
