package com.m.livedate.custom;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * createDate:2020/8/13
 *
 * @author:spc
 * @describe：
 */
public class UiUtil {
    // 获取最大宽度
    public static int getMaxWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService( Context.WINDOW_SERVICE );
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics( dm );
        return dm.widthPixels;
    }
    // 获取最大高度
    public static int getMaxHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService( Context.WINDOW_SERVICE );
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics( dm );
        return dm.heightPixels;
    }

}
