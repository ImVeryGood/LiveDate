package com.m.livedate.custom;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.m.livedate.R;
import com.m.livedate.custom.view.DragView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends AppCompatActivity {

    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.sign)
    Button sign;
    private DragView dragView;
    private List<DragView> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);
        list = new ArrayList<>();
    }


    @OnClick({R.id.sign, R.id.get_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign:
                makeSign();
                break;
            case R.id.get_sign:
                getSign();
                break;
        }
    }
    public void makeSign() {
        dragView = new DragView(SignActivity.this);
        dragView.setISrc(R.mipmap.icon_android);
        list.add(dragView);
        container.addView(dragView);

    }
    public void getSign(){
        for (int i = 0; i < list.size(); i++) {
            dragView=list.get(i);
            int[] location = new int[2];
            dragView.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            Log.d("SSSSSSSSSSS", "getSign: x=:"+x+"y=:"+y);
            Log.d("SSSSSSSSSSS", "-------------------------------------");
        }
    }

}