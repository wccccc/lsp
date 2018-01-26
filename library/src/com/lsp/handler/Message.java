package com.lsp.handler;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public final class Message implements Delayed {
    public int mWhat;
    public Object mObj;
    public Runnable mRun;
    private long mExcuteTime;//执行时间

    public Message(){}

    public Message(Runnable run,long delayTime){
        this(0,null,run,delayTime);
    }

    public Message(int what,Object obj,long delayTime){
        this(what,obj,null,delayTime);
    }

    private Message(int what,Object obj,Runnable run,long delayTime){
        this.mWhat=what;
        this.mObj=obj;
        this.mRun=run;
        setDelayTime(delayTime);
    }

    public void setDelayTime(long delayTime){
        this.mExcuteTime=TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.MILLISECONDS) + System.nanoTime();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.mExcuteTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if(o == null || ! (o instanceof Message)) return 1;
        Message msg= (Message) o;
        return this.getExcuteTime()>msg.getExcuteTime()? 1 : this.getExcuteTime()<msg.getExcuteTime()? -1 : 0;
    }

    public long getExcuteTime() {
        return mExcuteTime;
    }

    public static Message obtain(){
        // TODO maybe can use message pool.
        return new Message();
    }
}
