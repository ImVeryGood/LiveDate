package com.m.livedate.mvvm.ui;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.m.livedate.R;
import com.m.livedate.mvvm.basic.base.BaseFragment;
import com.m.livedate.mvvm.basic.retrofit.ApiResponse;
import com.m.livedate.databinding.FragmentSecondBinding;
import com.m.livedate.mvvm.ui.adapter.SecondeNormalAdapter;
import com.m.livedate.mvvm.ui.bean.PagingBean;
import com.m.livedate.mvvm.ui.model.SecondeViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends BaseFragment<SecondeViewModel, FragmentSecondBinding> {
    private String title;
    private SecondeNormalAdapter secondeNormalAdapter;

    public static SecondFragment getInstance(String title) {
        SecondFragment sf = new SecondFragment();
        sf.title = title;
        return sf;
    }

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
        secondeNormalAdapter = new SecondeNormalAdapter();
        dataBinding.rv.setAdapter(secondeNormalAdapter);
        dataBinding.rv.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    @Override
    protected void lazyLoad() {
        mViewModel.getPageData(false, getActivity());
    }

    @Override
    protected void initListener() {
        mViewModel.returnPageData().observe(this, new Observer<ApiResponse<PagingBean.DataBean>>() {
            @Override
            public void onChanged(ApiResponse<PagingBean.DataBean> dataBeanApiResponse) {
                RefreshState refreshState = dataBinding.refresh.getState();
                if (refreshState.isFooter && refreshState.isOpening) {
                    secondeNormalAdapter.addData(dataBeanApiResponse.getData().getDatas());
                    dataBinding.refresh.finishLoadMore();
                } else if (refreshState.isHeader && refreshState.isOpening) {
                    secondeNormalAdapter.setNewData(dataBeanApiResponse.getData().getDatas());
                    dataBinding.refresh.finishRefresh();
                } else {
                    if (dataBeanApiResponse != null) {
                        secondeNormalAdapter.setNewData(dataBeanApiResponse.getData().getDatas());
                    }

                }
            }
        });
//        dataBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                mViewModel.getPageData(false, getActivity());
//
//            }
//        });
//        dataBinding.refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                mViewModel.getPageData(true, getActivity());
//
//            }
//        });
    }

}
