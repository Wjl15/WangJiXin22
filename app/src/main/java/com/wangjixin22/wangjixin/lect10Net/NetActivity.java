package com.wangjixin22.wangjixin.lect10Net;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.wangjixin22.wangjixin.R;
import com.wangjixin22.wangjixin.lect09SeniorView.NetInputUtils;
import com.wangjixin22.wangjixin.lect10Net.bean.VideoListResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class NetActivity extends AppCompatActivity {
    private static final String TAG = NetActivity.class.getSimpleName();
    private ImageView mLlogoIv;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        mLlogoIv =findViewById(R.id.img_icon);
        mHandler=new Handler();

    }
        public void toJsonClick (View view){
            String json = "{\n" +
                    "      \"result\":\"0\",\n" +
                    "       \"list\":[\n" +
                    "          {\n" +
                    "              \"title\":\"Big Wedding Day\",\n" +
                    "               \"filePath\":\"http://ramedia.sinaapp.com/res/Video/BigWeddingDay.hlv\",\n" +
                    "                  \"thumbPath\":\"http://ramedia.sinaapp.com/res/Video/BigWeddingDay.png\",\n" +
                    "                   \"id\":2\n" +
                    "       }\n" +
                    "        ]\n" +
                    "}";
            VideoListResponse videoListResponse = convertJsonToBean(json);
        }
        private VideoListResponse convertJsonToBean (String json){
            Gson gson = new Gson();
            VideoListResponse response = gson.fromJson(json, VideoListResponse.class);
            return response;
        }


    public void toRequestNet(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String raUrl = "http://ramedia.sinaapp.com/videolist.json";
                String imgUrl = "http://ramedia.sinaapp.com/res/GetColdFeet.png";
                String movieUrl="http://ramedia.sinaapp.com/res/DouBanMovie.json";
                try {
                    URL url = new URL(movieUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setReadTimeout(10000);
                    urlConnection.connect();
                    int responseCode = urlConnection.getResponseCode();
                    Log.i(TAG, "---responseCode:" + responseCode);
                    if (200 == responseCode) {
                        InputStream inputStream = urlConnection.getInputStream();
                        final Bitmap bitmap= NetInputUtils.readImg(inputStream);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mLlogoIv.setImageBitmap(bitmap);
                            }
                        });
                        inputStream.close();
                    }
                } catch (ProtocolException ex) {
                    ex.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }}