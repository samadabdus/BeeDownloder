package com.shootbee.tamada.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.shootbee.tamada.R;
import com.shootbee.tamada.databinding.ActivityTiktokwebBinding;

import static com.shootbee.tamada.util.Utils.RootDirectoryTikTok;
import static com.shootbee.tamada.util.Utils.TikTokWebUrl;
import static com.shootbee.tamada.util.Utils.startDownload;

public class TikTokWebActivity extends AppCompatActivity {
    ActivityTiktokwebBinding binding;
    TikTokWebActivity activity;

    private static ValueCallback<Uri[]> mUploadMessageArr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tiktokweb);
        activity = this;
        binding.imBack.setOnClickListener(view -> onBackPressed());
        binding.TVTitle.setText(activity.getResources().getString(R.string.tiktok_app_name));

        if (Build.VERSION.SDK_INT >= 24) {
            onstart();
            
            
            binding.webTikTok.clearFormData();
            binding.webTikTok.getSettings().setSaveFormData(true);
            binding.webTikTok.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
            binding.webTikTok.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
            binding.webTikTok.setWebViewClient(new MyBrowser());

            binding.webTikTok.getSettings().setAllowFileAccess(true);
            binding.webTikTok.getSettings().setAppCacheEnabled(true);
            binding.webTikTok.getSettings().setJavaScriptEnabled(true);
            binding.webTikTok.getSettings().setDefaultTextEncodingName("UTF-8");
            binding.webTikTok.getSettings().setCacheMode(1);
            binding.webTikTok.getSettings().setDatabaseEnabled(true);
            binding.webTikTok.getSettings().setBuiltInZoomControls(false);
            binding.webTikTok.getSettings().setSupportZoom(true);
            binding.webTikTok.getSettings().setUseWideViewPort(true);
            binding.webTikTok.getSettings().setDomStorageEnabled(true);
            binding.webTikTok.getSettings().setAllowFileAccess(true);
            binding.webTikTok.getSettings().setLoadWithOverviewMode(true);
            binding.webTikTok.getSettings().setLoadsImagesAutomatically(true);
            binding.webTikTok.getSettings().setBlockNetworkImage(false);
            binding.webTikTok.getSettings().setBlockNetworkLoads(false);
            binding.webTikTok.getSettings().setLoadWithOverviewMode(true);
            binding.webTikTok.setDownloadListener(new DownloadListener() {
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {


                    startDownload(url,
                        RootDirectoryTikTok, activity, "tiktok_" + System.currentTimeMillis() + ".mp4");
                }
            });
            binding.webTikTok.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    if (progress < 100 && binding.progressBar.getVisibility() == 8) {
                        binding.progressBar.setVisibility(0);
                    }
                    binding.progressBar.setProgress(progress);
                    if (progress == 100) {
                        binding.progressBar.setVisibility(8);
                    }
                }
            });
            binding.webTikTok.loadUrl(TikTokWebUrl);
            return;
        }
        onstart();


        binding.webTikTok.clearFormData();
        binding.webTikTok.getSettings().setSaveFormData(true);
        binding.webTikTok.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
        binding.webTikTok.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        binding.webTikTok.setWebViewClient(new MyBrowser());
        binding.webTikTok.getSettings().setAllowFileAccess(true);
        binding.webTikTok.getSettings().setAppCacheEnabled(true);
        binding.webTikTok.getSettings().setJavaScriptEnabled(true);
        binding.webTikTok.getSettings().setDefaultTextEncodingName("UTF-8");
        binding.webTikTok.getSettings().setCacheMode(1);
        binding.webTikTok.getSettings().setDatabaseEnabled(true);
        binding.webTikTok.getSettings().setBuiltInZoomControls(false);
        binding.webTikTok.getSettings().setSupportZoom(false);
        binding.webTikTok.getSettings().setUseWideViewPort(true);
        binding.webTikTok.getSettings().setDomStorageEnabled(true);
        binding.webTikTok.getSettings().setAllowFileAccess(true);
        binding.webTikTok.getSettings().setLoadWithOverviewMode(true);
        binding.webTikTok.getSettings().setLoadsImagesAutomatically(true);
        binding.webTikTok.getSettings().setBlockNetworkImage(false);
        binding.webTikTok.getSettings().setBlockNetworkLoads(false);
        binding.webTikTok.getSettings().setLoadWithOverviewMode(true);
        binding.webTikTok.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                startDownload(url,
                        RootDirectoryTikTok, activity, "tiktok_" + System.currentTimeMillis() + ".mp4");
            }
        });
        binding.webTikTok.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && binding.progressBar.getVisibility() == 8) {
                    binding.progressBar.setVisibility(0);
                }
                binding.progressBar.setProgress(progress);
                if (progress == 100) {
                    binding.progressBar.setVisibility(8);
                }
            }
        });
        binding.webTikTok.loadUrl(TikTokWebUrl);
    }

    public void onstart() {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE", "android.permission.ACCESS_COARSE_LOCATION"}, 123);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1001 && Build.VERSION.SDK_INT >= 21) {
            mUploadMessageArr.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(i2, intent));
            mUploadMessageArr = null;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            try {
                if (binding.webTikTok.canGoBack()) {
                    binding.webTikTok.goBack();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (binding.webTikTok.canGoBack()) {
            binding.webTikTok.goBack();
        } else {
            super.onBackPressed();
        }
    }


    private class MyBrowser extends WebViewClient {
        private MyBrowser() {
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            binding.progressBar.setVisibility(0);

            super.onPageStarted(view, url, favicon);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String request) {
            view.loadUrl(request);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            binding.progressBar.setVisibility(8);
        }
    }

}