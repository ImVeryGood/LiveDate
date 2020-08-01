package com.m.livedate.mvvm.basic.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.m.livedate.mvvm.basic.view.DialogBean;
import com.m.livedate.mvvm.basic.view.LoadingDialog;
import com.m.livedate.utils.ActivityManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public abstract class BaseActivity<VM extends BaseViewModel, DB extends ViewDataBinding> extends AppCompatActivity {
    protected VM mViewModel;
    protected DB dataBinding;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = initDataBinding(getContentViewId());
        dataBinding.setLifecycleOwner(this);
        ActivityManager.getAppInstance().addActivity(this);
        createViewModel();
        setDataBinding();
        initObserve();
        initData();
        initListener();
    }

    /**
     * 初始化DataBinding
     *
     * @param layoutId
     * @return
     */
    private DB initDataBinding(int layoutId) {
        return DataBindingUtil.setContentView(this, layoutId);
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
     * 用途DataBinding.setXX()
     */
    protected abstract void setDataBinding();

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

    /**
     * 监听当前ViewModel中 showDialog和error的值
     */
    private void initObserve() {
        if (mViewModel == null) return;
        mViewModel.getShowDialog(this, new Observer<DialogBean>() {
            @Override
            public void onChanged(DialogBean bean) {
                if (bean.isShow()) {
                    showDialog(bean.getMsg());
                } else {
                    dismissDialog();
                }
            }
        });
    }

    /**
     * 显示用户等待框
     *
     * @param msg 提示信息
     */
    protected void showDialog(String msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.setLoadingMsg(msg);
        } else {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setLoadingMsg(msg);
            loadingDialog.show();
        }
    }

    /**
     * 隐藏等待框
     */
    protected void dismissDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataBinding != null) {
            dataBinding.unbind();
        }
        ActivityManager.getAppInstance().removeActivity(this);
    }
}
