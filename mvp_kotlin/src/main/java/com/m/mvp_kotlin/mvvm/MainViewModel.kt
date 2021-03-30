package com.m.mvp_kotlin.mvvm

import androidx.lifecycle.MutableLiveData
import com.m.mvp_kotlin.bean.AriticleResponse
import com.m.mvp_kotlin.mvvm.base.net.apiService
import com.m.mvp_kotlin.mvvm.base.viewmodel.BaseViewModel
import kotlinx.coroutines.delay

/**
 * createDate:2021/3/29
 * @author:spc
 * @describe：
 */
class MainViewModel : BaseViewModel() {
    var textRigger = MutableLiveData<String>()
    val listArticle = MutableLiveData<List<AriticleResponse>>()
     val list = MutableLiveData<List<AriticleResponse>>()
    fun getStrings() {
        launch(
                block = {
                    delay(2000)
                    textRigger.postValue("666")
                }
        )


    }

    fun getNetData() {
        launch(
                block = {
                    val result = async { apiService.getMyPerConfig() }
                    val res = async { apiService.yPerConfig() }
                    listArticle.value = result.await().data
                    list.value = res.await().data
                },
                isShowLoading = true
        )

    }
}