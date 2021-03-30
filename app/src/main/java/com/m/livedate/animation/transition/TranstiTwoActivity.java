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

public class TranstiTwoActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.text)
    TextView text;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transti_two);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.image)
    public void onViewClicked() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bundle = ActivityOptions.makeSceneTransitionAnimation(this, image, "activityTransform").toBundle();
            startActivity(new Intent(this, TransitOneActivity.class), bundle);
        } else {
            startActivity(new Intent(this, TransitOneActivity.class));
        }
    }
}