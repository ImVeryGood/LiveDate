package com.m.livedate.mvp.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.m.livedate.mvvm.basic.view.LoadingDialog;
import com.m.livedate.utils.ActivityManager;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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
    private LoadingDialog loadingDialog;
    private List<WeakReference<Context>> mWeakReferenceList = new ArrayList<>();

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

    public void showLoadingDialog(String msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.setLoadingMsg(msg);
        } else {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setLoadingMsg(msg);
            loadingDialog.show();
        }
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    @Override
    public Context getContext() {
        if (mWeakReferenceList.size() == 0) {
            WeakReference<Context> weakReference = new WeakReference<>(mContext);
            mWeakReferenceList.add(weakReference);
            return weakReference.get();
        } else {
            WeakReference<Context> weakReference = mWeakReferenceList.get(0);
            return weakReference.get();
        }
    }

    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(FragmentEvent event) {
        return null;
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
