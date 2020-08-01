package com.m.livedate.utils;

import android.widget.Toast;

import com.m.livedate.mvvm.basic.base.MAppLication;


public class ToastUtils {
    protected static Toast toast = null;
    private static String oldMsg;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void showShortToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(MAppLication.getMApplication(), s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

}
