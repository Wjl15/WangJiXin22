package com.wangjixin22.wangjixin.lect10Net;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wangjixin22.wangjixin.R;

public class WebActivity extends AppCompatActivity implements View.OnClickListener{
    public  static  final String WEB_URL="webUrl";
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        TextView mTitleTv=findViewById(R.id.tv_title);
        View mBackLayout=findViewById(R.id.layout_back);
        mProgressBar=findViewById(R.id.progress_bar_h);
        WebView webView=findViewById(R.id.web_content);
        mTitleTv.setText("王霁昕");
        mBackLayout.setOnClickListener(this);
        String webUrl = getIntent().getStringExtra(WEB_URL);
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        MyChromeClient mChromClient=new MyChromeClient();
        webView.setWebChromeClient(mChromClient);
        webView.setWebViewClient(mWebClient);
        webView.loadUrl(webUrl);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back: {
                finish();
            }break;
            default:
                break;
        }
    }
    WebViewClient mWebClient=new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setVisibility(View.GONE);
        }
    };
    public class MyChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view,int newProgress){
            Log.i("MyChromeClient","---onProgressChanged:"+newProgress);
            mProgressBar.setProgress(newProgress);
        }
    }

}
