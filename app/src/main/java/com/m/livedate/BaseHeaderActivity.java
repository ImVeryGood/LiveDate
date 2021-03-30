package com.m.livedate;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseHeaderActivity extends AppCompatActivity {

    FrameLayout rlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_header);
        rlContent=findViewById(R.id.rl_content);
        rlContent.addView(View.inflate(this,setId(),null));

    }

    public abstract int setId();
}