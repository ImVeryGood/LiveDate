package com.m.livedate.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.PermissionChecker;

public class CheckPremissionUtils {
    /**
     * 检测permission是否存在
     *
     * @param context
     * @param permission 要检测的权限
     * @return
     */
    public static boolean selfPermissionGranted(Context context, String permission) {
        boolean ret = true;
        if (Build.VERSION.SDK_INT >= 23) {
            ret = context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            ret = PermissionChecker.checkSelfPermission(context, permission) == PermissionChecker.PERMISSION_GRANTED;
        }
        return ret;
    }
}
