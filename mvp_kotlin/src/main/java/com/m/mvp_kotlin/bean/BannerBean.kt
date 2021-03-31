package com.m.mvp_kotlin.bean

/**
 * createDate:2021/3/31
 * @author:spc
 * @describe：
 */
data class BannerBean(
    val createTime: Long,
    val deviceType: String,
    val href: String,
    val id: Int,
    val imgUrl: String,
    val indexId: Int,
    val keyword: String,
    val sideColor: Any,
    val size: Any,
    val status: String,
    val type: String,
    val updateTime: Long
)