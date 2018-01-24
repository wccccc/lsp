package com.lsp.handler;

import com.lsp.util.Log;

import java.util.concurrent.DelayQueue;

public class HandlerThread {
    private static final String TAG="HandlerThread";
    private Handler mHandler;
    private boolean mIsStop;
    private DelayQueue<Message> mMessageQueue;
    public HandlerThread(){
        mIsStop =true;
        mMessageQueue=new DelayQueue<>();
    }
    public synchronized void loop(){
        if(!mIsStop){return;}
        mIsStop =false;
        new Thread(){
            @Override
            public void run() {
                while(!mIsStop){
                    try {
                        Message msg=mMessageQueue.take();
                        if(msg.mRun!=null){
                            msg.mRun.run();
                        }else if(mHandler!=null){
                            mHandler.handleMessage(msg);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public synchronized void stop(){
        mIsStop =true;
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    public void putMessage(Message msg){
        boolean success=mMessageQueue.offer(msg);
        Log.d(TAG,"put msg :"+success);
    }
}
