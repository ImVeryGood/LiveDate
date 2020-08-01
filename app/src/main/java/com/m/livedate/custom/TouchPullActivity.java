package com.m.livedate.custom;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.m.livedate.R;
import com.m.livedate.custom.view.TouchPullView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TouchPullActivity extends AppCompatActivity {

    @BindView(R.id.touch_pull)
    TouchPullView MTouchPull;
    @BindView(R.id.container)
    LinearLayout container;
    private static final float TOUCH_MOVE_MAX_Y = 600;
    private float mTouchMoveStartY;
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
                    default:
                        break;
                }
                return false;
            }
        });
    }
}