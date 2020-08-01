package com.m.livedate.ui;


import android.os.Handler;
import android.util.Log;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.m.livedate.R;
import com.m.livedate.basic.base.BaseActivity;
import com.m.livedate.bottombar.BottomBarItem;
import com.m.livedate.bottombar.BottomBarLayout;
import com.m.livedate.databinding.ActivityFragmentBinding;
import com.m.livedate.ui.model.MViewModel;
import com.m.livedate.ui.model.NulllViewModel;

import java.util.ArrayList;
import java.util.List;


public class FragmentActivity extends BaseActivity<NulllViewModel, ActivityFragmentBinding> implements BottomBarLayout.OnItemSelectedListener {

    private List<Fragment> fragments;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private int mIndex = 0;
    private Handler mHandler;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void setDataBinding() {

    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        fragments.add(firstFragment);
        fragments.add(secondFragment);
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, firstFragment)
                .show(firstFragment)
                .commit();
    }

    @Override
    protected void initListener() {
        dataBinding.bottomBarLayout.setOnItemSelectedListener(this);


    }

    public void setHandler(Handler handler) {
        mHandler = handler;
    }
    @Override
    public void onItemSelected(BottomBarItem bottomBarItem, int previousPosition, int currentPosition) {
        switch (currentPosition) {
            case 0:
                switchFragment(0);
                MViewModel model=new ViewModelProvider(this).get(MViewModel.class);
                break;
            case 1:
                switchFragment(1);
                if (mHandler!=null)
                mHandler.sendEmptyMessage(1);
                break;
            case 2:
                break;
            case 3:
                break;
        }

    }

    public void switchFragment(int currentIndex) {
        if (mIndex == currentIndex) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments.get(mIndex));
        if (fragments.get(currentIndex).isAdded()) {
            transaction.show(fragments.get(currentIndex));
        } else {
            transaction.add(R.id.frameLayout, fragments.get(currentIndex)).show(fragments.get(currentIndex));
        }
        transaction.commit();
        mIndex = currentIndex;
    }
}
