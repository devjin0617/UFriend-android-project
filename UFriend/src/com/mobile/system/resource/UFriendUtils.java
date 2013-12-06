package com.mobile.system.resource;

import android.content.SharedPreferences;
import com.androidquery.AQuery;

/**
 * Created with IntelliJ IDEA.
 * User: jin_note
 * Date: 13. 12. 6
 * Time: 오후 8:09
 * To change this template use File | Settings | File Templates.
 */
public class UFriendUtils {

    public static SharedPreferences getUserData(AQuery aQuery)
    {
        SharedPreferences sharedPreferences = aQuery.getContext().getSharedPreferences("userData", aQuery.getContext().MODE_PRIVATE);

        return sharedPreferences;
    }

    public static SharedPreferences.Editor getEditor(AQuery aQuery)
    {
        SharedPreferences sharedPreferences = aQuery.getContext().getSharedPreferences("userData", aQuery.getContext().MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        return editor;
    }
}
