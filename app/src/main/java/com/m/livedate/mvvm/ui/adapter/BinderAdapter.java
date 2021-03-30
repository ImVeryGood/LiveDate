package com.m.livedate.mvvm.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseBinderAdapter;
import com.chad.library.adapter.base.binder.QuickDataBindingItemBinder;
import com.m.livedate.R;
import com.m.livedate.databinding.ImageLayoutBinding;
import com.m.livedate.databinding.RecyclerItemBinding;
import com.m.livedate.mvvm.ui.bean.ImageBen;
import com.m.livedate.mvvm.ui.bean.ListBean;
import com.m.livedate.utils.ToastUtils;

import org.jetbrains.annotations.NotNull;

/**
 * createDate:2020/8/25
 *
 * @author:spc
 * @describe：
 */
public class BinderAdapter extends BaseBinderAdapter {
    public BinderAdapter() {
        addItemBinder(ListBean.DataBean.class, new RecycleBinder());
        addItemBinder(ImageBen.class, new ImageBinder());

    }


    class RecycleBinder extends QuickDataBindingItemBinder<ListBean.DataBean, RecyclerItemBinding> {

        @NotNull
        @Override
        public RecyclerItemBinding onCreateDataBinding(@NotNull LayoutInflater layoutInflater, @NotNull ViewGroup viewGroup, int i) {
            return RecyclerItemBinding.inflate(layoutInflater, viewGroup, false);
        }

        @Override
        public void convert(@NotNull BinderDataBindingHolder<RecyclerItemBinding> holder, ListBean.DataBean dataBean) {
            holder.getDataBinding().setDataBean(dataBean);
        }

        @Override
        public void onClick(@NotNull BinderDataBindingHolder<RecyclerItemBinding> holder, @NotNull View view, ListBean.DataBean data, int position) {
            super.onClick(holder, view, data, position);
            ToastUtils.showShortToast(position+"");
        }

        @Override
        public void onChildClick(@NotNull BinderDataBindingHolder<RecyclerItemBinding> holder, @NotNull View view, ListBean.DataBean data, int position) {
            super.onChildClick(holder, view, data, position);

        }
    }

    class ImageBinder extends QuickDataBindingItemBinder<ImageBen, ImageLayoutBinding> {

        @NotNull
        @Override
        public ImageLayoutBinding onCreateDataBinding(@NotNull LayoutInflater layoutInflater, @NotNull ViewGroup viewGroup, int i) {
            return ImageLayoutBinding.inflate(layoutInflater, viewGroup, false);
        }

        @Override
        public void convert(@NotNull BinderDataBindingHolder<ImageLayoutBinding> holder, ImageBen imageBen) {
            holder.getDataBinding().setImageBean(imageBen);
        }
    }
}
