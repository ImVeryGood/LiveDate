package com.m.livedate.ui.adapter;

import com.m.livedate.BR;
import com.m.livedate.R;
import com.m.livedate.basic.adapter.BaseDBRVAdapter;
import com.m.livedate.basic.adapter.BaseDBRVHolder;
import com.m.livedate.databinding.SecondFragmentItemBinding;
import com.m.livedate.ui.bean.PagingBean;

/**
 * createDate:2020/7/31
 *
 * @author:spc describe：
 */
public class SecondeNormalAdapter extends BaseDBRVAdapter<PagingBean.DataBean.DatasBean, SecondFragmentItemBinding> {
    public SecondeNormalAdapter() {
        super(R.layout.second_fragment_item, BR.pageBean);
    }

}
