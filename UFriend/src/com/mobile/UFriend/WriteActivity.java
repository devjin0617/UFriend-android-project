package com.mobile.UFriend;

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

/**
 * Created with IntelliJ IDEA.
 * User: jin_note
 * Date: 13. 12. 6
 * Time: 오후 9:06
 * To change this template use File | Settings | File Templates.
 */
public class WriteActivity extends CommonUFriendActivity {

    private String strCurrentUnivId;
    private Map<String, Object> resultData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        setContentView(R.layout.write_view);

        setActionBarTitle("새로운글쓰기");

        strCurrentUnivId = getIntent().getStringExtra("univ_id").toString();

        commonAQuery.id(R.id.custom_actionbar_left_button).getView()
                .setVisibility(View.VISIBLE);

        commonAQuery.id(R.id.write_write_button).clicked(commonAQuery.getContext(), "doContentWirte");

    }

    public void doContentWirte(View v)
    {

        final String strContentText = commonAQuery.id(R.id.write_content_edit_text).getEditable().toString();

        if(strContentText.length() != 0)
        {
            new JinProgress(commonAQuery.getContext(), new JinAsync() {
                @Override
                public void doASyncData() {
                    //To change body of implemented methods use File | Settings | File Templates.

                    Map<String, Object> params = new HashMap<String, Object>();
                    // (#reply_id#, #content#, NOW(), #user_id#, #univ_id#)
                    params.put("reply_id", "0");
                    params.put("content", strContentText);
                    params.put("user_id", UFriendUtils.getUserData(commonAQuery).getString("user_id", ""));
                    params.put("univ_id", strCurrentUnivId);


                    try {
                        resultData = Face3Utils.getUrlLongToJsonObject(UFriendVariable.getServerHttpUrl("/boarddata/addBoardContent.do"), params);
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (JSONException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }

                }

                @Override
                public void doFinish() {
                    //To change body of implemented methods use File | Settings | File Templates.

                    if(resultData != null)
                    {
                        onBackPressed();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(commonAQuery.getContext(), "input content text", 1).show();
        }

    }
}
