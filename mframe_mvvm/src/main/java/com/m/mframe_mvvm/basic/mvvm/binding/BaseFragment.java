package com.m.mframe_mvvm.basic.mvvm.binding;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.m.mframe_mvvm.basic.mvvm.binding.view.DialogBean;
import com.m.mframe_mvvm.basic.mvvm.binding.view.LoadingDialog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment<VM extends BaseViewModel, DB extends ViewDataBinding> extends Fragment {
    protected VM mViewModel;
    protected Context mContext;
    protected DB dataBinding;
    private LoadingDialog loadingDialog;
    /**
     * 是否初始化过布局
     */
    protected boolean isViewInitiated;
    /**
     * 当前界面是否可见
     */
    protected boolean isVisibleToUser;
    /**
     * 是否加载过数据
     */
    protected boolean isDataInitiated;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataBinding = initDataBinding(inflater, getFragmentLayoutId(), container);
        dataBinding.setLifecycleOwner(this);
        createViewModel();
        setDataBinding();
        initData();
        initListener();
        initObserve();
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();

    }

    /**
     * 初始化DataBinding
     */
    protected DB initDataBinding(LayoutInflater inflater, @LayoutRes int layoutId, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, layoutId, container, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            prepareFetchData();
        }
    }

    public void prepareFetchData() {
        prepareFetchData(false);
    }

    /**
     * 判断懒加载条件
     *
     * @param forceUpdate 强制更新，好像没什么用？
     */
    public void prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            lazyLoad();
            isDataInitiated = true;
        }
    }

    protected abstract void lazyLoad();

    /**
     * 用途DataBinding.setXX()
     */
    protected abstract void setDataBinding();

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

            mViewModel = (VM) new ViewModelProvider(requireActivity()).get(modelClass);
        }
    }

    protected final VM getVM() {
        return mViewModel;
    }

    /**
     * 布局
     *
     * @return
     */
    protected abstract int getFragmentLayoutId();

    /**
     * 数据初始化
     */
    protected abstract void initData();

    /**
     * 监听
     */
    protected abstract void initListener();

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
            loadingDialog = new LoadingDialog(mContext);
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
    public void onDestroy() {
        super.onDestroy();
        if (dataBinding != null) {
            dataBinding.unbind();
        }
    }
}
