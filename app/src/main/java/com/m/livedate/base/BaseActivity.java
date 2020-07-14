package com.m.livedate.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.m.livedate.utils.ActivityManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity {
    private VM mViewModel;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        mUnbinder = ButterKnife.bind(this);
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
                //如果没有指定泛型，择默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            mViewModel = (VM) new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(modelClass);
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
        Log.d("SSSSSSSSSS", "startActivity: ");
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        ActivityManager.getAppInstance().removeActivity(this);
    }
}
