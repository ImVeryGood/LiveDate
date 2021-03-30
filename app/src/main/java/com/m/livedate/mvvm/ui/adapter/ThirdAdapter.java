package com.m.livedate.mvvm.ui.adapter;

import com.m.livedate.BR;
import com.m.livedate.R;
import com.m.livedate.databinding.ImageLayoutBinding;
import com.m.livedate.databinding.RecyclerItemBinding;
import com.m.livedate.mvvm.basic.adapter.MultiDBRVAdapter;
import com.m.livedate.mvvm.ui.bean.ListBean;

/**
 * createDate:2020/8/24
 *
 * @author:spc
 * @describe：
 */
public class ThirdAdapter extends MultiDBRVAdapter<ListBean.DataBean> {
    public ThirdAdapter() {
        super(new int[]{R.layout.image_layout, R.layout.recycler_item}, new int[]{BR.dataBean});
    }
}
