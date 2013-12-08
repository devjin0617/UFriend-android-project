package com.mobile.UFriend;

import android.content.SharedPreferences;
import android.location.Location;
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

    public static int backCount = 0;

    public String strUserEmail = "";
    public String strPassword = "";

    public Map<String, Object> loginResultData = null;
    public Location currentLocation;

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.

        backCount = 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        setActionBarTitle("UFriend 로그인");

        if(UFriendUtils.getUserData(commonAQuery).getString("AUTO_LOGIN", "false").equals("true"))
        {
            showActivity(HomeActivity.class);
        }

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
                params.put("password", UFriendUtils.md5(strPassword));


                try {
                    loginResultData = Face3Utils.getUrlLongToJsonObject(UFriendVariable.getServerHttpUrl("/commondata/login.do"), params);

                    if (loginResultData != null) {
                        if (loginResultData.get("success").toString().equals("true")) {

                            Map<String, Object> userProfileData = Face3Utils.getUrlLongToJsonObject(UFriendVariable.getServerHttpUrl("/commondata/getUserProfile.do"), params);

                            currentLocation = getMyLocation();

                            if(currentLocation != null)
                            {
                                Map<String, Object> locationParams = new HashMap<String, Object>();
                                locationParams.put("latitude", String.valueOf(currentLocation.getLatitude()));
                                locationParams.put("longitude", String.valueOf(currentLocation.getLongitude()));
                                locationParams.put("user_id", userProfileData.get("user_id").toString());

                                Face3Utils.getUrlLongToJsonObject(UFriendVariable.getServerHttpUrl("/locationdata/addUserLocation.do"), locationParams);
                            }



                            SharedPreferences.Editor editor = UFriendUtils.getEditor(commonAQuery);

                            editor.putString("AUTO_LOGIN", "true");
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
                    if(loginResultData.get("success").toString().equals("true"))
                    {
                        showActivity(HomeActivity.class);
                    }
                    else
                    {
                        Toast.makeText(commonAQuery.getContext(), "아이디&비밀번호를 확인해주세요", 1).show();
                    }

                }
                else
                {
                    Toast.makeText(commonAQuery.getContext(), "네트워크에 문제가 있습니다.", 1).show();
                }


            }
        }, "Login");

    }

    public void doJoinButton(View v) {

        showActivity(JoinActivity.class);
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
