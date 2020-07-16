package com.m.livedate.basic.base;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.m.livedate.basic.view.DialogBean;
import com.m.livedate.basic.view.DialogLiveData;
import com.m.livedate.retrofit.ApiResponse;
import com.m.livedate.retrofit.RequestImpl;
import com.m.livedate.utils.ToastUtils;

/**
 * date:2020/7/13
 * describe：基类 ViewModel
 */
public class BaseViewModel extends AndroidViewModel {
    protected Application mApplication;
    protected RequestImpl mRequestImpl;
    protected DialogLiveData<DialogBean> showDialog = new DialogLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.mApplication = application;
        mRequestImpl = new RequestImpl();
    }

    @NonNull
    public final <T> LiveData<ApiResponse<T>> map(@NonNull LiveData<ApiResponse<T>> source) {
        return Transformations.map(source, it -> {
            showDialog.setValue(false);
            Log.d("SSSSSSSSSSS", "map: "+it.isSuccess());
            if (it.isSuccess()) {
                parseCode(it);
            }
            return it;
        });
    }

    protected void parseCode(ApiResponse it) {
        Log.d("SSSSSSSSSSSSS", "parseCode: ");
        int code = it.getErrorCode();
        String errorMsg = it.getErrorMsg();
        if (code == 401 || code == 4433) {
            ToastUtils.showShortToast(errorMsg);

        } else if (code == 500) {
            ToastUtils.showShortToast(errorMsg);
        }
    }

    public void getShowDialog(LifecycleOwner owner, Observer<DialogBean> observer) {
        showDialog.observe(owner, observer);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        showDialog = null;
    }
}
