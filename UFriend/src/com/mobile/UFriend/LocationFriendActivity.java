package com.mobile.UFriend;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import com.mobile.UFriend.Adapter.CustomLocationFriendAdapter;
import com.mobile.system.lib.JinAsync;
import com.mobile.system.lib.JinProgress;
import com.mobile.system.resource.UFriendUtils;
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
 * Date: 13. 12. 7
 * Time: 오전 12:11
 * To change this template use File | Settings | File Templates.
 */
public class LocationFriendActivity extends CommonUFriendActivity {

    private List<Map<String, Object>> friendData;
    private CustomLocationFriendAdapter friendGridAdapter;

    public Location currentLocation;



    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.

        currentLocation = getMyLocation();
        loadData();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.location_grid_view);


        commonAQuery.id(R.id.custom_actionbar_left_button).getView()
                .setVisibility(View.VISIBLE);

    }

    public void loadData()
    {
        new JinProgress(commonAQuery.getContext(), new JinAsync() {
            @Override
            public void doASyncData() {
                //To change body of implemented methods use File | Settings | File Templates.

                Map<String, Object> params = new HashMap<String, Object>();

                //user_latitude
                //user_longitude


                params.put("user_id", UFriendUtils.getUserData(commonAQuery).getString("user_id", "").toString());

                if(currentLocation != null)
                {
                    params.put("user_latitude", String.valueOf(currentLocation.getLatitude()));
                    params.put("user_longitude", String.valueOf(currentLocation.getLongitude()));
                }
                else
                {
                    params.put("user_latitude", String.valueOf(0));
                    params.put("user_longitude", String.valueOf(0));
                }


                try {

                    friendData = Face3Utils.getUrlLongFromList(UFriendVariable.getServerHttpUrl("/locationdata/getOtherUserLocationList.do"), params);

                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

            }

            @Override
            public void doFinish() {
                //To change body of implemented methods use File | Settings | File Templates.

                if(friendData != null)
                {

                    friendGridAdapter = new CustomLocationFriendAdapter(commonAQuery.getContext(), friendData);
                    commonAQuery.id(R.id.location_grid_view).getGridView().setAdapter(friendGridAdapter);

                }
            }
        });
    }



}
