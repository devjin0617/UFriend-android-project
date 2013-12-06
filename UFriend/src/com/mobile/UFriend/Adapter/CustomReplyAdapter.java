package com.mobile.UFriend.Adapter;

/**
 * Created with IntelliJ IDEA.
 * User: jin_note
 * Date: 13. 12. 6
 * Time: 오후 4:20
 * To change this template use File | Settings | File Templates.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.mobile.UFriend.R;

import java.util.List;
import java.util.Map;

public class CustomReplyAdapter extends BaseAdapter {

    private Context mContext;
    private List<Map<String, Object>> mListArray;
    private LayoutInflater mInflater;

    public CustomReplyAdapter(Context context,
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
            convertView = mInflater.inflate(R.layout.custom_listview_row_reply, null);
        }

//        if (dataMap != null) {
//            TextView subjectText = (TextView) convertView
//                    .findViewById(R.id.calcListViewSubjectRow);
//            subjectText.setText(dataMap.get("subject"));
//            TextView pointText = (TextView) convertView
//                    .findViewById(R.id.calcListViewPointRow);
//            String pointTmp = dataMap.get("point") + "학점";
//            pointText.setText(pointTmp);
//            TextView gradeText = (TextView) convertView
//                    .findViewById(R.id.calcListViewGradeRow);
//            gradeText.setText(dataMap.get("grade"));
//
//        }

        if(dataMap != null)
        {
            TextView nameTextView = (TextView)convertView.findViewById(R.id.custom_listview_row_reply_name);
            nameTextView.setText(dataMap.get("name").toString());

            TextView univTextView = (TextView)convertView.findViewById(R.id.custom_listview_row_reply_univ);
            univTextView.setText(dataMap.get("univ_name").toString());

            TextView contentTextView = (TextView)convertView.findViewById(R.id.custom_listview_row_reply_text);
            contentTextView.setText(dataMap.get("content").toString());

            TextView replyDateTimeTextView = (TextView)convertView.findViewById(R.id.custom_listview_row_reply_datetime);
            replyDateTimeTextView.setText(dataMap.get("regi_datetime").toString());

        }

        return convertView;  //To change body of implemented methods use File | Settings | File Templates.
    }
}