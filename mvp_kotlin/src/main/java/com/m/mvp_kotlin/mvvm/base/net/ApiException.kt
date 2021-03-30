package com.m.mvp_kotlin.mvvm.base.net

import androidx.annotation.Keep

@Keep
class ApiException(var code: String, override var message: String) : RuntimeException()