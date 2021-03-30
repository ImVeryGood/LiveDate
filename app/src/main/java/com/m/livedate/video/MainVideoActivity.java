package com.m.livedate.video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.m.livedate.R;

public class MainVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_video);
    }

    public void goTo(View view) {
        switch (view.getId()) {
            case R.id.GSYVideo:
                start(VideoActivity.class);
                break;
            case R.id.JiaoZiVideo:
                start(JiaoZiActivity.class);
                break;
            case R.id.spanner:
                start(ViewActivity.class);
                break;
        }
    }

    public void start(Class mClass) {
        startActivity(new Intent(this, mClass));

    }
}