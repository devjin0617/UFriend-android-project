package com.mobile.UFriend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.mobile.system.resource.UFriendUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jin_note
 * Date: 13. 12. 7
 * Time: 오후 9:17
 * To change this template use File | Settings | File Templates.
 */
public class SettingActivity extends CommonUFriendActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        setContentView(R.layout.setting_view);

        setActionBarTitle("설정");

        commonAQuery.id(R.id.custom_actionbar_left_button).getView().setVisibility(View.VISIBLE);


        List<String> settingMenuData = new ArrayList<String>();
        settingMenuData.add("만든사람");
        settingMenuData.add("버전정보");
        settingMenuData.add("로그아웃");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(commonAQuery.getContext(), android.R.layout.simple_list_item_1, settingMenuData);

        commonAQuery.id(R.id.setting_listview).getListView().setAdapter(arrayAdapter);

        commonAQuery.id(R.id.setting_listview).itemClicked(commonAQuery.getContext(), "doSettingMenuClick");

    }

    public void doSettingMenuClick(AdapterView<?> parent, View v, int pos, long id)
    {

        switch (pos)
        {
            case 0 :
                final CharSequence[] items = {"서버:김청진", "클라이언트:이관승"};

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        commonAQuery.getContext());

                builder.setTitle("만든사람");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
                break;
            case 1 :
                final CharSequence[] vItems = {"현재버전 : 1.1.1", "최신버전 : 1.1.1"};

                AlertDialog.Builder vBuilder = new AlertDialog.Builder(
                        commonAQuery.getContext());

                vBuilder.setTitle("버전정보");
                vBuilder.setItems(vItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }
                });

                AlertDialog vAlert = vBuilder.create();

                vAlert.show();
                break;
            case 2 :
                SharedPreferences.Editor editor = UFriendUtils.getEditor(commonAQuery);
                editor.putString("AUTO_LOGIN", "false");
                editor.commit();

                showActivity(LoginActivity.class);
                break;
            default:
                break;
        }

    }
}
