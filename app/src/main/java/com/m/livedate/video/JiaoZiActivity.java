package com.m.livedate.video;

import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.m.livedate.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JzvdStd;

public class JiaoZiActivity extends AppCompatActivity {

    @BindView(R.id.video)
    JzvdStd video;
    private String url = "http://vfx.mtime.cn/Video/2019/06/29/mp4/190629004821240734.mp4";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiao_zi);
        ButterKnife.bind(this);
        JzvdStd jzvdStd = findViewById(R.id.video);
        jzvdStd.setUp(url, "你好");
        Glide.with(this).load(url).into(jzvdStd.posterImageView);
        jzvdStd.setScreenNormal();
        jzvdStd.setWillNotDraw(true);
        jzvdStd.setPressed(false);
        jzvdStd.progressBar.setClickable(false);
        jzvdStd.progressBar.setEnabled(false);
        jzvdStd.progressBar.setAlpha(0.5f);
        jzvdStd.startVideo();


    }
}