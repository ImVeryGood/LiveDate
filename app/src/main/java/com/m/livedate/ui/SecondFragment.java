package com.m.livedate.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m.livedate.R;
import com.m.livedate.basic.base.BaseFragment;
import com.m.livedate.basic.retrofit.ApiResponse;
import com.m.livedate.databinding.FragmentSecondBinding;
import com.m.livedate.ui.bean.ListBean;
import com.m.livedate.ui.model.MViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends BaseFragment<MViewModel, FragmentSecondBinding> {


    @Override
    protected void setDataBinding() {
        dataBinding.setMethod(this);
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_second;
    }

    @Override
    protected void initData() {

    }

    public void getData() {
        Log.d("SSSSSSSSS", "getData: ");
        mViewModel.getData();
    }

    @Override
    protected void initListener() {
        mViewModel.getListBeanData().observe(getViewLifecycleOwner(), new Observer<ApiResponse<List<ListBean.DataBean>>>() {
            @Override
            public void onChanged(ApiResponse<List<ListBean.DataBean>> listApiResponse) {
                Log.d("SSSSSSSSSSSSSSSSS", "onChanged: SecondFragment");
            }
        });

    }

}
