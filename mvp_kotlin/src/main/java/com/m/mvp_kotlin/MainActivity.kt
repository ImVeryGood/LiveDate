package com.m.mvp_kotlin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import com.m.mvp_kotlin.bean.MBean
import com.m.mvp_kotlin.utils.DataStoreUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val array = IntArray(10);
        val a: Int = 6678956;
        init(a);
        arrayCreate();
    }

    private fun init(vararg v: Int) {

    }

    private fun arrayCreate() {
        var editor = getSharedPreferences("store", Context.MODE_PRIVATE).edit();
        editor.putString("a", "a")
        editor.apply();

        val array1 = Array(10) { i -> i * i }
        val array2 = IntArray(10);
        val array3 = arrayOfNulls<Int>(5)
        val array4 = intArrayOf(1, 2);

        val array5 = String()
        //创建不可变集合，返回值是List
        var list21 = listOf("Java", "Kotlin", null, "Go")
        println(list21)
        println("listOf的返回对象的实际类型：${list21.javaClass}")
        //创建不可变集合，返回值是list，创建不含null的集合对象
        var list2 = listOfNotNull("Java", "Kotlin", null, "Go")
        println(list2)
        println("listOfNotNull的返回对象的实际类型：${list2.javaClass}")
        //创建可变集合，返回值是MutableList
        var mutableList = mutableListOf("Java", "Kotlin", null, "Go")
        println(mutableList)
        println("mutableListOf的返回对象的实际类型：${mutableList.javaClass}")
        //创建ArrayList集合
        var arrayList = arrayListOf("Java", "Kotlin", null, "Go")
        println(arrayList)
        println("arrayListOf的返回对象的实际类型：${arrayList.javaClass}")


        //创建不可变集合，返回值是List
        var list1 = mutableListOf("Java", "Kotlin", null, "Go")
        //在索引2处插入一个新元素
        list1.add(2, "Java")
        println(list1)
        //删除索引1处的元素
        list1.removeAt(1)
        println(list1)
        //将索引1处的元素替换为"Python"
        list1[1] = "Python"
        println(list1)
        //清空List集合的所有元素
        list1.clear()
        println(list1.count())

        val items = setOf(2, 3, 5);
//        items.forEach(fun(value: Int) {
//            print(value);
//        })
        val a: Int = 2;
        val b: Int = 5;
        val max = if (a > b) a else b;

        when (a) {
            1 -> print("x==1")
            2 -> print("x==2")
        }
        val mBean = MBean();
        val name = mBean.name;
        mBean.name = 100;
        mBean.title = "666"
        Log.d("SSSSSSSSSSS", "arrayCreate: " + mBean.title)
        println("SSSSSSSSSSS$mBean.title")
    }

    suspend fun switchView(view: View) {
        when (view.id) {
            R.id.text -> {
                var view = LayoutInflater.from(this).inflate(R.layout.second_layout, null);
                //setContentView(view);
               // DataStoreUtils.writeData(this,"66666")
                //Log.d("SSSSSSSSSSSSSSSS", "switchView: "+DataStoreUtils.readData(this))
            }
        }

    }



}


