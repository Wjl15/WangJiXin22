package com.wangjixin22.wangjixin.lect10Net;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HttpProxy {
    private  static  final  String TAG=  "HttpProxy";
    private  static  final  HttpProxy instance=new HttpProxy();
    private HttpProxy(){

    }
    public  static  HttpProxy getInstance(){
        return  instance;
    }

    public void load(final String urlStr, final NetInputCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String raUrl = "http://ramedia.sinaapp.com/videolist.json";
                String imgUrl = "http://ramedia.sinaapp.com/res/Video/BigWeddingDay.png";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            Log.i(TAG, "---responseCode:" + responseCode);
            if (200 == responseCode) {
                InputStream inputStream = urlConnection.getInputStream();
                callback.onSuccess(inputStream);
                inputStream.close();
            }
        } catch (ProtocolException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}).start();
}
 public  interface NetInputCallback{

     void onSuccess(InputStream inputStream);
 }
}

