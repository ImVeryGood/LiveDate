package com.m.mframe_mvvm.basic.utils;

import android.widget.Toast;

import com.m.mframe_mvvm.MApplication;


public class ToastUtils {
    protected static Toast toast = null;
    private static String oldMsg;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void showShortToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(MApplication.getInstance(), s, Toast.LENGTH_SHORT);
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
