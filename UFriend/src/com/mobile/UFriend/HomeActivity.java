package com.mobile.UFriend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.mobile.UFriend.Adapter.CustomContentAdapter;
import com.mobile.system.lib.JinAsync;
import com.mobile.system.lib.JinProgress;
import com.mobile.system.resource.UFriendUtils;
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

        loadData();
        backCount = 0;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);

        setActionBarTitle("Main");

        commonAQuery.id(R.id.custom_actionbar_right_button).getView().setVisibility(View.VISIBLE);
        commonAQuery.id(R.id.custom_actionbar_right_button).clicked(commonAQuery.getContext(), "doWrite");

        commonAQuery.id(R.id.custom_actionbar_left_button).getView().setVisibility(View.VISIBLE);
        commonAQuery.id(R.id.custom_actionbar_left_button_imageview).getImageView().setBackgroundResource(R.drawable.actionbar_right_plus_button);
        commonAQuery.id(R.id.custom_actionbar_left_button).clicked(commonAQuery.getContext(), "doMenu");

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

    public void doWrite(View v)
    {
        showActivity(WriteActivity.class, new CommonIntentListener() {
            @Override
            public void setExtraData(Intent i) {
                //To change body of implemented methods use File | Settings | File Templates.

                i.putExtra("univ_id", UFriendUtils.getUserData(commonAQuery).getString("univ_id", "0").toString());
            }
        });
    }

    public void doMenu(View v)
    {
        final CharSequence[] items = {"내프로필", "다른학교보기", "주변친구보기", "설정"};

        AlertDialog.Builder builder = new AlertDialog.Builder(
                commonAQuery.getContext());
        builder.setTitle("UFriend Menu");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                switch (item)
                {
                    case 0 :


                        break;
                    case 1 :

                        break;
                    case 2 :
                        showActivity(LocationFriendActivity.class);
                        break;
                    case 3 :
                        showActivity(SettingActivity.class);
                        break;
                    default:

                        break;
                }
            }
        });
        AlertDialog alert = builder.create();

        alert.show();
    }

    public void loadData()
    {
        new JinProgress(commonAQuery.getContext(), new JinAsync() {
            @Override
            public void doASyncData() {
                //To change body of implemented methods use File | Settings | File Templates.

                SharedPreferences sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);


                Map<String, Object> params = new HashMap<String, Object>();
                params.put("reply_id", "0");
                params.put("univ_id", sharedPreferences.getString("univ_id", "0").toString());

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