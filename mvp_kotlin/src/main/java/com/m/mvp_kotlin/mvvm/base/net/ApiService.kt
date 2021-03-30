package com.m.mvp_kotlin.mvvm.base.net

import com.m.mvp_kotlin.bean.AriticleResponse
import retrofit2.http.GET

/**
 * createDate:2021/3/30
 * @author:spc
 * @describe：
 */
interface ApiService {
    @GET("article/top/json")
    suspend fun getMyPerConfig(): ApiResponse<List<AriticleResponse>>

    @GET("article/top/json")
    suspend fun yPerConfig(): ApiResponse<List<AriticleResponse>>
}