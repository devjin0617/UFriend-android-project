package com.mobile.UFriend;

import android.os.Bundle;
import android.widget.TextView;
import com.mobile.system.Lib.JinAsync;
import com.mobile.system.Lib.JinProgress;

public class MainActivity extends CommonUFriendActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        JinProgress.doBackgroundAndLoading(this, new JinAsync() {
            @Override
            public void doASyncData() {
                //To change body of implemented methods use File | Settings | File Templates.

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

            @Override
            public void doFinish() {
                //To change body of implemented methods use File | Settings | File Templates.

                TextView textView = (TextView)findViewById(R.id.test_textview);

                textView.setText("hihi");
            }
        }, "hihi");
    }


}
