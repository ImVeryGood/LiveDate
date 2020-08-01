package com.m.livedate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.m.livedate.custom.TouchPullActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void goTo(View view) {
        switch (view.getId()) {
            case R.id.mvvm:
                startActivity(new Intent(this,
                        com.m.livedate.mvvm.ui.MainActivity.class));
                break;
            case R.id.custom:
                startActivity(new Intent(this,
                        TouchPullActivity.class));
                break;
            default:
                break;
        }
    }
}