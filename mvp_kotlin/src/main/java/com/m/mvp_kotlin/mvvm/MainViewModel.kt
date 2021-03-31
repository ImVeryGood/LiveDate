package com.m.mvp_kotlin.mvvm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.m.mvp_kotlin.bean.AriticleResponse
import com.m.mvp_kotlin.bean.BannerBean
import com.m.mvp_kotlin.mvvm.base.MainRepository
import com.m.mvp_kotlin.mvvm.base.net.AppConstant
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
    val listArticle = MutableLiveData<List<BannerBean>>()
    val list = MutableLiveData<List<String>>()
    fun getStrings() {
        launch(
                block = {
                    delay(2000)
                    textRigger.postValue("666")
                }
        )


    }

    fun getNetData(type: String) {
        launch(
                block = {
                    val result = async { MainRepository.getBanner(type) }
                    val res = async { MainRepository.getMyData() }
                    listArticle.value = result.await()
                    list.value = res.await()
                    Log.d(AppConstant.TAG, "getNetData: "+ result.await()[0].imgUrl)
                },
                isShowLoading = true
        )

    }
}