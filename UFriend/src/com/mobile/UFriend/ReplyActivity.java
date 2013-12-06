package com.mobile.UFriend;

import android.os.Bundle;
import android.widget.Toast;
import com.mobile.system.lib.JinAsync;
import com.mobile.system.lib.JinProgress;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        setContentView(R.layout.reply_view);

        setActionBarTitle("Reply");

        strCurrentBoardId = getIntent().getStringExtra("board_id").toString();
        strCurrentUnivId = getIntent().getStringExtra("univ_id").toString();

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

                }
            }
        });
    }
}
