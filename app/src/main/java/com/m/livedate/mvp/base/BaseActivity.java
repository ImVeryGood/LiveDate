package com.m.livedate.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.m.livedate.utils.ActivityManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * createDate:2020/8/6
 *
 * @author:spc
 * @describe：baseActivity 基类
 */
public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity implements BaseView {
    private Unbinder unbinder;
    protected Context mContext;
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        mContext = this;
        mPresenter = initPresenter();
        ActivityManager.getAppInstance().addActivity(this);

        onCreate();
        initPresenter();
        initViews();
    }

    protected abstract void onCreate();

    /**
     * 布局id
     *
     * @return layoutId
     */
    protected abstract int getLayoutId();

    /**
     * 实例化presenter
     */
    public abstract P initPresenter();

    /**
     * 初始化控件
     */
    protected abstract void initViews();

    @Override
    public Context mContext() {
        return mContext;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (mPresenter != null) {
            mPresenter.detach();
            mPresenter = null;
        }
        ActivityManager.getAppInstance().removeActivity(this);
    }
}
