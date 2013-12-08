package com.mobile.UFriend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobile.system.resource.UFriendUtils;
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
	
	public List<Map<String, Object>> univData;
    public String strSelectUnivId;

    public Map<String, Object> resultData;

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.


    }

    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join_view);

		commonAQuery.id(R.id.custom_actionbar_left_button).getView()
				.setVisibility(View.VISIBLE);

		setActionBarTitle("회원가입");

		commonAQuery.id(R.id.join_join_button).clicked(
				commonAQuery.getContext(), "doJoin");

		commonAQuery.id(R.id.join_user_univ_button).clicked(
				commonAQuery.getContext(), "doUnivSelect");
	}

	public void doJoin(View v) {
		final String strEmail = commonAQuery.id(R.id.join_user_email_edit_text)
				.getEditable().toString();
        final String strPassword = commonAQuery.id(R.id.join_password1_edit_text)
				.getEditable().toString();
        final String strPasswordCheck = commonAQuery
				.id(R.id.join_passwordcheck_edit_text).getEditable().toString();
        final String strName = commonAQuery.id(R.id.join_user_name_edit_text)
				.getEditable().toString();

		if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches())) {
			Toast.makeText(commonAQuery.getContext(), "이메일을 확인해주세요", 1).show();
			return;
		}

		if (!(strPassword.endsWith(strPasswordCheck))) {
			Toast.makeText(commonAQuery.getContext(), "비밀번호를 확인해주세요", 1)
					.show();
		}

		if ((strName.length() == 0)) {
			Toast.makeText(commonAQuery.getContext(), "이름을 입력하세요", 1).show();
		}

		else {

            new JinProgress(commonAQuery.getContext(), new JinAsync() {
                @Override
                public void doASyncData() {
                    //To change body of implemented methods use File | Settings | File Templates.

                    Map<String, Object> params = new HashMap<String, Object>();

                    params.put("user_email", strEmail);
                    params.put("password", UFriendUtils.md5(strPassword));
                    params.put("name", strName);
                    params.put("univ_id", strSelectUnivId);
                    params.put("place_id", "2");

                    try {
                        resultData = Face3Utils.getUrlLongToJsonObject(UFriendVariable.getServerHttpUrl("/commondata/join.do"), params);
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (JSONException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }

                }

                @Override
                public void doFinish() {
                    //To change body of implemented methods use File | Settings | File Templates.

                    if(resultData != null)
                    {
                        if(resultData.get("success").toString().equals("true"))
                        {

                            Toast.makeText(commonAQuery.getContext(), "회원가입에 성공하였습니다.", 1).show();
                            Toast.makeText(commonAQuery.getContext(), "로그인해주세요", 1).show();
                            onBackPressed();
                        }
                        else
                        {
                            Toast.makeText(commonAQuery.getContext(), "이메일중복이나 네트워크에 문제가 있습니다", 1).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(commonAQuery.getContext(), "이메일중복이나 네트워크에 문제가 있습니다", 1).show();
                    }
                }
            });
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