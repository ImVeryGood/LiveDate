package com.m.mvp_kotlin.mvvm.base.net

import com.m.mvp_kotlin.bean.BannerBean
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * createDate:2021/3/30
 * @author:spc
 * @describe：
 */
interface ApiService {
    @GET("message/banner/query")
    suspend fun banner(@Query("type") type: String): ApiResponse<List<BannerBean>>

    @POST("/trade/card/list")
    suspend fun yPerConfig(): ApiResponse<List<String>>
}