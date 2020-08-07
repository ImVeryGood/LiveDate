package com.m.livedate.kotlin

/**
 * createDate:2020/8/5
 * @author:spc
 *
 * describe：
 * 定义只读局部变量使⽤关键字 val 定义。只能为其赋值⼀次。
 * 可重新赋值的变量使⽤ var 关键字：
 */
open class MKotlin {

    //定义int的方式一
    var a: Int = 0

    //定义int的方式二，同一
    var b = 0

    //定义一个字符串
    var s = "ssssss"

    //定义String,lateinit关键字 可以不初始化，但在使用前必须初始化，否则会空指针
    lateinit var c: String

    //加个问号，表名这个属性可以为空，在使用时需要在属性名后加双感叹号，表示属性不为空时调用方法，   如：d!!.toString()
    var d: String? = null

    //    list 基本语法
    var list = ArrayList<String>();
    var size = list.size;
    //  var m = list[0];
//    list 遍历

    /**
     * 无参方法定义
     */
    fun main() {
        for (i in list.indices) {
            var item = list[i];
        }
        list.forEach {
            var item = it;
        }
        for (i in 0..9) {
//            打印0-9
        }
        for ((index, value) in list.withIndex()) {
            print("index $index value $value")
        }
//        switch-case
        var key = 1;
        when (key) {
            0 -> {

            }
            1 -> {

            }
            else -> {

            }
        }

        fun hasPreFix(x: Any) = when (x) {
            is String -> {
            }
            else -> {
            }
        }

        var a = 5;
        var b = 8;
        var max = if (a > b) a else b;
    }

    /**
     * 有参方法定义
     * args String数组定义
     */
    fun main(args: Array<String>) {

    }

    /**
     * 有返回值方法定义
     * 返回字符串
     */
    fun mains(): String {

        return ""
    }

    /**
     * 有默认参数方法的定义
     */
    fun print(s: String = "没有内容") {

    }

    //调用
    //不传参数，则s = "没有内容"
    //print()
    //传参，则s = "我是内容"
    // print("我是内容")

    //定义接口
    interface M {
        fun gM(s: String)
    }

    fun setInterFace(i: M) {

    }

}