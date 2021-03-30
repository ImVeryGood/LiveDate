package com.m.livedate.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.m.livedate.R;
import com.m.livedate.animation.transition.TransitOneActivity;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
    }

    public void goTo(View view) {
        switch (view.getId()) {
            case R.id.transition:
                startActivity(TransitOneActivity.class);
                break;
            default:
                break;
        }
    }

    public void startActivity(Class classs) {
        startActivity(new Intent(this, classs));
    }
}