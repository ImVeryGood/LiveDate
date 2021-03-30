package com.m.livedate.custom.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.m.livedate.R;
import com.m.livedate.custom.inter.OnDragViewClickListener;


/**
 * Created by bailing on 2018/8/18.
 * Description：拖动view
 */


public class DragView extends FrameLayout {

    private float mStartX;
    private float mStartY;
    private int rawX;
    private int rawY;
    private int lastX;
    private int lastY;
    private int pHeight;
    private int pWidth;
    private long mLastTime;
    private ViewGroup parent;

    private ImageView iv_pic, iv_close;
    private RelativeLayout relativeLayout;
    private EditText edit;
    private OnDragViewClickListener dragViewClickListener;
    private boolean flag = false;

    public void SetClickListener(OnDragViewClickListener dragViewClickListener) {
        this.dragViewClickListener = dragViewClickListener;
    }

    public DragView(Context context) {
        this(context, null);

    }


    public DragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DragView);
        int i_src = typedArray.getResourceId(R.styleable.DragView_i_src, 0);
        float i_width = typedArray.getFloat(R.styleable.DragView_i_width, 0);
        float i_height = typedArray.getFloat(R.styleable.DragView_i_height, 0);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dragview, this);
        relativeLayout = view.findViewById(R.id.draw);
        iv_close = view.findViewById(R.id.iv_close);
        iv_pic = view.findViewById(R.id.iv_pic);
        edit = view.findViewById(R.id.edit);
        iv_pic.setImageResource(i_src);
        iv_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dragViewClickListener.onDragViewListener("delete","");
            }
        });
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        typedArray.recycle();
    }

    public void setISrc(int imageId) {
        iv_pic.setImageResource(imageId);
        SetClickListener(dragViewClickListener);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    //判断是否触摸view
    private boolean isTouchInView(ImageView view, float xAxis, float yAxis) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (yAxis >= top && yAxis <= bottom && xAxis >= left
                && xAxis <= right) {
            return true;
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取相对屏幕的坐标，即以屏幕左上角为原点
        rawX = (int) event.getRawX();
        rawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:    //捕获手指触摸按下动作
                //获取相对View的坐标，即以此View左上角为原点
                mStartX = event.getRawX();
                mStartY = event.getRawY();
                if (isTouchInView(iv_close, rawX, rawY)) {
                    flag = true;
                }
                mLastTime = System.currentTimeMillis();
                lastX = rawX;
                lastY = rawY;
                if (getParent() != null) {
                    parent = (ViewGroup) getParent();
                    pHeight = parent.getHeight();
                    pWidth = parent.getWidth();
                }
                break;
            case MotionEvent.ACTION_MOVE:   //捕获手指触摸移动动作
                int dx = rawX - lastX;
                int dy = rawY - lastY;
                float x = getX() + dx;
                float y = getY() + dy;
                //判断是否到边界
                x = x < 0 ? 0 : x > pWidth - getWidth() ? pWidth - getWidth() : x;
                y = getY() < 0 ? 0 : getY() + getHeight() > pHeight ? pHeight - getHeight() : y;
                setX(x);
                setY(y);
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_UP:    //捕获手指触摸离开动作
                if (System.currentTimeMillis() - mLastTime < 800) {
                    if (Math.abs(mStartX - event.getRawX()) < 10.0 && Math.abs(mStartY - event.getRawY()) < 10.0) {
                        //处理点击的事件
                            if (iv_close.getVisibility() == View.VISIBLE) {
                                iv_close.setVisibility(INVISIBLE);
                            } else {
                                iv_close.setVisibility(VISIBLE);
                            }
                    }
                }
                flag = false;
                break;
        }
        return true;
    }

}