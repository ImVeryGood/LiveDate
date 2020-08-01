package com.m.livedate.mvvm.ui.adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.paging.PagedListAdapter;

import com.m.livedate.R;
import com.m.livedate.databinding.SecondFragmentItemBinding;
import com.m.livedate.mvvm.ui.bean.PagingBean;

import java.util.ArrayList;
import java.util.List;

/**
 * createDate:2020/7/31
 *
 * @author:spc describe：
 */
public class SecondFragmentAdapter extends PagedListAdapter<PagingBean.DataBean.DatasBean, SecondFragmentAdapter.PagedHolder> {
    private List<PagingBean.DataBean.DatasBean> beanList;

    public SecondFragmentAdapter() {
        super(new DiffUtil.ItemCallback<PagingBean.DataBean.DatasBean>() {
            @Override
            public boolean areItemsTheSame(@NonNull PagingBean.DataBean.DatasBean oldItem, @NonNull PagingBean.DataBean.DatasBean newItem) {
                return oldItem.getApkLink() == newItem.getApkLink();
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull PagingBean.DataBean.DatasBean oldItem, @NonNull PagingBean.DataBean.DatasBean newItem) {
                return oldItem.equals(newItem);
            }
        });
        beanList = new ArrayList<>();
    }

    @NonNull
    @Override
    public PagedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SecondFragmentItemBinding secondFragmentItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.second_fragment_item, parent, false);
        return new PagedHolder(secondFragmentItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PagedHolder holder, int position) {
        PagingBean.DataBean.DatasBean datasBean = getItem(position);
        holder.getBinding().setPageBean(datasBean);

    }

    public class PagedHolder extends RecyclerView.ViewHolder {
        SecondFragmentItemBinding secondFragmentItemBinding;

        public PagedHolder(SecondFragmentItemBinding secondFragmentItemBinding) {
            super(secondFragmentItemBinding.getRoot());
            secondFragmentItemBinding = secondFragmentItemBinding;
        }

        public SecondFragmentItemBinding getBinding() {
            return secondFragmentItemBinding;
        }
    }

}
