package com.mobile.UFriend;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.mobile.system.lib.JinAsync;
import com.mobile.system.lib.JinProgress;
import com.mobile.system.resource.UFriendUtils;
import com.mobile.system.resource.UFriendVariable;
import com.mobile.system.utils.Face3Utils;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends CommonUFriendActivity {
    /**
     * Called when the activity is first created.
     */

    public String strUserEmail = "";
    public String strPassword = "";

    public Map loginResultData = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        setActionBarTitle("Login");

        // ActionBar Init
        commonAQuery.id(R.id.custom_actionbar_right_button).getView().setVisibility(View.VISIBLE);

        commonAQuery.id(R.id.login_login_button).clicked(commonAQuery.getContext(), "doLoginButton");
        commonAQuery.id(R.id.custom_actionbar_right_button).clicked(commonAQuery.getContext(), "doJoinButton");


        /*JinProgress.doBackgroundAndLoading(this, new JinAsync() {
            @Override
            public void doASyncData() {
                //To change body of implemented methods use File | Settings | File Templates.

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

            @Override
            public void doFinish() {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        }, "hihi");      */
    }

    public void doLoginButton(View v) {

        strUserEmail = commonAQuery.id(R.id.login_user_email_edit_text).getEditText().getText().toString();
        strPassword = commonAQuery.id(R.id.login_password_edit_text).getEditText().getText().toString();


        new JinProgress(commonAQuery.getContext(), new JinAsync() {

            @Override
            public void doASyncData() {
                //To change body of implemented methods use File | Settings | File Templates.

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("user_email", strUserEmail);
                params.put("password", strPassword);


                try {
                    loginResultData = Face3Utils.getUrlLongToJsonObject(UFriendVariable.getServerHttpUrl("/commondata/login.do"), params);

                    if (loginResultData != null) {
                        if (loginResultData.get("success").toString().equals("true")) {

                            Map<String, Object> userProfileData = Face3Utils.getUrlLongToJsonObject(UFriendVariable.getServerHttpUrl("/commondata/getUserProfile.do"), params);


                            SharedPreferences.Editor editor = UFriendUtils.getEditor(commonAQuery);

                            editor.putString("user_id", userProfileData.get("user_id").toString());
                            editor.putString("user_email", userProfileData.get("user_email").toString());
                            editor.putString("password", strPassword);
                            editor.putString("name", userProfileData.get("name").toString());
                            editor.putString("univ_id", userProfileData.get("univ_id").toString());
                            editor.putString("place_id", userProfileData.get("place_id").toString());
                            editor.commit();

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }


            @Override
            public void doFinish() {
                //To change body of implemented methods use File | Settings | File Templates.

                if(loginResultData != null)
                {
                    showActivity(HomeActivity.class);
                }
                else
                {
                    Toast.makeText(commonAQuery.getContext(), "login error", 1).show();
                }


            }
        }, "Login");

    }

    public void doJoinButton(View v) {

        showActivity(JoinActivity.class);
    }


}
