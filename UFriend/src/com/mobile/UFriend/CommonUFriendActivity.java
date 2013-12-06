package com.mobile.UFriend;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.androidquery.AQuery;

/**
 * Created with IntelliJ IDEA.
 * User: jin_note
 * Date: 13. 10. 15
 * Time: 오후 6:46
 * To change this template use File | Settings | File Templates.
 */
public class CommonUFriendActivity extends Activity {

    public AQuery commonAQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        commonAQuery = new AQuery(this);


        ActionBar actionBar = getActionBar();


        // 뷰를 가져옴..
        View v = getLayoutInflater().inflate(R.layout.custom_actionbar,
                null);

        // 액션바에 커스텀뷰를 설정
        actionBar.setCustomView(v, new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT));

        //커스텀뷰를 써야하므로 옵션에서 설정
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);


        // ActionBar 버튼 기본적으로 숨김
        commonAQuery.id(R.id.custom_actionbar_left_button).getView().setVisibility(View.INVISIBLE);
        commonAQuery.id(R.id.custom_actionbar_right_button).getView().setVisibility(View.INVISIBLE);
        commonAQuery.id(R.id.custom_actionbar_left_button).clicked(commonAQuery.getContext(), "doBack");

        // Transition Effect
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    public void setActionBarTitle(String strTitle) {
        commonAQuery.id(R.id.custom_actionbar_title_textview).text(strTitle);
    }

    public void doBack(View v) {
        onBackPressed();
    }

    public Dialog getDialog(String message) {
        Dialog busyDialog = new Dialog(commonAQuery.getContext(), R.style.lightbox_dialog);
        busyDialog.setContentView(R.layout.lightbox_dialog);
        ((TextView) busyDialog.findViewById(R.id.dialogText)).setText(message);

        return busyDialog;
    }

    public Dialog getDialog() {
        Dialog busyDialog = new Dialog(commonAQuery.getContext(), R.style.lightbox_dialog);
        busyDialog.setContentView(R.layout.lightbox_dialog);
        ((TextView) busyDialog.findViewById(R.id.dialogText)).setText("Loading...");

        return busyDialog;
    }

    public void showActivity(Class activityClass, CommonIntentListener commonIntentListener) {
        Intent i = new Intent(commonAQuery.getContext(), activityClass);

        commonIntentListener.setExtraData(i);

        startActivity(i);

    }

    public void showActivity(Class activityClass) {
        Intent i = new Intent(commonAQuery.getContext(), activityClass);

        startActivity(i);

    }

    public Location getMyLocation() {

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        // provider 기지국||GPS 를 통해서 받을건지 알려주는 Stirng 변수
        // minTime 최소한 얼마만의 시간이 흐른후 위치정보를 받을건지 시간간격을 설정 설정하는 변수
        // minDistance 얼마만의 거리가 떨어지면 위치정보를 받을건지 설정하는 변수
        // manager.requestLocationUpdates(provider, minTime, minDistance, listener);

        // 10초
//        long minTime = 10000;

        // 거리는 0으로 설정
        // 그래서 시간과 거리 변수만 보면 움직이지않고 10초뒤에 다시 위치정보를 받는다
//        float minDistance = 0;

//        MyLocationListener listener = new MyLocationListener();
//
//        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, listener);

        Location currentLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        return currentLocation;
    }

}
