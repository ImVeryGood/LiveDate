package com.m.mvp_kotlin.bean

/**
 * createDate:2021/1/8
 * @author:spc
 * @describe：
 */
open class MBean {
    var name: Int = 0;
    var title: String = ""
        get() {
            return field;
        }
        set(value) {
            if (value.equals("666")) {
                field = "5555";
            }
        };
    val age: Int = 0;
 constructor();
}