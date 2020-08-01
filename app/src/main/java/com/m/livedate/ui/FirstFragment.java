package com.m.livedate.ui;


import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m.livedate.R;
import com.m.livedate.basic.adapter.OnItemClickListener;
import com.m.livedate.basic.base.BaseFragment;
import com.m.livedate.databinding.FragmentFirstragmentBinding;
import com.m.livedate.basic.retrofit.ApiResponse;
import com.m.livedate.ui.adapter.FirstFragmentAdapter;
import com.m.livedate.ui.bean.ListBean;
import com.m.livedate.ui.model.MViewModel;
import com.m.livedate.ui.model.SecondeViewModel;
import com.m.livedate.utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends BaseFragment<MViewModel, FragmentFirstragmentBinding> {

    private RecyclerView recyclerView;
    private FirstFragmentAdapter adapter;
    private SecondeViewModel secondeViewModel;

    @Override
    protected void setDataBinding() {

    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_firstragment;
    }

    @Override
    protected void initData() {
        mViewModel.getData();
        recyclerView = dataBinding.rv;
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        adapter = new FirstFragmentAdapter();
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        secondeViewModel = new ViewModelProvider(requireActivity()).get(SecondeViewModel.class);

    }

    @Override
    protected void initListener() {
        mViewModel.getListBeanData().observe(getViewLifecycleOwner(), new Observer<ApiResponse<List<ListBean.DataBean>>>() {
            @Override
            public void onChanged(ApiResponse<List<ListBean.DataBean>> listApiResponse) {
                if (listApiResponse != null){
                    adapter.setNewData(listApiResponse.getData());
                }

            }
        });
        adapter.setOnItemListener(new OnItemClickListener<ListBean.DataBean>() {
            @Override
            public void onItemClickListener(ListBean.DataBean dataBean, int position) {
                ToastUtils.showShortToast(dataBean.getName());
            }

            @Override
            public boolean onItemLongClick(ListBean.DataBean dataBean, int position) {
                return false;
            }
        });

    }

}
