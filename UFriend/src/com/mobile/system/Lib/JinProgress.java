package com.mobile.system.Lib;

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

    private static JinProgress singleton = new JinProgress();

    private JinProgress(){
    }

    private static JinProgress getInstance(){
        return singleton;
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

    public static void doBackgroundAndLoading(Context pContext, JinAsync pAsync) {

        getInstance();
        singleton.currentContext = pContext;
        singleton.currentAsync = pAsync;

        singleton.execute();
    }

    public static void doBackgroundAndLoading(Context pContext, JinAsync pAsync, String strMessage) {
        singleton.currentContext = pContext;
        singleton.currentAsync = pAsync;
        singleton.currentMessage = strMessage;

        singleton.execute();
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

