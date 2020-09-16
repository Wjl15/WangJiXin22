package com.wangjixin22.wangjixin.lect10Net.list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.wangjixin22.wangjixin.R;
import com.wangjixin22.wangjixin.lect09SeniorView.NetInputUtils;
import com.wangjixin22.wangjixin.lect10Net.HttpProxy;
import com.wangjixin22.wangjixin.lect10Net.SignActivity;
import com.wangjixin22.wangjixin.lect10Net.bean.VideoInfo;
import com.wangjixin22.wangjixin.lect10Net.bean.VideoListResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NetListActivity extends AppCompatActivity {
    private static final String TAG = "NetListActivity";
    private static final int RequestCode = 10;
    private VideoAdapter mAdapter;
    private Handler mHandler = new Handler();
    private List<VideoInfo> mDataList;
    private  TextView mTextName;
    private  TextView mSigm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_list);
        ListView mListView = findViewById(R.id.lv);

        View headLayout = buildLsitHeader();
        mListView.addHeaderView(headLayout);
        mTextName=headLayout.findViewById(R.id.txt_name);
        mSigm=headLayout.findViewById(R.id.txt_sign);
        mDataList = new ArrayList<>();
        mAdapter = new VideoAdapter(mDataList, this);
        mListView.setAdapter(mAdapter);
        initData();
    }

    private View buildLsitHeader() {
        View headLayout = LayoutInflater.from(this).inflate(R.layout.layout_header, null);
        TextView nameTV = headLayout.findViewById(R.id.txt_sign);

        nameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NetListActivity.this, "去设置签名", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NetListActivity.this, SignActivity.class);
                startActivityForResult(intent, RequestCode);
            }
        });

        return headLayout;
    }


    private void initData() {
        String raUrl = "http://ramedia.sinaapp.com/videolist.json";
        String movieUrl="http://ramedia.sinaapp.com/res/DouBanMovie2.json";
        HttpProxy.getInstance().load(movieUrl, new HttpProxy.NetInputCallback() {
            @Override
            public void onSuccess(InputStream inputStream) {
                String respJson = null;
                try {
                    respJson = NetInputUtils.readString(inputStream);
                    Log.i(TAG, "---response json:\n" + respJson);
                    VideoListResponse videoListResponse = conVertJsonToBean(respJson);
                    final List<VideoInfo> list = videoListResponse.getList();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setData(list);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Intent intent=getIntent();
        mTextName.setText(intent.getStringExtra("loginName"));
    }
            private VideoListResponse conVertJsonToBean(String json) {
                Gson gson=new Gson();
                VideoListResponse response=gson.fromJson(json, VideoListResponse.class);
                return  response;
            }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RequestCode: {
                if (RESULT_OK == resultCode) {
                    String sign = data.getStringExtra("mySign");
                    mSigm.setText(sign);
                } else {
                    Toast.makeText(this, "魏金龙  取消了设置", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    }
