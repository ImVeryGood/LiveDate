package com.m.livedate.mvvm.ui.adapter;


import android.graphics.Color;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.m.livedate.BR;
import com.m.livedate.R;
import com.m.livedate.mvvm.basic.adapter.BaseDBRVAdapter;
import com.m.livedate.mvvm.basic.adapter.BaseDBRVHolder;
import com.m.livedate.databinding.RecyclerItemBinding;
import com.m.livedate.mvvm.ui.bean.ListBean;

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
        if (dataBean.isChecked()){
            binding.userName.setTextColor(Color.parseColor("#df212c"));
        }
        super.onBindViewHolder(dataBean, binding, position);
    }

    public interface setOnSelectClick{
        void selectClick(int pos);
    }
}
