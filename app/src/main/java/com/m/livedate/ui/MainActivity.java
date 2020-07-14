package com.m.livedate.ui;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.m.livedate.ui.model.MViewModel;
import com.m.livedate.R;
import com.m.livedate.base.BaseActivity;
import com.m.livedate.retrofit.ApiResponse;
import com.m.livedate.retrofit.ListBean;
import com.m.livedate.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.m.livedate.utils.ActivityManager.getAppInstance;

public class MainActivity extends BaseActivity<MViewModel> {
    @BindView(R.id.txt)
    TextView textView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        getVM().getListBeanData().observe(this, new Observer<ApiResponse<List<ListBean.DataBean>>>() {
            @Override
            public void onChanged(ApiResponse<List<ListBean.DataBean>> listApiResponse) {
                textView.setText(new Gson().toJson(listApiResponse));
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.GET, R.id.CLEAR,R.id.GOFragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.GET:
                getVM().getData();
                break;
            case R.id.CLEAR:
                textView.setText(null);
                break;
            case R.id.GOFragment:
                startActivity(FragmentActivity.class);
                break;
        }
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
