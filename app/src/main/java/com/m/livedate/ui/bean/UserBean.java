package com.m.livedate.ui.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.m.livedate.BR;


/**
 * createDate:2020/7/16
 *
 * @author:spc describe： 数据双向绑定
 */
public class UserBean extends BaseObservable {
    private String name;
    private String passWord;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
        notifyPropertyChanged(BR.passWord);
    }
}
