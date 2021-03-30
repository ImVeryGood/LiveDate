package com.m.livedate.video;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.m.livedate.R;
import com.m.livedate.utils.PickerUtils;
import com.m.livedate.utils.ToastUtils;
import com.m.livedate.video.pop.CommonPopupWindow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spanner;
    private TextView pop;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view2);
        ButterKnife.bind(this);
        spanner = findViewById(R.id.spanner);
        pop = findViewById(R.id.pop);
        data_list = new ArrayList<String>();
        data_list.add("北京");
        data_list.add("上海");
        data_list.add("广州");
        data_list.add("深圳");

        //适配器
        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spanner.setAdapter(arr_adapter);
        setListener();
    }

    private void setListener() {
        pop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.pop_layout)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        500)

                .setOutsideTouchable(true)
                .setBackGroundLevel(0.5f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        TextView textView = view.findViewById(R.id.pop);
                        textView.setText("nihaoaaaa");
                    }
                })
                .create();
        popupWindow.showAtLocation(pop, Gravity.BOTTOM, 0, 0);


    }

    public void picker(View view) {
        switch (view.getId()) {
            case R.id.picker:
                PickerUtils.showSelectOptions(this, data_list, new PickerUtils.onPickerSelect() {
                    @Override
                    public void pickerSelect(String selectText) {
                        Log.d("SSSSSSSSS", "pickerSelect: " + selectText);
                    }

                });
                break;
            case R.id.date:
                PickerUtils.getPickTime(this, new PickerUtils.onTimeSelect() {
                    @Override
                    public void timeSelect(Date date) {
                        Log.d("SSSSSSSSS", "timeSelect: " + date.toString());
                    }
                });
                break;
        }
    }
}