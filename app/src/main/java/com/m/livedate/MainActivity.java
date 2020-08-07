package com.m.livedate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.m.livedate.custom.TouchPullActivity;
import com.m.livedate.kotlin.KActivity;
import com.m.livedate.mvp.ui.MVpActivity;

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
            case R.id.mvp:
                startActivity(new Intent(this,
                        MVpActivity.class));
                break;
            case R.id.custom:
                startActivity(new Intent(this,
                        TouchPullActivity.class));
                break;
            case R.id.kotlin:
                startActivity(new Intent(this,
                        KActivity.class));
                break;
            default:
                break;
        }
    }
}