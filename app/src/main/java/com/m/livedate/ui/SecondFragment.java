package com.m.livedate.ui;


import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.m.livedate.R;
import com.m.livedate.basic.base.BaseFragment;
import com.m.livedate.databinding.FragmentSecondBinding;
import com.m.livedate.ui.model.MViewModel;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends BaseFragment<MViewModel, FragmentSecondBinding> {


    @BindView(R.id.text)
    TextView text;

    @Override
    protected void setDataBinding() {

    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_second;
    }

    @Override
    protected void initData() {
        dataBinding.text.setText("66666");

    }

    @Override
    protected void initListener() {

    }

}
