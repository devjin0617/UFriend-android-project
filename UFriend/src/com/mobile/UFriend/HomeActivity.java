package com.mobile.UFriend;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/**
 * Created with IntelliJ IDEA.
 * User: jin_note
 * Date: 13. 10. 17
 * Time: 오전 3:19
 * To change this template use File | Settings | File Templates.
 */
public class HomeActivity extends CommonUFriendActivity {

    public static int backCount = 0;

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.

        backCount = 0;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);






    }

    @Override
    public void onBackPressed() {

        if (backCount++ == 0) {
            Toast.makeText(commonAQuery.getContext(), "Once Again BackButton - Quit", 2).show();
        } else {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }


    }
}