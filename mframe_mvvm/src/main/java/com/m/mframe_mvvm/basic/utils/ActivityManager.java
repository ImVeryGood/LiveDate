package com.m.mframe_mvvm.basic.utils;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;


public class ActivityManager {

    private static Stack<Activity> activityStack;

    private ActivityManager() {
    }

    /**
     * 单一实例
     */
    public static ActivityManager getAppInstance() {
        return HolderClass.instance;
    }

    private static class HolderClass {
        final static ActivityManager instance = new ActivityManager();
    }

    /**
     * 添加Activity到堆栈
     */
    public synchronized void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
//        LogUtils.i("ActivityManager添加了：" + activity.getClass().getName());
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        finishActivity(activityStack.lastElement());
    }

    /**
     * 移除最后一个Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
//            LogUtils.i("ActivityManager移除了：" + activity.getClass().getName());
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
                finishActivity(activityStack.get(i));
                removeActivity(activityStack.get(i));
                return;
            }
        }
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }


    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
