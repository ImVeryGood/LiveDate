package com.m.livedate.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.m.livedate.R;

/**
 * createDate:2020/8/13
 *
 * @author:spc
 * @describe： 可随意拖动的小球
 */
public class SignView extends View {
    private Paint mPaint;
    private float mX = 100;
    private float mY = 100;
    private int pointRadius=30;
    private float startX;
    private float startY;
    public SignView(Context context) {
        super(context);
        init();
    }

    public SignView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int xModel = MeasureSpec.getMode(widthMeasureSpec);
        int yModel = MeasureSpec.getMode(heightMeasureSpec);

        int xWidth = MeasureSpec.getSize(widthMeasureSpec);
        int yHeight = MeasureSpec.getSize(heightMeasureSpec);

        int minWidth = pointRadius * 2 + getPaddingLeft() + getPaddingRight();
        int minHeight = pointRadius * 2 + getPaddingTop() + getPaddingBottom();
        int measureWidth;
        int measureHeight;
        if (xModel == MeasureSpec.AT_MOST) {
            measureWidth = minWidth;
        } else {
            measureWidth = xWidth;
        }
        if (yModel == MeasureSpec.AT_MOST) {
            measureHeight = minHeight;
        } else {
            measureHeight = yHeight;
        }
        setMeasuredDimension(xWidth, yHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mX, mY, pointRadius, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX=event.getX();
                startY=event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                setTranslationX(getX() + (event.getX() - startX));
                setTranslationY(getY() + (event.getY() - startY));
                return true;
        }
        return super.onTouchEvent(event);
    }

    public void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(getResources().getColor(R.color.blue));

    }
}
