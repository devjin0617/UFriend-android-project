package com.mobile.UFriend;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: jin_note
 * Date: 13. 10. 17
 * Time: 오전 2:20
 * To change this template use File | Settings | File Templates.
 */
public class JoinActivity extends CommonUFriendActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_view);

        commonAQuery.id(R.id.custom_actionbar_left_button).getView().setVisibility(View.VISIBLE);

        setActionBarTitle("Join");
    }
}