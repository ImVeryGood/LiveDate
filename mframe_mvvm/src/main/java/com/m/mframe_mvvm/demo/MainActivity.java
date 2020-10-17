package com.m.mframe_mvvm.demo;

import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.m.mframe_mvvm.R;
import com.m.mframe_mvvm.basic.mvvm.butterknife.BaseActivity;
import com.m.mframe_mvvm.basic.retrofit.ApiResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainViewModel> {

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.frame)
    FrameLayout frame;
    private BlankFragment blankFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initData() {
        blankFragment = new BlankFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, blankFragment)
                .show(blankFragment)
                .commit();

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
        showLoadingDialog("加载中");
        mViewModel.getData();
    }
}
