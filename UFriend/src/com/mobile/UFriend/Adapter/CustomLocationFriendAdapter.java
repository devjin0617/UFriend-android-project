package com.mobile.UFriend.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.mobile.UFriend.R;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jin_note
 * Date: 13. 12. 7
 * Time: 오전 12:21
 * To change this template use File | Settings | File Templates.
 */
public class CustomLocationFriendAdapter extends BaseAdapter {

    private Context mContext;
    private List<Map<String, Object>> mListArray;
    private LayoutInflater mInflater;

    public CustomLocationFriendAdapter(Context context,
                                List<Map<String, Object>> objects) {
        this.mContext = context;
        this.mListArray = objects;
        this.mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mListArray.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getItem(int position) {
        return mListArray.get(position);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getItemId(int position) {
        return position;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Map<String, Object> dataMap = mListArray.get(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_gridview_cell_friend, null);
        }

        TextView userNameTextView = (TextView)convertView.findViewById(R.id.custom_gridview_user_name_text_view);
        TextView univNameTextView = (TextView)convertView.findViewById(R.id.custom_gridview_user_univ_text_view);
        TextView distanceTextView = (TextView)convertView.findViewById(R.id.custom_gridview_user_distance_text_view);

        userNameTextView.setText(dataMap.get("name").toString());
        univNameTextView.setText(dataMap.get("univ_name").toString());

        String strDistance = dataMap.get("distance").toString();
        double dDistance = Double.valueOf(strDistance);
        int iDistance;
        String strResultDistance = "";

        if(dDistance < 1.0)
        {
            iDistance = (int)(dDistance * 1000);
            strResultDistance = iDistance + "m";
        }
        else
        {
            iDistance = (int)dDistance;
            strResultDistance = iDistance + "km";
        }

        distanceTextView.setText(strResultDistance);


        return convertView;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
