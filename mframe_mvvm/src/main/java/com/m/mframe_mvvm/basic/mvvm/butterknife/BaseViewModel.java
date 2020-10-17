package com.m.mframe_mvvm.basic.mvvm.butterknife;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.m.mframe_mvvm.basic.retrofit.ApiResponse;
import com.m.mframe_mvvm.basic.retrofit.RequestImpl;
import com.m.mframe_mvvm.basic.utils.ToastUtils;


/**
 * date:2020/7/13
 * describe：基类 ViewModel
 */
public class BaseViewModel extends AndroidViewModel {
    protected Application mApplication;
    protected RequestImpl mRequestImpl;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.mApplication = application;
        mRequestImpl = new RequestImpl();
    }

    @NonNull
    public final <T> LiveData<ApiResponse<T>> map(@NonNull LiveData<ApiResponse<T>> source) {
        return Transformations.map(source, it -> {
            if (it.isSuccess()) {
                parseCode(it);
            }
            return it;
        });
    }

    protected void parseCode(ApiResponse it) {
        int code = it.getErrorCode();
        String errorMsg = it.getErrorMsg();
        if (code == 401 || code == 4433) {
            ToastUtils.showShortToast(errorMsg);

        } else if (code == 500) {
            ToastUtils.showShortToast(errorMsg);
        }
    }

}
