package com.m.livedate.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * createDate:2020/8/1
 *
 * @author:spc describe：贝塞尔曲线
 */
public class TestBell extends ViewGroup {
    public TestBell(Context context) {
        super(context);
        init();
    }

    public TestBell(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestBell(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TestBell(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent event) {
        return super.dispatchHoverEvent(event);
    }

    //画笔
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //    路径
    private final Path mPath = new Path();

    public void init() {
        /**
         * 一阶贝塞尔
         */
        Path path = mPath;
        //         起始点
        path.moveTo(100, 100);
        //       链接两个点
        path.lineTo(300, 300);
        /**
         * quadTo相对于最原始坐标（0，0）
         * rQuadTo相对于上一个点,只考虑结束点
         */
        // path.quadTo(500, 100, 700, 300);
        //       相对的实现
        path.rQuadTo(200, -300, 400, 0);
        /**
         * 三阶 两个控制点一个结束点
         */
        path.moveTo(400, 800);
       // path.cubicTo(500, 600, 700, 1200, 800, 800);
        path.rCubicTo(100, -200, 300, 400, 400, 0);
        /**
         * 使用三次或者三次以上这样写能提升效率Paint paint = mPaint;
         */
        Paint paint = mPaint;
        //         抗锯齿
        paint.setAntiAlias(true);
        //        抗抖动
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);

    }
}
