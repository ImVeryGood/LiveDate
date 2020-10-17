package com.m.livedate.mvvm.ui.adapter;

import com.m.livedate.BR;
import com.m.livedate.R;
import com.m.livedate.mvvm.basic.adapter.BaseDBRVAdapter;
import com.m.livedate.databinding.SecondFragmentItemBinding;
import com.m.livedate.mvvm.ui.bean.PagingBean;

/**
 * createDate:2020/7/31
 *
 * @author:spc describe：
 */
public class SecondeNormalAdapter extends BaseDBRVAdapter<PagingBean.DataBean.DatasBean, SecondFragmentItemBinding> {
    public SecondeNormalAdapter() {
        super(R.layout.records_list_item_layout, BR.pageBean);
    }

}
