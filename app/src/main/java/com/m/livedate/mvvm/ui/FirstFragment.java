package com.m.livedate.mvvm.ui;


import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m.livedate.R;
import com.m.livedate.mvvm.basic.adapter.OnItemClickListener;
import com.m.livedate.mvvm.basic.base.BaseFragment;
import com.m.livedate.databinding.FragmentFirstragmentBinding;
import com.m.livedate.mvvm.basic.retrofit.ApiResponse;
import com.m.livedate.mvvm.basic.view.LoadingLayout;
import com.m.livedate.mvvm.ui.adapter.FirstFragmentAdapter;
import com.m.livedate.mvvm.ui.bean.ListBean;
import com.m.livedate.mvvm.ui.model.MViewModel;
import com.m.livedate.mvvm.ui.model.SecondeViewModel;
import com.m.livedate.utils.ToastUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends BaseFragment<MViewModel, FragmentFirstragmentBinding> {

    private RecyclerView recyclerView;
    private FirstFragmentAdapter adapter;
    private SecondeViewModel secondeViewModel;
    private int selectIndex;
    private List<ListBean.DataBean> list;

    @Override
    protected void setDataBinding() {

    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_firstragment;
    }

    @Override
    protected void initData() {
        recyclerView = dataBinding.rv;
        mViewModel.getData();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        adapter = new FirstFragmentAdapter();
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        secondeViewModel = new ViewModelProvider(requireActivity()).get(SecondeViewModel.class);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    protected void lazyLoad() {
        mViewModel.getData();
    }

    @Override
    protected void initListener() {
        mViewModel.getListBeanData().observe(getViewLifecycleOwner(), new Observer<ApiResponse<List<ListBean.DataBean>>>() {
            @Override
            public void onChanged(ApiResponse<List<ListBean.DataBean>> listApiResponse) {
                if (listApiResponse != null) {
                    list = listApiResponse.getData();
                    adapter.setNewData(listApiResponse.getData());
                }

            }
        });
        adapter.setOnItemListener(new OnItemClickListener<ListBean.DataBean>() {
            @Override
            public void onItemClickListener(ListBean.DataBean dataBean, int position) {
                list.get(selectIndex).setChecked(false);
                dataBean.setChecked(true);
                adapter.notifyDataSetChanged();
                selectIndex = position;
            }

            @Override
            public boolean onItemLongClick(ListBean.DataBean dataBean, int position) {
                return false;
            }
        });

    }

}
