package com.m.livedate.mvp.ui;

import android.widget.TextView;

import com.google.gson.Gson;
import com.m.livedate.R;
import com.m.livedate.mvp.base.BaseActivity;
import com.m.livedate.mvp.ui.bean.ArticleBean;
import com.m.livedate.mvp.ui.presenter.MPresenter;
import com.m.livedate.mvp.ui.view.MView;

import butterknife.BindView;

public class MVpActivity extends BaseActivity<MPresenter> implements MView {

    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_m_vp;
    }

    @Override
    public MPresenter initPresenter() {
        return new MPresenter(this);
    }

    @Override
    protected void initViews() {

    }


    @Override
    public void showArticle(ArticleBean articleBean) {
        text.setText(new Gson().toJson(articleBean));
    }


}