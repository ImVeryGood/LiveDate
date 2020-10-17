package com.m.mframe_mvvm.demo;

import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.m.mframe_mvvm.R;
import com.m.mframe_mvvm.basic.mvvm.butterknife.BaseFragment;
import com.m.mframe_mvvm.basic.retrofit.ApiResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class BlankFragment extends BaseFragment<MainViewModel> {


    @BindView(R.id.text)
    TextView text;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_blank;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mViewModel.getMutableLiveData().observe(this, new Observer<ApiResponse<List<ListBean.DataBean>>>() {
            @Override
            public void onChanged(ApiResponse<List<ListBean.DataBean>> listApiResponse) {
                dismissLoadingDialog();
                text.setText(new Gson().toJson(listApiResponse));
            }
        });

    }

    @OnClick(R.id.text)
    public void onViewClicked() {
        showLoadingDialog("加载中...");
        mViewModel.getData();
    }
}