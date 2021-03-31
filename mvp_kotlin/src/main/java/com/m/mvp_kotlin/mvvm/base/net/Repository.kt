package com.m.mvp_kotlin.mvvm.base.net

import android.content.Context
import android.util.Log
import com.m.mvp_kotlin.MAP
import com.m.mvp_kotlin.utils.showToast

/**
 * createDate:2021/3/31
 * @author:spc
 * @describe：
 */
open class Repository {

    /**
     * 预处理数据(错误)
     * @param context 跳至登录页的上下文
     */
    fun <T : Any> preprocessData(baseBean: ApiResponse<T>): T {
        Log.d(AppConstant.TAG, "preprocessData: "+baseBean.result)
        return if (baseBean.code == "200") {// 成功
            // 返回数据
            baseBean.result
        } else {// 失败
            // 抛出接口异常
            MAP.mContext.showToast(baseBean.msg)
            throw ApiException(baseBean.code, baseBean.msg)
        }
    }


}