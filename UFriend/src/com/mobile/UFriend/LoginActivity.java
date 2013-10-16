package com.mobile.UFriend;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.androidquery.callback.AjaxStatus;
import com.mobile.system.Lib.JinAsync;
import com.mobile.system.Lib.JinProgress;
import org.json.JSONObject;

public class LoginActivity extends CommonUFriendActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        setActionBarTitle("Login");

        // ActionBar Init
        commonAQuery.id(R.id.custom_actionbar_right_button).getView().setVisibility(View.VISIBLE);

        commonAQuery.id(R.id.login_login_button).clicked(currentContext, "doLoginButton");
        commonAQuery.id(R.id.custom_actionbar_right_button).clicked(currentContext, "doJoinButton");



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

    public void doLoginButton(View v)
    {
        JinProgress.doBackgroundAndLoading(currentContext,new JinAsync() {
            @Override
            public void doASyncData() {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void doFinish() {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        }, "Login");

    }

    public void doJoinButton(View v)
    {
        Intent i = new Intent(this, JoinActivity.class);

        startActivity(i);

    }

}
