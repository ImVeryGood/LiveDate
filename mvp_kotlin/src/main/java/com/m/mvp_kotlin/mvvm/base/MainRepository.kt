package com.m.mvp_kotlin.mvvm.base

import com.m.mvp_kotlin.mvvm.base.net.Repository
import com.m.mvp_kotlin.mvvm.base.net.apiService

/**
 * createDate:2021/3/31
 * @author:spc
 * @describe：
 */
object MainRepository : Repository() {

    suspend fun getMyData() = preprocessData(apiService.yPerConfig())
    suspend fun getBanner(type: String) = preprocessData(apiService.banner(type))
}