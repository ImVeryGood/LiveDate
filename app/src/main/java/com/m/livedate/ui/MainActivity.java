package com.m.livedate.ui;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.m.livedate.R;
import com.m.livedate.basic.base.BaseActivity;
import com.m.livedate.basic.retrofit.ApiResponse;
import com.m.livedate.databinding.ActivityMainBinding;
import com.m.livedate.ui.bean.ListBean;
import com.m.livedate.ui.bean.UserBean;
import com.m.livedate.ui.model.MViewModel;
import com.m.livedate.utils.ToastUtils;

import java.util.List;

import static com.m.livedate.utils.ActivityManager.getAppInstance;

public class MainActivity extends BaseActivity<MViewModel, ActivityMainBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setDataBinding() {
        dataBinding.setViewModel(getVM());
        dataBinding.setMethord(this);
        dataBinding.setUserBean(new UserBean());
    }


    @Override
    protected void initData() {

    }

    public void onClick() {
       startActivity(FragmentActivity.class);
    }

    @Override
    protected void initListener() {
    mViewModel.getListBeanData().observe(this, new Observer<ApiResponse<List<ListBean.DataBean>>>() {
        @Override
        public void onChanged(ApiResponse<List<ListBean.DataBean>> listApiResponse) {
            Log.d("SSSSSSSSSSSSSSSSSS", "onChanged:回来触发 ");
        }
    });

    }


    private static final long BACK_WAIT_TIME = 2000;
    private long mTouchTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mTouchTime) >= BACK_WAIT_TIME) {
                mTouchTime = System.currentTimeMillis();
                ToastUtils.showShortToast("再按一次退出");
            } else {
                mTouchTime = 0;
                getAppInstance().AppExit(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
