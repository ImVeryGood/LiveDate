package com.m.livedate.custom.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

import com.m.livedate.R;

/**
 * createDate:2020/8/3
 *
 * @author:spc describe：类似于健康管理的进度条
 */
public class CircleProgress extends View {
    private RectF mRectF;
    private Paint mCirclePaint;
    private Paint mRecFPaint;
    private Paint textPaint;
    private int mProgress;
    private float baseStartAngle = 270f;
    private float baseFinishAngle = 359.8f;
    private float progressStartAngle = 270f;
    //    底层画笔颜色
    private int baseProgressPaintColor;
    private int defaultBaseProgressPaintColor = 0Xff000000;
    //    进度条画笔颜色
    private int progressPaintColor;
    private int defaultProgressPaintColor = 0xffff0000;
    //    进度条画笔宽度
    private int progressLineWidth;
    private int defaultProgressLineWidth = 4;
    //半径
    private int circleRadius;
    private int defaultCircleRadius = 100;
    //圆环宽度
    private int strokeWidth;
    private int defaultStrokeWidth = 10;
    //    绘制起点
    private int location;
    private int defaultLocation = 1;
    private int textColor;
    private int defaultTextColor = 0xffff0000;


    private float textSize;
    private float defaultTextSize = 30;
    private int paddingT;
    private int paddingL;
    private int paddingR;
    private int paddingB;

    private ValueAnimator animator;

    public CircleProgress(Context context) {
        super(context);
        init();
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int xModel = MeasureSpec.getMode(heightMeasureSpec);
        int yModel = MeasureSpec.getMode(widthMeasureSpec);

        paddingT = getPaddingTop();
        paddingL = getPaddingLeft();
        paddingR = getPaddingRight();
        paddingB = getPaddingBottom();


        paddingT = paddingT + strokeWidth / 2;
        paddingL = paddingL + strokeWidth / 2;
        paddingR = paddingR + strokeWidth / 2;
        paddingB = paddingB + strokeWidth / 2;


        int width = MeasureSpec.getSize(widthMeasureSpec) + paddingL + paddingR;
        int height = MeasureSpec.getSize(heightMeasureSpec) + paddingB + paddingT;

        int minHeight = circleRadius * 2 + paddingB + paddingT;
        int minWidth = circleRadius * 2 + paddingL + paddingR;

        int mWidth;
        int mHeight;

        if (xModel == MeasureSpec.AT_MOST) {
            mHeight = Math.min(height, minHeight);
        } else {
            mHeight = height;
        }
        if (yModel == MeasureSpec.AT_MOST) {
            mWidth = Math.min(width, minWidth);
        } else {
            mWidth = width;
        }
        setMeasuredDimension(mWidth, mHeight);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float sweepAngle = baseFinishAngle * mProgress / 100;

        canvas.drawArc(mRectF, baseStartAngle, baseFinishAngle, false, mCirclePaint);
        canvas.drawArc(mRectF, progressStartAngle, sweepAngle, false, mRecFPaint);

        String defaultText = mProgress + "%";
        //        获取字的宽度
        float textWidth = textPaint.measureText(defaultText, 0, defaultText.length());
        float dx = getWidth() / 2 - textWidth / 2;
        //        FontMetricsInt 字体度量
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        /**
         * 文字坐标为文字的左下角
         * fontMetricsInt.top 为负，fontMetricsInt.bottom - fontMetricsInt.top等于文字高度
         * (fontMetricsInt.bottom - fontMetricsInt.top) / 2 高度的中心也就是高的一半
         * 高的一半包括bottom dy是中心线到baseline 的距离
         */
        float dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        float baseLine = getHeight() / 2 + dy;

        canvas.drawText(defaultText, dx, baseLine, textPaint);
    }

    public void initAttrs(Context mContext, AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        baseProgressPaintColor = typedArray.getColor(R.styleable.CircleProgress_base_progress_paint_color, defaultBaseProgressPaintColor);
        progressPaintColor = typedArray.getColor(R.styleable.CircleProgress_progress_paint_color, defaultProgressPaintColor);
        progressLineWidth = typedArray.getDimensionPixelSize(R.styleable.CircleProgress_progress_paint_width, defaultProgressLineWidth);
        circleRadius = typedArray.getDimensionPixelSize(R.styleable.CircleProgress_circle_radius, defaultCircleRadius);
        strokeWidth = typedArray.getDimensionPixelSize(R.styleable.CircleProgress_progress_stroke_width, defaultStrokeWidth);
        location = typedArray.getInt(R.styleable.CircleProgress_location, defaultLocation);
        textColor = typedArray.getColor(R.styleable.CircleProgress_progress_text_color, defaultTextColor);
        textSize = typedArray.getDimension(R.styleable.CircleProgress_progress_text_size, defaultTextSize);
        typedArray.recycle();
    }

    @SuppressLint("ResourceAsColor")
    public void init() {
        //        绘制文字
        textPaint = new Paint();
        textPaint.setDither(true);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);


        PathEffect effect = new DashPathEffect(new float[]{progressLineWidth, 8}, 8);
        mRectF = new RectF(50, 50, circleRadius * 2, circleRadius * 2);
        //        底部
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setDither(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(strokeWidth);
        mCirclePaint.setPathEffect(effect);
        mCirclePaint.setColor(baseProgressPaintColor);
        //进度
        mRecFPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRecFPaint.setAntiAlias(true);
        mRecFPaint.setDither(true);
        mRecFPaint.setStyle(Paint.Style.STROKE);
        mRecFPaint.setStrokeWidth(strokeWidth);
        mRecFPaint.setPathEffect(effect);
        mRecFPaint.setColor(progressPaintColor);
        switch (location) {
            case 1:
                progressStartAngle = 180f;
                break;
            case 2:
                progressStartAngle = 270f;
                break;
            case 3:
                progressStartAngle = 0f;
                break;
            case 4:
                progressStartAngle = 90f;
                break;
        }
    }

    public void setProgress(int progress) {
        if (progress > 100) {
            return;
        }
        mProgress = progress >= 100 ? 100 : progress;
        invalidate();
    }

    /**
     * 设置进度并展示动画
     *
     * @param progress 当前进度
     * @param animTime 动画时间(毫秒)
     */
    public void setProgress(int progress, long animTime) {
        if (progress > 100) {
            return;
        }
        int jProgress = Math.min(progress, 100);
        if (animTime <= 0) {
            setProgress(progress);
        } else {
            animator = ValueAnimator.ofInt(mProgress, jProgress);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mProgress = (int) animator.getAnimatedValue();
                    if (mProgress >= 0 && mProgress <= 100) {
                        invalidate();
                        onUpdateListener.onUpdateListener(mProgress);
                    }

                }
            });
            animator.setInterpolator(new OvershootInterpolator());
            animator.setDuration(animTime);
            animator.start();
        }

    }

    private OnUpdateListener onUpdateListener;

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

    public interface OnUpdateListener {
        void onUpdateListener(int progress);
    }

    public void setBaseProgressPaintColor(int baseProgressPaintColor) {
        this.baseProgressPaintColor = baseProgressPaintColor;
    }

    public void setProgressPaintColor(int progressPaintColor) {
        this.progressPaintColor = progressPaintColor;
    }

    public void setProgressLineWidth(int progressLineWidth) {
        this.progressLineWidth = progressLineWidth;
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator != null) {
            animator.cancel();
        }


    }
}
