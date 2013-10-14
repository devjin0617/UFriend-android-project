package com.mobile.UFriend;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



        new MainAsync().execute("");


    }

    public class MainAsync extends AsyncTask <String, String, String>
    {
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
