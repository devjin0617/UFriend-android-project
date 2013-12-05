package com.mobile.system.lib;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import com.mobile.UFriend.R;

/**
 * Created with IntelliJ IDEA.
 * User: jin_note
 * Date: 13. 10. 15
 * Time: 오후 4:50
 * To change this template use File | Settings | File Templates.
 */

public class JinProgress extends AsyncTask<String, String, String> {

    private Context currentContext;
    private JinAsync currentAsync;
    private String currentMessage;
    private Dialog busyDialog;

    public JinProgress(Context pContext, JinAsync pAsync){

        this.currentContext = pContext;
        this.currentAsync = pAsync;

        execute();

    }

    public JinProgress(Context pContext, JinAsync pAsync, String strMessage){

        this.currentContext = pContext;
        this.currentAsync = pAsync;
        this.currentMessage = strMessage;

        execute();

    }

    private void showBusyDialog(String message) {
        busyDialog = new Dialog(currentContext, R.style.lightbox_dialog);
        busyDialog.setContentView(R.layout.lightbox_dialog);
        ((TextView)busyDialog.findViewById(R.id.dialogText)).setText(message);

        busyDialog.show();
    }

    private void dismissBusyDialog() {
        if (busyDialog != null)
            busyDialog.dismiss();

        busyDialog = null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();    //To change body of overridden methods use File | Settings | File Templates.

        if(currentMessage == null)
        {
            currentMessage = "Loading..";
        }

        showBusyDialog(currentMessage);


    }

    @Override
    protected String doInBackground(String... strings) {

        currentAsync.doASyncData();

        return "";  //To change body of implemented methods use File | Settings | File Templates.

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);    //To change body of overridden methods use File | Settings | File Templates.

        dismissBusyDialog();
        currentAsync.doFinish();

    }
}

