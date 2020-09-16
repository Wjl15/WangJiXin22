package com.wangjixin22.wangjixin.lect10Net;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.wangjixin22.wangjixin.lect09SeniorView.NetInputUtils;

import java.io.InputStream;


public class ImageLoader {
    private static final  ImageLoader instance=new ImageLoader();
    private Handler mHandler;
    private  ImageLoader(){
        mHandler=new Handler(Looper.getMainLooper());
        }
        public  static  ImageLoader getInstance(){
        return instance;
    }
    public void load(final ImageView iconIv, final String thumbPath){
        iconIv.setTag(thumbPath);
        iconIv.setBackgroundColor(Color.GRAY);
        HttpProxy.getInstance().load(thumbPath, new HttpProxy.NetInputCallback() {
            @Override
            public void onSuccess(InputStream inputStream) {
                final Bitmap bitmap= NetInputUtils.readImg(inputStream);
                mHandler.post(new Runnable(){
                    @Override
                    public void run(){
                        String tag=(String)iconIv.getTag();
                        if (tag.equals(thumbPath)) {
                            iconIv.setImageBitmap(bitmap);
                        }
                    }
                });
            }
        });
    }
}
