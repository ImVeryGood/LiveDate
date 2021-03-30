package com.m.livedate.mvvm.ui.adapter;

import com.chad.library.adapter.base.BaseBinderAdapter;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseProviderMultiAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.m.livedate.R;
import com.m.livedate.databinding.RecyclerItemBinding;
import com.m.livedate.mvvm.ui.bean.ListBean.DataBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * createDate:2020/8/24
 *
 * @author:spc
 * @describe：
 */
public class QuitAdapter extends BaseProviderMultiAdapter<ProviderMultiEntity> {
    public QuitAdapter() {

    }

    @Override
    protected int getItemType(@NotNull List<? extends ProviderMultiEntity> list, int i) {
        switch (i){

        }
        return 0;
    }
}
