package com.m.livedate.base;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m.livedate.R;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {
    private View view;
    private Unbinder unbinder;
    private VM mViewModel;
    protected Context mContext;

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
            mViewModel = (VM) new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(modelClass);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
