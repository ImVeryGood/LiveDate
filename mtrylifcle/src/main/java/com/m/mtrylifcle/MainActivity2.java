package com.m.mtrylifcle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        WebView webView = findViewById(R.id.web);
        webView.loadUrl("https://www.baidu.com/?tn=88093251_83_hao_pg");

    }
}