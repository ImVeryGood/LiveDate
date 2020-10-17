package com.m.mframe_mvp.demo;

import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.m.mframe_mvp.R;
import com.m.mframe_mvp.basic.mvp.BaseFragment;
import com.m.mframe_mvp.demo.p.MainPresenter;
import com.m.mframe_mvp.demo.v.MainView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BlankFragment extends BaseFragment<MainPresenter> implements MainView {


    @BindView(R.id.text)
    TextView text;

    @Override
    protected int geLayoutId() {
        return R.layout.fragment_blank;
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onCreate() {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void setArticle(ArticleBean article) {
        text.setText(new Gson().toJson(article));
    }

    @OnClick(R.id.text)
    public void onViewClicked() {
        showLoadingDialog("加载...");
        mPresenter.getData(loadingDialog);
    }
}