package com.mobile.UFriend;

import android.os.Bundle;
import android.widget.ArrayAdapter;

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


        List<String> settingMenuData = new ArrayList<String>();
        settingMenuData.add("만든사람");
        settingMenuData.add("버전정보");
        settingMenuData.add("로그아웃");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(commonAQuery.getContext(), android.R.layout.simple_list_item_1, settingMenuData);

        commonAQuery.id(R.id.setting_listview).getListView().setAdapter(arrayAdapter);

    }
}
