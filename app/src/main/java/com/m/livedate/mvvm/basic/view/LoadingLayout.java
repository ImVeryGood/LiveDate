package com.m.livedate.mvvm.basic.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import com.m.livedate.R;


/**
 * createDate:2020/9/24
 *
 * @author:spc
 * @describe：
 */
public class LoadingLayout extends FrameLayout {
    private static final String TAG = LoadingLayout.class.getSimpleName();

    /** 加载中 */
    public static final int LOADING_STATE = 0x001;
    /** 加载成功 */
    public static final int LOAD_SUCCESS_STATE = 0x002;
    /** 网络错误 */
    public static final int NET_ERROR_STATE = 0x003;
    /** 加载失败，超时、接口错误、404等 */
    public static final int LOAD_FAILURE_STATE = 0x004;
    /** 没有数据 */
    public static final int NULL_DATA_STATE = 0x005;

    private View loadingView;       //loading 加载中。。
    private View netErrorView;      //网络错误View
    private View loadFailureView;   //加载失败View
    private View nullDataView;      //没有数据View

    //定义注释限定参数值，可以去掉
    @IntDef({LOADING_STATE, LOAD_SUCCESS_STATE, NET_ERROR_STATE, LOAD_FAILURE_STATE,NULL_DATA_STATE})
    public @interface LoadingState{}

    private final LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    public LoadingLayout(@NonNull Context context) {
        this(context, null);
    }

    public LoadingLayout(@NonNull  Context context, @NonNull AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingLayout(@NonNull  Context context, @NonNull  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //指定默认ID
        if(getId() == -1){
            setId(R.id.loading_layout_id);
        }

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.LoadingLayout, 0, 0);
        try{
            //为每种状态指定默认效果
            int progressViewRes = a.getResourceId(R.styleable.LoadingLayout_loadingViewDrawable,
                    R.layout.default_loading_layout);
            int netErrorViewRes = a.getResourceId(R.styleable.LoadingLayout_netErrorViewDrawable,
                    R.layout.default_net_error_layout);
            int loadFailureViewRes = a.getResourceId(R.styleable.LoadingLayout_loadFailureViewDrawable,
                    R.layout.default_load_failure_layout);
            int nullDataViewRes = a.getResourceId(R.styleable.LoadingLayout_nullDataViewDrawable,
                    R.layout.default_null_data_layout);

            loadingView = LayoutInflater.from(context).inflate(progressViewRes, null, true);
            netErrorView = LayoutInflater.from(context).inflate(netErrorViewRes, null, true);
            loadFailureView = LayoutInflater.from(context).inflate(loadFailureViewRes, null, true);
            nullDataView = LayoutInflater.from(context).inflate(nullDataViewRes, null, true);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.layoutParams.width = this.getMeasuredWidth();
        this.layoutParams.height = this.getMeasuredHeight();
    }


    /**
     * 通过状态刷新界面显示
     * @param state 当前状态
     */
    public void refreshView(@LoadingState int state){
        refreshView(state, null);
    }

    /**
     * 通过状态刷新界面显示
     * @param state 当前状态
     * @param onClickListener 点击监听
     */
    public void refreshView(@LoadingState int state, OnClickListener onClickListener){
        switch (state){
            case LOAD_SUCCESS_STATE:
                //加载成功，移除所有状态View
                removeStateView();
                break;
            case LOADING_STATE:
                showLoading();
                break;
            case NET_ERROR_STATE:
                showNetErrorMessage(onClickListener);
                break;
            case LOAD_FAILURE_STATE:
                showLoadFailureMessage(onClickListener);
                break;
            case NULL_DATA_STATE:
                showNullDataMessage();
                break;
            default:
                Log.d(TAG, "state error");
                break;
        }
    }

    /**
     * 显示loading
     */
    public void showLoading(){
        removeStateView();

        if(loadingView != null){
            this.addView(loadingView);
        } else{
            Log.d(TAG, "Please init loadingView first");
        }
    }

    /**
     * 显示网络错误提示
     */
    public void showNetErrorMessage(){
        showNetErrorMessage(null);
    }

    /**
     * 显示网络错误提示
     * @param onClickListener 指定点击效果监听
     */
    public void showNetErrorMessage(OnClickListener onClickListener){
        removeStateView();

        if(netErrorView != null){
            if(onClickListener != null){
                netErrorView.setOnClickListener(onClickListener);
            }
            this.addView(netErrorView);
        } else{
            Log.d(TAG, "Please init netErrorView first");
        }
    }

    /**
     * 显示无数据提示
     */
    public void showNullDataMessage(){
        removeStateView();

        if(nullDataView != null){
            this.addView(nullDataView);
        } else{
            Log.d(TAG, "Please init nullDataView first");
        }
    }

    /**
     * 显示加载失败提示
     */
    public void showLoadFailureMessage(){
        showLoadFailureMessage(null);
    }

    /**
     * 显示加载失败提示
     * @param onClickListener 指定点击效果监听
     */
    public void showLoadFailureMessage(OnClickListener onClickListener){
        removeStateView();

        if(loadFailureView != null){
            if(onClickListener != null){
                loadFailureView.setOnClickListener(onClickListener);
            }

            this.addView(loadFailureView);
        } else{
            Log.d(TAG, "Please init loadFailureView first");
        }
    }

    /**
     * 移除所有状态View
     */
    private void removeStateView(){
        if(loadingView != null){
            this.removeView(loadingView);
        }
        if(netErrorView != null){
            this.removeView(netErrorView);
        }
        if(loadFailureView != null){
            this.removeView(loadFailureView);
        }
        if(nullDataView != null){
            this.removeView(nullDataView);
        }
    }
}
