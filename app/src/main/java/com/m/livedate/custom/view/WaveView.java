package com.m.livedate.custom.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * createDate:2020/8/12
 *
 * @author:spc
 * @describe： 水波纹
 */
public class WaveView extends View{

    private Paint mPaint;
    private Path mPath;
    //一个波浪长，相当于两个二阶贝塞尔曲线的长度
    private int mItemWaveLength = 400;
    //波浪在Y轴方向的位置
    int originY = 400;
    //波浪幅度
    private int range=100;
    private int dx;

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        int halfWaveLen = mItemWaveLength/2; //半个波长，即一个贝塞尔曲线长度
        mPath.moveTo(-mItemWaveLength+dx,originY);  //波浪的开始位置
        //每一次for循环添加一个波浪的长度到path中，根据view的宽度来计算一共可以添加多少个波浪长度
        for (int i = -mItemWaveLength;i<=getWidth()+mItemWaveLength;i+=mItemWaveLength){
            mPath.rQuadTo(halfWaveLen/2,-range,halfWaveLen,0);
            mPath.rQuadTo(halfWaveLen/2,range,halfWaveLen,0);
        }
        mPath.lineTo(getWidth(),getHeight());
        mPath.lineTo(0,getHeight());
        mPath.close(); //封闭path路径

        canvas.drawPath(mPath,mPaint);
    }

    public void startAnim(){
        //根据一个动画不断得到0~mItemWaveLength的值dx，通过dx的增加不断去改变波浪开始的位置，dx的变化范围刚好是一个波浪的长度，
        //所以可以形成一个完整的波浪动画，假如dx最大小于mItemWaveLength的话， 就会不会画出一个完整的波浪形状
        ValueAnimator animator = ValueAnimator.ofInt(0,mItemWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int)animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

}
