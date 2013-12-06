package com.mobile.UFriend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mobile.system.lib.JinAsync;
import com.mobile.system.lib.JinProgress;
import com.mobile.system.resource.UFriendVariable;
import com.mobile.system.utils.Face3Utils;

/**
 * Created with IntelliJ IDEA. User: jin_note Date: 13. 10. 17 Time: 오전 2:20 To
 * change this template use File | Settings | File Templates.
 */

public class JoinActivity extends CommonUFriendActivity {
	
	private List<Map<String, Object>> univData;
    private String strSelectUnivId;

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.


    }

    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join_view);

		commonAQuery.id(R.id.custom_actionbar_left_button).getView()
				.setVisibility(View.VISIBLE);

		setActionBarTitle("Join");

		commonAQuery.id(R.id.join_join_button).clicked(
				commonAQuery.getContext(), "doJoin");

		commonAQuery.id(R.id.join_user_univ_button).clicked(
				commonAQuery.getContext(), "doUnivSelect");
	}

	public void doJoin(View v) {
		String strEmail = commonAQuery.id(R.id.join_user_email_edit_text)
				.getEditable().toString();
		String strPassword = commonAQuery.id(R.id.join_password1_edit_text)
				.getEditable().toString();
		String strPasswordCheck = commonAQuery
				.id(R.id.join_passwordcheck_edit_text).getEditable().toString();
		String strName = commonAQuery.id(R.id.join_user_name_edit_text)
				.getEditable().toString();

		if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches())) {
			Toast.makeText(commonAQuery.getContext(), "email error", 1).show();
			return;
		}

		if (!(strPassword.endsWith(strPasswordCheck))) {
			Toast.makeText(commonAQuery.getContext(), "password error", 1)
					.show();
		}

		if ((strName.length() == 0)) {
			Toast.makeText(commonAQuery.getContext(), "name error", 1).show();
		}

		else {
			Toast.makeText(commonAQuery.getContext(), "success", 1).show();
		}

	}

	public void doUnivSelect(View v) {

		new JinProgress(commonAQuery.getContext(), new JinAsync() {

			@Override
			public void doASyncData() {
				// TODO Auto-generated method stub

				try {
					univData = Face3Utils.getUrlLongFromList(UFriendVariable.getServerHttpUrl("/commondata/getUnivList.do"), null);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			@Override
			public void doFinish() {
				// TODO Auto-generated method stub

				if(univData != null)
				{
					
					List<String> tempList = new ArrayList<String>();
					for(Map item : univData)
					{
						tempList.add(item.get("univ_name").toString());
					}
					final CharSequence[] items = tempList.toArray(new CharSequence[tempList.size()]);
					
					AlertDialog.Builder builder = new AlertDialog.Builder(
							commonAQuery.getContext());
					builder.setTitle("Alert Dialog with ListView");

					builder.setItems(items, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
//							Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();

                            Map<String, Object> selectData = univData.get(item);
                            strSelectUnivId = String.valueOf(selectData.get("univ_id"));
                            commonAQuery.id(R.id.join_user_univ_button).getButton().setText(selectData.get("univ_name").toString());
						}
					});
					AlertDialog alert = builder.create();

					alert.show();
				}

			}

		}, "Univ Load");

		/*
		 * AlertDialog.Builder builder; builder = new AlertDialog.Builder(this);
		 * builder.setMessage("University Select") .setCancelable(false)
		 * .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) { //
		 * TODO Auto-generated method stub
		 * 
		 * dialog.cancel(); } }).show();
		 */

	}

}