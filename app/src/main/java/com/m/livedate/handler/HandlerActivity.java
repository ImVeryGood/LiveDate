package com.m.livedate.handler;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.m.livedate.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HandlerActivity extends AppCompatActivity {

    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.info)
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        ButterKnife.bind(this);
        info.post(new Runnable() {
            @Override
            public void run() {
                info.setText("666666");
            }
        });

    }

    /**
     * Handler 是Android定义的一套，子线程与主线程间通讯的消息传递机制
     * Handler 作用是把子线程需要更新的UI信息，传递给UI线程，以此完成UI更新操作
     * 为什么用Handler 不用行不行
     * 答：不行，Handler是安卓设计之初封装的一套消息创建，传递，处理。
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    info.setText(msg.obj.toString());
                    break;
                default:
                    break;
            }
        }
    };

    @OnClick(R.id.btn)
    public void onViewClicked() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 1;
                message.obj = "msg";
                handler.sendMessage(message);

            }
        }).start();
    }
}