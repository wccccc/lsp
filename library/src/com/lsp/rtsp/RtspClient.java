package com.lsp.rtsp;

import com.lsp.util.Log;

import java.net.URI;

public class RtspClient {
    private static final String TAG="RtspClient";
    private String mHost;
    private int mPort;
    public RtspClient(String url) {
        URI uri = URI.create(url);
        mHost = uri.getHost();
        mPort = uri.getPort()==-1 ? 554 : uri.getPort();
        Log.d(TAG,"mHost:" + mHost);
        Log.d(TAG,"mPort:"+mPort);

    }
}
