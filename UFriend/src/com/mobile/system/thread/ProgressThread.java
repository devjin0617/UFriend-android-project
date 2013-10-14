/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobile.system.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 *
 * @author Administrator
 */
public class ProgressThread extends Thread {

    Handler mHandler;
    int mState;
    int total;
    final static int STATE_DONE = 0;
    final static int STATE_RUNNING = 1;

    ProgressThread(Handler h) {
        mHandler = h;
    }

    //쓰레드가 실행되면서 할일 시키기.
    public void run() {

        mState = STATE_RUNNING;
        total = 0;

        while (mState == STATE_RUNNING) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
            Message msg = mHandler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("total", total);
            msg.setData(b);
            mHandler.sendMessage(msg);
            total++;
        }
    }

    public void setState(int state) {
        mState = state;
    }
}



