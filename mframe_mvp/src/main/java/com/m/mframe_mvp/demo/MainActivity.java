package com.m.mframe_mvp.demo;

import android.view.View;
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
    protected void onCreate() {
        blankFragment = new BlankFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, blankFragment)
                .show(blankFragment)
                .commit();

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
