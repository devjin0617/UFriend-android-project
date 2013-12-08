package com.mobile.UFriend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.mobile.UFriend.Adapter.CustomContentAdapter;
import com.mobile.system.lib.JinAsync;
import com.mobile.system.lib.JinProgress;
import com.mobile.system.resource.UFriendVariable;
import com.mobile.system.utils.Face3Utils;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jin_note
 * Date: 13. 12. 8
 * Time: 오후 7:26
 * To change this template use File | Settings | File Templates.
 */
public class BoardActivity extends CommonUFriendActivity {

    public List<Map<String, Object>> boardData;

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.

        loadData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.home_view);

        setActionBarTitle(getIntent().getStringExtra("univ_name").toString());

        commonAQuery.id(R.id.custom_actionbar_left_button).getView().setVisibility(View.VISIBLE);

        commonAQuery.id(R.id.home_list_view).getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //To change body of implemented methods use File | Settings | File Templates.

                final int currentPos = position;

                showActivity(ReplyActivity.class, new CommonIntentListener() {
                    @Override
                    public void setExtraData(Intent i) {
                        //To change body of implemented methods use File | Settings | File Templates.

                        Map<String, Object> item = boardData.get(currentPos);

                        i.putExtra("board_id", item.get("board_id").toString());
                        i.putExtra("univ_id", item.get("univ_id").toString());
                    }
                });
            }
        });
    }

    public void loadData() {
        new JinProgress(commonAQuery.getContext(), new JinAsync() {
            @Override
            public void doASyncData() {
                //To change body of implemented methods use File | Settings | File Templates.

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("reply_id", "0");
                params.put("univ_id", getIntent().getStringExtra("univ_id").toString());

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

                CustomContentAdapter contentAdapter = new CustomContentAdapter(commonAQuery.getContext(), boardData);

                commonAQuery.id(R.id.home_list_view).getListView().setAdapter(contentAdapter);


            }
        });
    }
}
