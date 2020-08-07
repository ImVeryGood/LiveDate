package com.m.livedate.custom;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.m.livedate.R;
import com.m.livedate.custom.view.CircleProgress;
import com.m.livedate.custom.view.TouchPullView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TouchPullActivity extends AppCompatActivity {

    @BindView(R.id.touch_pull)
    TouchPullView MTouchPull;
    @BindView(R.id.container)
    LinearLayout container;
    private static final float TOUCH_MOVE_MAX_Y = 600;
    @BindView(R.id.circle_progress)
    CircleProgress circleProgress;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.clear)
    Button clear;
    private float mTouchMoveStartY;
    private int degress = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_pull);
        ButterKnife.bind(this);
        initListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                拿到意图
                int action = motionEvent.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchMoveStartY = motionEvent.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float y = motionEvent.getY();
                        if (y >= mTouchMoveStartY) {
//                            移动距离
                            float moveSize = y - mTouchMoveStartY;
//                            计算进度
                            float progress = moveSize >= TOUCH_MOVE_MAX_Y ? 1 : moveSize / TOUCH_MOVE_MAX_Y;
                            MTouchPull.setProgress(progress);

                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        MTouchPull.setProgress(0);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

        circleProgress.setOnUpdateListener(new CircleProgress.OnUpdateListener() {
            @Override
            public void onUpdateListener(int progress) {
                Log.d("SSSSSSSSSSSS", "onUpdateListener: "+progress);
            }
        });
    }


    @OnClick({R.id.btn, R.id.clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                degress = degress + 10;
                circleProgress.setProgress(degress,200);
                break;
            case R.id.clear:
                degress=0;
                circleProgress.setProgress(0,2000);
                break;
        }
    }
}