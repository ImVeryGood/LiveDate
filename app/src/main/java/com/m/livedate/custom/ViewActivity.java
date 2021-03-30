package com.m.livedate.custom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.m.livedate.R;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    }

    public void goTo(View view) {
        switch (view.getId()) {
            case R.id.view:
                startActivity(TouchPullActivity.class);
                break;
            case R.id.sign:
                startActivity(SignActivity.class);
                break;
            default:
                break;
        }
    }

    public void startActivity(Class mClass) {
        startActivity(new Intent(this, mClass));
    }
}