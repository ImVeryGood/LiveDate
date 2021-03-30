package com.m.livedate.animation.transition;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.m.livedate.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransitOneActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.text)
    TextView text;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transit_one);
        ButterKnife.bind(this);
    }

    /**
     * Activity过渡动画包含「进入过渡」和「退出过渡」、
     * 「共享元素过渡」三个动画，它们同样仅支持Android 5.0+版本。
     */
    @OnClick(R.id.image)
    public void onViewClicked() {
        /**
         * 代码中，先判断当前Android版本是否大于等于5.0，
         * 大于或等于Android 5.0的话就设置共享元素动画,小于5.0 就正常启动第二个Activity。
         *
         * 通过ActivityOptions.makeSceneTransitionAnimation()创建启动Activity过渡的一些参数
         *，makeSceneTransitionAnimation()函数第一个参数为Activity对象;第二个参数为共享元素组件，
         * 这里设置为id是ivImage的ImageView视图；第三个参数为transitionName属性的值，这里是activityTransform。
         * 在调用AcivityOptions对象toBundle函数，包装成Bundle对象。
         *
         *
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bundle = ActivityOptions.makeSceneTransitionAnimation(this, image, "activityTransform").toBundle();
            startActivity(new Intent(this, TranstiTwoActivity.class), bundle);
        } else {
            startActivity(new Intent(this, TranstiTwoActivity.class));
        }
    }
}