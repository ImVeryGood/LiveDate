package com.m.mframe_mvvm.basic.mvvm.butterknife;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.m.mframe_mvvm.basic.utils.LoadingDialog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {
    protected VM mViewModel;
    protected Context mContext;
    private Unbinder unbinder;
    private LoadingDialog loadingDialog;
    private View view;
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
        view = inflater.inflate(getFragmentLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        createViewModel();
        initData();
        initListener();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();

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

    protected abstract void lazyLoad();

    /**
     * 监听
     */
    protected abstract void initListener();


    public void showLoadingDialog(String msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.showDialog(msg);
        } else {
            loadingDialog = new LoadingDialog(getActivity());
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
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
