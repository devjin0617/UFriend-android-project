package com.mobile.UFriend;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.mobile.UFriend.R;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.mobile.system.lib.JinAsync;
import com.mobile.system.lib.JinProgress;
import com.mobile.system.resource.UFriendVariable;
import com.mobile.system.utils.Face3Utils;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jin_note
 * Date: 13. 12. 8
 * Time: 오후 6:24
 * To change this template use File | Settings | File Templates.
 */
public class UnivListActivity extends CommonUFriendActivity {

    public List<Map<String, Object>> univData;

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.

        loadData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        setContentView(R.layout.univ_view);

        setActionBarTitle("학교선택");

        commonAQuery.id(R.id.custom_actionbar_left_button).getView().setVisibility(View.VISIBLE);

        commonAQuery.id(R.id.univ_list_view).itemClicked(commonAQuery.getContext(), "doUnivSelect");


    }

    public void loadData()
    {

        new JinProgress(commonAQuery.getContext(), new JinAsync() {
            @Override
            public void doASyncData() {
                //To change body of implemented methods use File | Settings | File Templates.


                try {
                    univData = Face3Utils.getUrlLongFromList(UFriendVariable.getServerHttpUrl("/commondata/getUnivList.do"), null);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }


            }

            @Override
            public void doFinish() {
                //To change body of implemented methods use File | Settings | File Templates.

                if(univData != null)
                {
                    ArrayList<String> resultData = new ArrayList<String>();

                    for(Map item : univData)
                    {
                        resultData.add(item.get("univ_name").toString());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(commonAQuery.getContext(), android.R.layout.simple_list_item_1, resultData);

                    commonAQuery.id(R.id.univ_list_view).getListView().setAdapter(adapter);

                }
            }
        });
    }


    public void doUnivSelect(AdapterView<?> parent, View v, int pos, long id)
    {

        final int currentPos = pos;

        showActivity(BoardActivity.class, new CommonIntentListener() {
            @Override
            public void setExtraData(Intent i) {
                //To change body of implemented methods use File | Settings | File Templates.

                Map<String, Object> currentData = univData.get(currentPos);

                i.putExtra("univ_id", currentData.get("univ_id").toString());
                i.putExtra("univ_name", currentData.get("univ_name").toString());
            }
        });
    }
}
