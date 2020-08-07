package com.m.livedate.custom.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.orhanobut.logger.Logger;

/**
 * createDate:2020/8/1
 *
 * @author:spc describe：可拉动刷新头
 */
public class TouchPullView extends View {
    //    圆的画笔
    private Paint mCirclePaint;
    //    圆的半径
    private int mCircleRadius = 200;
    private float mCircleX;
    private float mCircleY;
    //进度值
    private float mProgress;
    //    可拖动高度
    private int mDragHeight = 450;

    public TouchPullView(Context context) {
        super(context);
        init();
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * DashPathEffect 画虚线，new float[]{10, 5} 10代表实线的宽度，5代表虚线的宽度 phase 偏移量
     */
    public void init() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //        设置抗锯齿
        paint.setAntiAlias(true);
        //        防抖动
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(40);
        PathEffect effects = new DashPathEffect(new float[]{3, 3}, 0);
        paint.setPathEffect(effects);
        paint.setColor(0xFF000002);
        mCirclePaint = paint;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCircleX, mCircleY, mCircleRadius, mCirclePaint);
    }

    /**
     * 当测量的时候触发
     * 0.5f 实现四舍五入
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //        宽度的意图，宽度的类型
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //最小高度
        int minHeight = (int) ((mDragHeight * mProgress + 0.5f) + getPaddingTop() + getPaddingBottom());
        //最小宽度
        int minWidth = 2 * mCircleRadius + getPaddingLeft() + getPaddingRight();

        int measureWidth;
        int measureHeight;
        //宽度
        if (widthMode == MeasureSpec.EXACTLY) {
            //            确切值
            measureWidth = width;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //            最多
            measureWidth = Math.min(minWidth, width);
        } else {
            measureWidth = width;
        }
        //        高度
        if (heightMode == MeasureSpec.EXACTLY) {
            //           确切值
            measureHeight = height;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //           最多
            measureHeight = Math.min(minHeight, height);
        } else {
            measureHeight = height;
        }
        //   设置宽高
        setMeasuredDimension(measureWidth, measureHeight);
    }

    /**
     * 当大小改变时触发
     * java中有三种移位运算符
     * <<      :     左移运算符，num << 1,对于是十进制相当于num乘以2。一定要数据的类型大小，再确定符号位。
     * <p>
     * >>      :     右移运算符，num >> 1,对于是十进制相当于num除以2。一定要数据的类型大小，再确定符号位。
     * <p>
     * >>>    :     无符号右移，忽略符号位，对于是二进制空位都以0补齐。只是对32位和64位的值有意义
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCircleX = getWidth() >> 1;
        mCircleY = getHeight() >> 1;
    }

    /**
     * 设置进度
     *
     * @param progress 进度
     */
    public void setProgress(float progress) {
        mProgress = progress;
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "rotation", 360 * progress / 100);
        animator.setDuration(2000);
        animator.start();

        //        请求重新测量
        requestLayout();
    }
}
