package com.lsp;

import com.lsp.handler.HandlerThread;
import com.lsp.util.Log;

public class Main {
    private static final String TAG="Main";
    public static void main(String arg[]){
//        new RtspClient("rtsp://124.75.34.37/PLTV/88888888/224/3221226313/10000100000000060000000001865656_0.smil");
        HandlerThread thread =new HandlerThread();
        thread.start();
        thread.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: 777");
            }
        },5000);
        thread.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: 666");
            }
        });
        try {
            Thread.sleep(10000);
            thread.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

