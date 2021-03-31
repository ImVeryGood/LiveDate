package com.m.mvp_kotlin.mvvm.base.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m.mvp_kotlin.MAP
import com.m.mvp_kotlin.mvvm.base.net.ApiException
import com.m.mvp_kotlin.mvvm.base.net.AppConstant
import com.m.mvp_kotlin.utils.showToast
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * createDate:2021/3/29
 * @author:spc
 * @describe：
 */
typealias Block<T> = suspend () -> T
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit

open class BaseViewModel : ViewModel() {

    var isShowLoading = MutableLiveData<Boolean>()//是否显示loading

    private fun showLoading() {
        isShowLoading.value = true
    }

    private fun dismissLoading() {
        isShowLoading.value = false
    }


    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @param error 错误时执行
     * @return Job
     */
    protected fun launch(block: Block<Unit>, error: Error? = null, cancel: Cancel? = null, isShowLoading: Boolean? = false): Job {
        return viewModelScope.launch {
            if (isShowLoading!!) {
                showLoading()
            }
            try {
                block.invoke()
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e)
                        error?.invoke(e)
                    }
                }
            } finally {
                dismissLoading()
            }

        }
    }

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @return Deferred<T>
     */
    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke() }
    }

    /**
     * 取消协程
     * @param job 协程job
     */
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

    private fun onError(e: Exception): Boolean {
        Log.d(AppConstant.TAG, "onError: $e")
        when (e) {
            is ApiException -> {
                when (e.code) {
                    "2001", "2002" -> {
//                        未登录
                    }
                    else -> {
//                        提示错误信息
                    }
                }
            }
            else -> {
                e.message?.let {
//                    提示错误信息
                    MAP.mContext.showToast(it)
                }
            }
        }
        return true
    }
}