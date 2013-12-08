package com.mobile.UFriend;

import android.os.Bundle;
import android.view.View;
import com.mobile.system.lib.JinAsync;
import com.mobile.system.lib.JinProgress;
import com.mobile.system.resource.UFriendUtils;

/**
 * Created with IntelliJ IDEA.
 * User: jin_note
 * Date: 13. 12. 8
 * Time: 오후 6:19
 * To change this template use File | Settings | File Templates.
 */
public class ProfileActivity extends CommonUFriendActivity {


    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.profile_view);

        setActionBarTitle("프로필변경");

        commonAQuery.id(R.id.custom_actionbar_left_button).getView().setVisibility(View.VISIBLE);


        commonAQuery.id(R.id.profile_user_email_edit_text).getEditText().setText(UFriendUtils.getUserData(commonAQuery).getString("user_email", ""));
        commonAQuery.id(R.id.profile_user_name_edit_text).getEditText().setText(UFriendUtils.getUserData(commonAQuery).getString("name", ""));

    }

}
