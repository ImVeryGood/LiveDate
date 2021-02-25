package com.m.mframe_mvp.demo;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.m.mframe_mvp.R;
import com.m.mframe_mvp.basic.mvp.BaseActivity;
import com.m.mframe_mvp.demo.p.MainPresenter;
import com.m.mframe_mvp.demo.v.MainView;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.webview)
    WebView webview;
    private BlankFragment blankFragment;

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SSSSSSSSSSSS", "onResume: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("SSSSSSSSSSSS", "onStart: ");
    }

    @Override
    protected void onCreate() {
        blankFragment = new BlankFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, blankFragment)
                .show(blankFragment)
                .commit();
        Log.d("SSSSSSSSSSSS", "onCreate: ");
        webview.loadUrl("http://shop.w888666.com:8000/pay/litebank/payInfo/2010201459181471820.html");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("SSSSSSSSSSSS", "onRestart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SSSSSSSSSSSS", "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("SSSSSSSSSSSS", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SSSSSSSSSSSS", "onDestroy: ");
    }

    public void getData(View view) {
        showLoadingDialog("加载中...");
        mPresenter.getData(loadingDialog);
    }

    @Override
    public void setArticle(ArticleBean article) {
        text.setText(new Gson().toJson(article));
    }
}
