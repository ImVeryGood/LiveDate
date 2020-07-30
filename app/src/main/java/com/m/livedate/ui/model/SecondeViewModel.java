package com.m.livedate.ui.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * createDate:2020/7/29
 *
 * @author:spc describe：
 */
public class SecondeViewModel extends ViewModel {
    private MutableLiveData<String> stringMutableLiveData;

    public SecondeViewModel() {
        stringMutableLiveData=new MutableLiveData<>();
    }

    public void setNames(String name) {
        stringMutableLiveData.setValue(name);
    }

    public MutableLiveData<String> getString() {
        return stringMutableLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("SSSSSSSSSSSSS", "onCleared: SecondeViewModel");
    }
}
