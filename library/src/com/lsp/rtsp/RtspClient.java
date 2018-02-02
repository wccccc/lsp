package com.lsp.rtsp;

import com.lsp.util.Log;

import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RtspClient {
    private static final String TAG="RtspClient";
    private String mHost;
    private int mPort;
    private String mUri;
    private int mCSeq;
    private Socket mRtspSocket;
    private BufferedInputStream mReader;
    private BufferedWriter mWriter;
    private String mUserAgent;
    public RtspClient(String url) {
        URI uri = URI.create(url);
        mUri=url;
        mHost = uri.getHost();
        mPort = uri.getPort()==-1 ? 554 : uri.getPort();
        mCSeq=1;
        mUserAgent="ProxyRTSPClient (LIVE555 Streaming Media v2015.11.09)";
        Log.d(TAG,"mHost:" + mHost);
        Log.d(TAG,"mPort:"+mPort);

    }

    private void connect() throws IOException {
        if(mRtspSocket ==null){
            if(mHost==null||mPort==0){
                throw new IOException("the host or post are not found!");
            }
            mRtspSocket =new Socket(mHost,mPort);
            mReader=new BufferedInputStream(mRtspSocket.getInputStream());
            mWriter=new BufferedWriter(new OutputStreamWriter(mRtspSocket.getOutputStream()));
        }
    }

    private String buildRequest(String method,String uri,Map<String,String> header){
        StringBuilder request=new StringBuilder(method);
        request.append(" ");
        request.append(uri);
        request.append(" RTSP/1.0\r\n");
        Set<String> keySet=header.keySet();
        for(String key:keySet){
            request.append(key);
            request.append(": ");
            request.append(header.get(key));
            request.append("\r\n");
        }
        request.append("\r\n");
        return request.toString();
    }

    private void sendRequestOptions(){
        int seq=mCSeq++;
        HashMap<String,String> header=new HashMap<>();
        header.put("User-Agent",mUserAgent);
    }
}
