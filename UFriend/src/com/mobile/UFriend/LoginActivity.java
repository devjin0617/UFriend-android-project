package com.mobile.UFriend;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.mobile.system.lib.JinAsync;
import com.mobile.system.lib.JinProgress;
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
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

            @Override
            public void doFinish() {
                //To change body of implemented methods use File | Settings | File Templates.

                if (loginResultData != null) {
                    if (loginResultData.get("success").toString().equals("true")) {
                        Toast.makeText(commonAQuery.getContext(), "Login True", 1).show();
                        showActivity(HomeActivity.class);
                    } else {
                        Toast.makeText(commonAQuery.getContext(), "Login Flase", 1).show();
                    }
                }

            }
        }, "Login");

    }

    public void doJoinButton(View v) {

        showActivity(JoinActivity.class);
    }


}
