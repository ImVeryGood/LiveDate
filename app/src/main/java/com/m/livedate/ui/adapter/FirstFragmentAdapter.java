package com.m.livedate.ui.adapter;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;

import com.m.livedate.R;
import com.m.livedate.basic.adapter.BaseDBRVAdapter;
import com.m.livedate.basic.adapter.BaseDBRVHolder;
import com.m.livedate.databinding.RecyclerItemBinding;
import com.m.livedate.ui.bean.ListBean;

/**
 * createDate:2020/7/15
 *
 * @author:spc describe：
 */
public class FirstFragmentAdapter extends BaseDBRVAdapter<ListBean.DataBean, RecyclerItemBinding> {
    public FirstFragmentAdapter() {
        super(R.layout.recycler_item, BR.dataBean);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public BaseDBRVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void onBindViewHolder(ListBean.DataBean dataBean, RecyclerItemBinding binding, int position) {
        super.onBindViewHolder(dataBean, binding, position);
    }
}
