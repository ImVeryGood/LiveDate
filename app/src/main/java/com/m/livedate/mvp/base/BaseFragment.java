package com.m.livedate.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * createDate:2020/8/6
 *
 * @author:spc
 * @describe： baseFragment
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {
    protected T mPresenter;
    protected Context mContext;
    protected Unbinder unbinder;
    protected View view;
    protected Bundle mBundle;


    public BaseFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mBundle = savedInstanceState.getBundle("bundle");
        } else {
            mBundle = getArguments() == null ? new Bundle() : getArguments();
        }
        mPresenter = initPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    protected T getPresenter() {
        return mPresenter;
    }

    protected abstract int getLayoutId();

    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract T initPresenter();

    /**
     * 得到context
     *
     * @return context
     */
    @Override
    public Context getContext() {
        return mContext;
    }

    /**
     * fragment进行回退
     * 类似于activity的OnBackPress
     */
    public void onBack() {
        getFragmentManager().popBackStack();
    }


    /**
     * 得到bundle
     *
     * @return bundle
     */
    public Bundle getBundle() {
        return mBundle;
    }

    /**
     * 得到fragment
     *
     * @return fragment
     */
    public Fragment getFragment() {
        return this;
    }

    /**
     * 跳转Fragment
     *
     * @param toFragment 跳转到的fragment
     * @param tag        fragment的标签
     */
    public void startFragment(Fragment toFragment, String tag) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.hide(this).add(android.R.id.content, toFragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 数据恢复
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mBundle != null) {
            outState.putBundle("bundle", mBundle);
        }
    }

    /**
     * 绑定activity
     *
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
