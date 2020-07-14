package com.m.livedate.utils;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationManagerCompat;

import com.m.livedate.R;
import com.m.livedate.base.MAppLication;


public class NotificationDialogUtils {
    /**
     * 判断是否打开通知
     * 接受推送的通知栏
     *
     * @return
     */
    public static boolean isNotificationEnabled(final Context context) {
        String openNotification = "openNotification";
        if (SharePreConfigUtils.getString(context, openNotification, "").equals("close")) {
            return false;
        }
        NotificationManagerCompat manager = NotificationManagerCompat.from(MAppLication.getMApplication());
        boolean enabled = manager.areNotificationsEnabled();
        if (!enabled) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_open_notification, null);
            final AlertDialog tipNotificationDialog = new AlertDialog.Builder(context)
                    .setView(inflate)
                    .create();
            final RadioGroup radioGroup = inflate.findViewById(R.id.radio_group);
            Button btnCancel = inflate.findViewById(R.id.btn_cancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tipNotificationDialog.dismiss();
                }
            });
            Button btnConfirm = inflate.findViewById(R.id.btn_confirm);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (radioGroup.getCheckedRadioButtonId()) {

                        case R.id.rb_1:
                            //立即开启
                            goToSet(context);
                            tipNotificationDialog.dismiss();
                            break;
                        case R.id.rb_2:
                            //暂时关闭
                            tipNotificationDialog.dismiss();
                            break;
                        case R.id.rb_3:
                            //不再提醒
                            SharePreConfigUtils.put(context, openNotification, "close");
                            tipNotificationDialog.dismiss();
                            break;
                    }
                }
            });
            tipNotificationDialog.show();
            return false;
        } else {
            //开启
            return true;
        }
    }

    /**
     * 跳转打开通知栏设置界面
     */
    private static void goToSet(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Intent intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
            context.startActivity(intent);
        } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
            context.startActivity(intent);
        }
    }
}
