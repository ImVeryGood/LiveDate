package com.m.livedate.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.m.livedate.R;
import com.m.livedate.utils.ToastUtils;

/**
 * createDate:2020/8/11
 *
 * @author:spc
 * @describe：
 */
public class LineProgress extends View {
    private Paint linePaint;
    private Path linePath;

    private Paint pPaint;
    private Path pPath;

    private Paint ponintPaint;
    //圆点半径
    private int pointRadius = 30;
    private int defaultPointRadius = 30;
    private float xProgress = 400;

    private int defaultXProgress = 800;
    private int defaultYProgress = 100;
    private int startX = 0;
    private int startY = 100;
    private float progress = 20;

    public LineProgress(Context context) {
        super(context);
        init();
    }

    public LineProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float leftX = xProgress + pointRadius;
        float rightX = xProgress - pointRadius;

        float touchStartX = event.getX();
        float touchStartY = event.getY();

        float moveX = event.getX();
        float moveY = event.getY();

        float topY = defaultYProgress - pointRadius;
        float bottomY = defaultYProgress + pointRadius;

        boolean isClick = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStartX = event.getX();
                touchStartY = event.getY();
                isClick = (touchStartX <= leftX && touchStartX >= rightX && touchStartY >= topY && touchStartY <= bottomY);
                if (isClick && pointRadius <= defaultPointRadius * 2) {
                    pointRadius = pointRadius * 2;
                    invalidate();
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (moveX <= defaultXProgress&&moveX>=startX) {
                    moveX = event.getX();
                    moveY = event.getY();
                    xProgress = moveX;
                    invalidate();
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (pointRadius > defaultPointRadius) {
                    pointRadius = pointRadius / 2;
                    invalidate();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pPath.lineTo(xProgress, defaultYProgress);
        linePath.lineTo(defaultXProgress, defaultYProgress);
        linePath.moveTo(startX, startY);
        //canvas.drawPath(linePath, linePaint);
        // canvas.drawPath(pPath, pPaint);
        canvas.drawLine(startX, startY, defaultXProgress, defaultYProgress, linePaint);
        canvas.drawLine(startX, startY, xProgress, defaultYProgress, pPaint);
        canvas.drawCircle(xProgress, defaultYProgress, pointRadius, ponintPaint);

    }

    /**
     * 当用path画一条直线时，需指定Paint的Style为STROKE（描边），
     * 否则Paint会使用默认Style-FILL（填充），这种样式用path是画不出直线的
     */
    public void init() {
//        底色
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setAntiAlias(true);
        linePaint.setDither(true);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeWidth(20);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(getResources().getColor(R.color.textRed));

        linePath = new Path();


//        进度
        pPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pPaint.setAntiAlias(true);
        pPaint.setDither(true);
        pPaint.setStrokeCap(Paint.Cap.ROUND);
        pPaint.setStyle(Paint.Style.STROKE);
        pPaint.setStrokeWidth(20);
        pPaint.setColor(getResources().getColor(R.color.blue));

        pPath = new Path();
        pPath.moveTo(startX, startY);
        xProgress = defaultXProgress * progress / 100;


        //        进度点
        ponintPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ponintPaint.setAntiAlias(true);
        ponintPaint.setDither(true);
        ponintPaint.setStyle(Paint.Style.FILL);
        ponintPaint.setColor(getResources().getColor(R.color.blue));

    }
}
