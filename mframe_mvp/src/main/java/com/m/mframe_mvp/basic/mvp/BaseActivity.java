package com.m.mframe_mvp.basic.mvp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.m.mframe_mvp.basic.utils.ActivityManager;
import com.m.mframe_mvp.basic.utils.LoadingDialog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * createDate:2020/10/16
 *
 * @author:spc
 * @describe：
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    private Unbinder unbinder;
    protected Context mContext;
    protected P mPresenter;
    protected LoadingDialog loadingDialog;
    private List<WeakReference<Context>> weakReferenceList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        mContext = this;
        mPresenter = initPresenter();
        ActivityManager.getAppInstance().addActivity(this);
        onCreate();
    }

    protected abstract P initPresenter();

    protected abstract int getLayoutId();

    // TODO: 2020/10/17  
    protected abstract void onCreate();

    @Override
    public Context getContext() {
        if (weakReferenceList.size() == 0) {
            WeakReference<Context> weakReference = new WeakReference<>(mContext);
            weakReferenceList.add(weakReference);
            return weakReference.get();
        } else {
            WeakReference<Context> contextWeakReference = weakReferenceList.get(0);
            return contextWeakReference.get();
        }
    }

    public void showLoadingDialog(String msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.showDialog(msg);
        } else {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.showDialog(msg);
            loadingDialog.show();
        }
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.hideDialog();
            loadingDialog = null;
        }
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