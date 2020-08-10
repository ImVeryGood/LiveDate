package com.m.livedate.mvp.base;

/**
 * createDate:2020/8/10
 *
 * @author:spc
 * @describe：
 */
public interface MBaseView extends BaseView {
    void showLoadingDialog(String msg);

    void dismissLoadingDialog();
}
