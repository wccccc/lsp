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
    public synchronized void start(){
        if(!mIsStop){return;}
        mIsStop =false;
        new Thread(){
            @Override
            public void run() {
                while(!mIsStop){
                    try {
                        Message msg=mMessageQueue.take();
                        Log.d(TAG, "take a msg:"+msg);
                        if(msg==null){

                        }else if(msg.mRun!=null){
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
        if(!mIsStop){
            mIsStop =true;
            post(new Runnable() {
                @Override
                public void run() {
                    // do nothing
                }
            });
        }
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    public void sendMessage(Message msg){
        mMessageQueue.offer(msg);
    }

    public void postDelayed(Runnable run,long delayTime){
        Message msg=Message.obtain();
        msg.mRun=run;
        msg.setDelayTime(delayTime);
        sendMessage(msg);
    }

    public void post(Runnable run){
        postDelayed(run,0);
    }

    public void sendMessageDelayed(int what,Object obj,long delayTime){
        Message msg=Message.obtain();
        msg.mWhat=what;
        msg.mObj=obj;
        msg.setDelayTime(delayTime);
        sendMessage(msg);
    }

    public void sendMessageDelayed(int what,long delayTime){
        sendMessageDelayed(what,null,delayTime);
    }

    public void sendMessage(int what){
        sendMessageDelayed(what,0);
    }

    public void sendMessage(int what,Object obj){
        sendMessageDelayed(what,obj,0);
    }
}
