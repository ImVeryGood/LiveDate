package com.m.livedate.mvvm.ui.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseBinderAdapter;
import com.chad.library.adapter.base.BaseDelegateMultiAdapter;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseProviderMultiAdapter;
import com.chad.library.adapter.base.binder.BaseItemBinder;
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.m.livedate.R;
import com.m.livedate.databinding.ImageLayoutBinding;
import com.m.livedate.databinding.RecyclerItemBinding;
import com.m.livedate.mvvm.ui.bean.ImageBen;
import com.m.livedate.mvvm.ui.bean.ListBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Observable;

/**
 * createDate:2020/8/24
 *
 * @author:spc
 * @describe：
 */
public class QuitMultiAdapter extends BaseDelegateMultiAdapter<Object, BaseViewHolder> {
    private Object item;
    public QuitMultiAdapter() {
        setMultiTypeDelegate(new BaseMultiTypeDelegate<Object>() {
            @Override
            public int getItemType(@NotNull List<? extends Object> list, int i) {
                item= list.get(i);
                switch (i) {
                    case 0:
                        return 0;
                    case 2:
                        return 2;
                }
                return 0;
            }
        });

        getMultiTypeDelegate()
                .addItemType(0, R.layout.recycler_item)
                .addItemType(2, R.layout.image_layout);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, Object bean) {
        switch (holder.getItemViewType()) {
            case 0:
                ListBean.DataBean dataBean= (ListBean.DataBean) item;
                RecyclerItemBinding binding = DataBindingUtil.bind(holder.itemView);
                binding.setDataBean(dataBean);
            case 2:
                ImageBen imageBen= (ImageBen) item;
                ImageLayoutBinding imageLayoutBinding = DataBindingUtil.bind(holder.itemView);
                imageLayoutBinding.setImageBean(imageBen);
                break;
        }

    }

}
