package com.mobile.UFriend;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.mobile.UFriend.Adapter.CustomReplyAdapter;
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
 * Date: 13. 12. 6
 * Time: 오후 2:08
 * To change this template use File | Settings | File Templates.
 */
public class ReplyActivity extends CommonUFriendActivity {

    private String strCurrentBoardId;
    private String strCurrentUnivId;

    private List<Map<String, Object>> replyData;
    private Map<String, Object> sendResultData;

    private CustomReplyAdapter customReplyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        setContentView(R.layout.reply_view);

        setActionBarTitle("댓글달기");

        commonAQuery.id(R.id.custom_actionbar_left_button).getView()
                .setVisibility(View.VISIBLE);

        strCurrentBoardId = getIntent().getStringExtra("board_id").toString();
        strCurrentUnivId = getIntent().getStringExtra("univ_id").toString();





        commonAQuery.id(R.id.reply_send_button).clicked(commonAQuery.getContext(), "doSend");

        new JinProgress(commonAQuery.getContext(), new JinAsync() {
            @Override
            public void doASyncData() {
                //To change body of implemented methods use File | Settings | File Templates.

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("reply_id", strCurrentBoardId);
                params.put("univ_id", strCurrentUnivId);

                try {
                    replyData = Face3Utils.getUrlLongFromList(UFriendVariable.getServerHttpUrl("/boarddata/getBoardList.do"), params);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }


            }

            @Override
            public void doFinish() {
                //To change body of implemented methods use File | Settings | File Templates.

                if(replyData != null)
                {
                    customReplyAdapter = new CustomReplyAdapter(commonAQuery.getContext(), replyData);

                    commonAQuery.id(R.id.reply_list_view).getListView().setAdapter(customReplyAdapter);
                }
            }
        });
    }



    public void doSend(View v)
    {

        final String strReplyText = commonAQuery.id(R.id.reply_edit_text).getEditable().toString();

        if(strReplyText.length() > 0)
        {
            new JinProgress(commonAQuery.getContext(), new JinAsync() {
                @Override
                public void doASyncData() {
                    //To change body of implemented methods use File | Settings | File Templates.

                    Map<String, Object> params = new HashMap<String, Object>();

                    // (#reply_id#, #content#, NOW(), #user_id#, #univ_id#)
                    params.put("reply_id", strCurrentBoardId);
                    params.put("content", strReplyText);
                    params.put("user_id", UFriendUtils.getUserData(commonAQuery).getString("user_id", ""));
                    params.put("univ_id", strCurrentUnivId);

                    try {
                        sendResultData = Face3Utils.getUrlLongToJsonObject(UFriendVariable.getServerHttpUrl("/boarddata/addBoardContent.do"), params);

                        if(sendResultData.get("success").equals("true"))
                        {
                            Map<String, Object> reParams = new HashMap<String, Object>();
                            reParams.put("reply_id", strCurrentBoardId);
                            reParams.put("univ_id", strCurrentUnivId);

                            replyData.clear();
                            replyData = Face3Utils.getUrlLongFromList(UFriendVariable.getServerHttpUrl("/boarddata/getBoardList.do"), reParams);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (JSONException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }

                }

                @Override
                public void doFinish() {
                    //To change body of implemented methods use File | Settings | File Templates.

                    if(sendResultData.get("success").equals("true"))
                    {

                        customReplyAdapter = new CustomReplyAdapter(commonAQuery.getContext(), replyData);

                        commonAQuery.id(R.id.reply_list_view).getListView().setAdapter(customReplyAdapter);
                        commonAQuery.id(R.id.reply_edit_text).getEditText().setText("");

                    }
                    else
                    {
                        Toast.makeText(commonAQuery.getContext(), "network error", 1).show();
                    }

                    InputMethodManager imm = (InputMethodManager)getSystemService(commonAQuery.getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(commonAQuery.id(R.id.reply_edit_text).getEditText().getWindowToken(), 0);


                }
            });
        }
        else
        {
            Toast.makeText(commonAQuery.getContext(), "not reply message", 1).show();
        }
    }
}
