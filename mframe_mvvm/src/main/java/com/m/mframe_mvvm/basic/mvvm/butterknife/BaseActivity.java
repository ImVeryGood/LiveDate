package com.m.mframe_mvvm.basic.mvvm.butterknife;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;


import com.m.mframe_mvvm.basic.utils.ActivityManager;
import com.m.mframe_mvvm.basic.utils.LoadingDialog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity {
    protected VM mViewModel;
    private Unbinder unbinder;
    protected LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        unbinder = ButterKnife.bind(this);
        ActivityManager.getAppInstance().addActivity(this);
        createViewModel();
        initData();
        initListener();
    }


    private void createViewModel() {
        if (mViewModel == null) {
            Class modelClass = null;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            mViewModel = (VM) new ViewModelProvider(this).get(modelClass);
        }
    }

    protected final VM getVM() {
        return mViewModel;
    }

    /**
     * 布局id抽象方法
     *
     * @return 页面布局id
     */
    protected abstract int getContentViewId();


    /**
     * 初始化组件及数据
     */
    protected abstract void initData();

    /**
     * 触摸等事件的监听写在该方法内
     */
    protected abstract void initListener();

    /**
     * 直接启动页面
     *
     * @param clazz
     */
    public void startActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
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
        ActivityManager.getAppInstance().removeActivity(this);
    }
}
