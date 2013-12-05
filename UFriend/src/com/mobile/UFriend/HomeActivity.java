package com.mobile.UFriend;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.mobile.system.lib.JinAsync;
import com.mobile.system.lib.JinProgress;
import com.mobile.system.resource.UFriendVariable;
import com.mobile.system.utils.Face3Utils;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jin_note
 * Date: 13. 10. 17
 * Time: 오전 3:19
 * To change this template use File | Settings | File Templates.
 */
public class HomeActivity extends CommonUFriendActivity {

    public static int backCount = 0;

    public List<Map<String, Object>> boardData = null;

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.

        backCount = 0;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);

        setActionBarTitle("Main");

        new JinProgress(commonAQuery.getContext(), new JinAsync() {
            @Override
            public void doASyncData() {
                //To change body of implemented methods use File | Settings | File Templates.

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("reply_id", "0");
                params.put("univ_id", "2");

                try {
                    boardData = Face3Utils.getUrlLongFromList(UFriendVariable.getServerHttpUrl("/boarddata/getBoardList.do"), params);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }


            }

            @Override
            public void doFinish() {
                //To change body of implemented methods use File | Settings | File Templates.

                ArrayList<String> testData = new ArrayList<String>();

                for (Map<String, Object> item : boardData) {
                    testData.add(item.get("content").toString());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(commonAQuery.getContext(), android.R.layout.simple_list_item_1, testData);

                commonAQuery.id(R.id.home_list_view).getListView().setAdapter(adapter);


            }
        });
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