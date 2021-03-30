package com.m.mvp_kotlin.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * createDate:2021/3/30
 * @author:spc
 * @describe：
 */
fun Context.showToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}