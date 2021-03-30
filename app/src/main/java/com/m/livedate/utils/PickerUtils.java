package com.m.livedate.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.m.livedate.R;

import java.util.Date;
import java.util.List;

/**
 * createDate:2020/9/3
 *
 * @author:spc
 * @describe：
 */
public class PickerUtils {

    public static void showSelectOptions(Context context, List<String> data, onPickerSelect onPickerSelect) {
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                onPickerSelect.pickerSelect(data.get(options1));
            }
        }
        ).setSelectOptions(0)
                .setOutSideCancelable(true)
                .setCancelColor(context.getResources().getColor(R.color.blue))
                .setSubmitColor(context.getResources().getColor(R.color.blue))
                .build();
        optionsPickerView.setPicker(data);

        optionsPickerView.show();
    }

    /***
     * 选择时间 年月日
     * @param context
     */
    public static void getPickTime(Context context, onTimeSelect onTimeSelect) {
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                onTimeSelect.timeSelect(date);
            }
        })
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setContentTextSize(18)
                .setSubmitColor(context.getResources().getColor(R.color.blue))//确定按钮文字颜色
                .setCancelColor(context.getResources().getColor(R.color.blue))//取消按钮文字颜色
                .build();
        pvTime.show();

    }

    public interface onTimeSelect {
        void timeSelect(Date date);
    }

    public interface onPickerSelect {
        void pickerSelect(String selectText);
    }
}
