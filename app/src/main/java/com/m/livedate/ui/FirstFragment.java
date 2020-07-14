package com.m.livedate.ui;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.m.livedate.R;
import com.m.livedate.base.BaseFragment;
import com.m.livedate.retrofit.ApiResponse;
import com.m.livedate.retrofit.ListBean;
import com.m.livedate.ui.model.MViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends BaseFragment<MViewModel> {


    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.GET)
    Button GET;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_firstragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        getVM().getListBeanData().observe(this, new Observer<ApiResponse<List<ListBean.DataBean>>>() {
            @Override
            public void onChanged(ApiResponse<List<ListBean.DataBean>> listApiResponse) {
                text.setText(new Gson().toJson(listApiResponse));
            }
        });

    }

    @OnClick({R.id.text, R.id.GET, R.id.CLEAR_DATA})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text:
                text.setText(null);
                break;
            case R.id.GET:
                getVM().getData();
                break;
            case R.id.CLEAR_DATA:
                text.setText(new Gson().toJson(getVM().getListBeanData().getValue()));
                break;
        }
    }
}
