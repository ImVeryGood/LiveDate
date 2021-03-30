package com.m.livedate.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.m.livedate.R;

/**
 * author：   wdl
 * time： 2018/9/17 15:16
 * des：    TODO
 */
public class HeaderBar extends RelativeLayout {
    //所需控件
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvRight;

    //对应属性
    private String title;
    private float titleSize;
    private int titleColor;

    private String rightTitle;
    private float rightTitleSize;
    private int rightColor;

    private Boolean isShowBack;

    private LayoutParams ivParams,titleParams,rightParams;

    public HeaderBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context
                .obtainStyledAttributes(attrs, R.styleable.HeaderBar);

        //获取对应的属性值
        title = typedArray.getString(R.styleable.HeaderBar_titleTextX);
        titleSize = typedArray.getDimension(R.styleable.HeaderBar_titleSize,15f);
        titleColor = typedArray.getColor(R.styleable.HeaderBar_titleColor,0);

        rightTitle = typedArray.getString(R.styleable.HeaderBar_rightTextX);
        rightTitleSize = typedArray.getDimension(R.styleable.HeaderBar_rightSize,12f);
        rightColor = typedArray.getColor(R.styleable.HeaderBar_rightColor,0);

        isShowBack = typedArray.getBoolean(R.styleable.HeaderBar_isShowBackX,false);
        //回收 避免内存泄露
        typedArray.recycle();

        //初始化控件
        ivBack = new ImageView(context);
        tvTitle = new TextView(context);
        tvRight = new TextView(context);

        //给控件设置相应的属性
        ivBack.setVisibility(isShowBack?VISIBLE:GONE);
        ivBack.setImageResource(R.drawable.video_back);

        tvTitle.setText(title);
        tvTitle.setTextSize(titleSize);
        tvTitle.setTextColor(titleColor);
        tvTitle.setGravity(CENTER_IN_PARENT);

        tvRight.setText(rightTitle);
        tvRight.setTextSize(rightTitleSize);
        tvRight.setTextColor(rightColor);
        tvRight.setGravity(CENTER_IN_PARENT);

        //设置参数添加进viewGroup
        ivParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        ivParams.addRule(ALIGN_PARENT_LEFT|CENTER_VERTICAL,TRUE);

        titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        titleParams.addRule(CENTER_IN_PARENT,TRUE);

        rightParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        rightParams.addRule(ALIGN_PARENT_RIGHT,TRUE);

        addView(ivBack,ivParams);
        addView(tvTitle,titleParams);
        addView(tvRight,rightParams);
        //设置背景色
        setBackgroundColor(getResources().getColor(R.color.colorAccent));

    }
}
