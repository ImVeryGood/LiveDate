package com.m.livedate.mvvm.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseBinderAdapter;
import com.chad.library.adapter.base.binder.QuickDataBindingItemBinder;
import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder;
import com.flyco.tablayout.SlidingScaleTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.tabs.TabLayout;
import com.m.livedate.R;
import com.m.livedate.databinding.FragmentFourBinding;
import com.m.livedate.databinding.ImageLayoutBinding;
import com.m.livedate.databinding.RecyclerItemBinding;
import com.m.livedate.mvvm.basic.base.BaseFragment;
import com.m.livedate.mvvm.basic.retrofit.ApiResponse;
import com.m.livedate.mvvm.ui.adapter.QuitAdapter;
import com.m.livedate.mvvm.ui.bean.ImageBen;
import com.m.livedate.mvvm.ui.bean.ListBean;
import com.m.livedate.mvvm.ui.model.MViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FourFragment extends BaseFragment<MViewModel, FragmentFourBinding> {
    private int[] colors = {
            Color.BLACK, Color.BLUE, Color.CYAN, Color.RED
    };
   private List<Fragment> fragments;
    private String[] titles = {
            "标题1", "标题2", "标题3","标题4"
    };

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void setDataBinding() {

    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_four;
    }

    @Override
    protected void initData() {
        TabLayout tabLayout = dataBinding.tabLayout;
        ViewPager viewPager = dataBinding.viewpager;
        viewPager.setAdapter(new MyViewPagerAdapter(getActivity().getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
         tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
             @Override
             public void onTabSelected(TabLayout.Tab tab) {

             }

             @Override
             public void onTabUnselected(TabLayout.Tab tab) {

             }

             @Override
             public void onTabReselected(TabLayout.Tab tab) {

             }
         });
           fragments=new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(SecondFragment.getInstance(titles[i]));
        }

    }

    @Override
    protected void initListener() {

    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position % titles.length];
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }
    }
}