package com.m.livedate;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * date:2020/7/9
 * describe：
 */
public class MViewModel extends ViewModel {
    private MutableLiveData<Integer> number;

    public MutableLiveData<Integer> getNumber() {
        if (number == null) {
            number = new MutableLiveData<>();
            number.setValue(0);
        }
        return number;
    }

    public void doSet(int n) {
        Log.d("SSSSSSSSSSS", "doSet: "+number.getValue()+"n="+n);
        number.setValue(number.getValue() + n);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
