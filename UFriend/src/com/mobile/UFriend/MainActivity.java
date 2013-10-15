package com.mobile.UFriend;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

    private ProgressDialog loagindDialog;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        createThreadAndDialog();

        //new MainAsync().execute("");


    }

    void createThreadAndDialog() {
        /* ProgressDialog */
        loagindDialog = ProgressDialog.show(this, "title", "Loading", true, true);
        loagindDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 시간걸리는 처리
                //handler.sendEmptyMessage(5000);
            }
        });
        thread.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            loagindDialog.dismiss(); // 다이얼로그 삭제
            // View갱신
        }
    };

    /*
    private Dialog busyDialog;

    public void showBusyDialog(String message) {
        busyDialog = new Dialog(this, R.style.lightbox_dialog);
        busyDialog.setContentView(R.layout.lightbox_dialog);
        ((TextView)busyDialog.findViewById(R.id.dialogText)).setText(message);

        busyDialog.show();
    }

    public void dismissBusyDialog() {
        if (busyDialog != null)
            busyDialog.dismiss();

        busyDialog = null;
    }
    */

    public class MainAsync extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        protected String doInBackground(String... strings) {

            Log.e("doInBackground", "1");


            return "";  //To change body of implemented methods use File | Settings | File Templates.

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);    //To change body of overridden methods use File | Settings | File Templates.

            Log.e("onPostExecute", "1");
        }
    }
}
