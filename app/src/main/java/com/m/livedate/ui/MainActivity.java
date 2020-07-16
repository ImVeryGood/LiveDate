package com.m.livedate.ui;

import android.view.KeyEvent;

import com.m.livedate.R;
import com.m.livedate.basic.base.BaseActivity;
import com.m.livedate.databinding.ActivityMainBinding;
import com.m.livedate.ui.bean.UserBean;
import com.m.livedate.ui.model.MViewModel;
import com.m.livedate.utils.ToastUtils;

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